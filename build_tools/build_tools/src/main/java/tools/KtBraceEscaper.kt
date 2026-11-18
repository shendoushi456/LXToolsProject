package tools

import java.io.File

/**
 * Kotlin 源码孤立大括号转义工具。
 *
 * 背景：XmlClassGuard(KtFileParser) 用纯文本算法解析 .kt——正则删注释后做字符级大括号配平，
 * 不识别字符串边界。字符串/字符字面量里的孤立 { 或 } 会让配平失败(removeRange 越界)，
 * 导致 xmlClassGuard 任务在"文件已移动、内容未替换"的中间态崩溃。
 *
 * 处理策略（在 xmlClassGuard 任务执行前调用）：
 * 1. 字符串感知状态机扫描（比插件的文本算法更接近 Kotlin 语法）；
 * 2. 普通字符串内的 { } → 转义为 \u007B \u007D（Kotlin Unicode 转义，编译后内容完全等价）；
 * 3. 字符字面量内的 '{' '}' → 转义为 '\u007B' '\u007D'；
 * 4. 模板表达式 ${...} 内部是代码，括号配平，不动；
 * 5. raw string 不支持反斜杠转义，无法自动改写：若统计到 raw 内大括号失衡，仅打印警告，
 *    需人工处理（实测项目中 raw string 内括号配平，此分支为防御性报告）。
 */
object KtBraceEscaper {

    // 转义目标：项目根下的 .kt 源码（排除 build 产物目录）
    fun escapeKtBraces(rootDir: File = File(System.getProperty("user.dir"))) {
        val ktFiles = findKtFiles(rootDir)
        var escapedCount = 0
        var fileCount = 0
        val rawWarnings = mutableListOf<String>()

        for (file in ktFiles) {
            val text = file.readText()
            val result = escape(text, rawWarnings, file.path)
            if (result != text) {
                file.writeText(result)
                fileCount++
            }
        }
        escapedCount = fileCount
        println("[KtBraceEscaper] 扫描 .kt 文件: ${ktFiles.size} 个, 转义修改: $escapedCount 个" +
                (if (rawWarnings.isEmpty()) "" else ", raw string 失衡警告: ${rawWarnings.size} 个"))
        rawWarnings.forEach { println("[KtBraceEscaper][警告] $it") }
    }

    /**
     * 对单个文件文本做转义，返回新文本（无变化时返回原对象，便于幂等跳过写回）
     */
    fun escape(text: String, rawWarnings: MutableList<String>? = null, pathForWarn: String = ""): String {
        val sb = StringBuilder(text.length + 64)
        // 各字面量上下文的括号统计（用于 raw string 失衡报告）
        var rawOpen = 0
        var rawClose = 0
        // raw string 内的注释启动序列计数：// 与 /* 同样会被插件注释剥离误删其后的内容，无法转义，只能警告
        var rawCommentSeq = 0
        // 模板表达式追踪：stack 记录进入模板前的字面量上下文，braces 记录每层模板内的代码块深度。
        // 只有 brace 深度归零的 } 才是模板收尾，避免把模板内 lambda/块的 } 误判为收尾
        val templateStack = ArrayDeque<String>()
        val templateBraces = ArrayDeque<Int>()
        var ctx = "CODE"
        var i = 0
        val n = text.length
        var changed = false

        while (i < n) {
            val c = text[i]
            val next = if (i + 1 < n) text[i + 1] else '\u0000'
            when (ctx) {
                "CODE" -> {
                    when {
                        c == '/' && next == '/' -> { ctx = "LINE_COMMENT"; sb.append(c); i++ }
                        c == '/' && next == '*' -> { ctx = "BLOCK_COMMENT"; sb.append(c); i++ }
                        c == '"' && next == '"' && i + 2 < n && text[i + 2] == '"' -> {
                            ctx = "RAW"; sb.append(text, i, i + 3); i += 3
                        }
                        c == '"' -> { ctx = "STRING"; sb.append(c); i++ }
                        c == '\'' -> { ctx = "CHAR"; sb.append(c); i++ }
                        c == '{' -> {
                            // 模板表达式内的代码块（如 lambda）加深层级
                            if (templateBraces.isNotEmpty()) {
                                templateBraces.addLast(templateBraces.removeLast() + 1)
                            }
                            sb.append(c); i++
                        }
                        // 模板表达式收尾：代码块深度归零的 } 才回到进入前的字面量上下文
                        c == '}' && templateBraces.isNotEmpty() -> {
                            val depth = templateBraces.removeLast()
                            if (depth == 0) {
                                ctx = templateStack.removeLast()
                            } else {
                                templateBraces.addLast(depth - 1)
                            }
                            sb.append(c); i++
                        }
                        else -> { sb.append(c); i++ }
                    }
                }
                "LINE_COMMENT" -> {
                    if (c == '\n') ctx = "CODE"
                    sb.append(c); i++
                }
                "BLOCK_COMMENT" -> {
                    if (c == '*' && next == '/') { ctx = "CODE"; sb.append(text, i, i + 2); i += 2 }
                    else { sb.append(c); i++ }
                }
                "STRING" -> {
                    when {
                        c == '\\' -> { sb.append(text, i, i + 2); i += 2 }  // 保留既有转义（含 \uXXXX），保证幂等
                        c == '"' -> { ctx = "CODE"; sb.append(c); i++ }
                        c == '$' && next == '{' -> {
                            // 模板表达式开始：内部是代码，进入 CODE
                            templateStack.addLast("STRING")
                            templateBraces.addLast(0)
                            ctx = "CODE"
                            sb.append(text, i, i + 2); i += 2
                        }
                        // 破坏字符串内的注释启动/结束序列：// /* */ 中的 / 转义为 \u002F。
                        // XmlClassGuard 的注释剥离正则会把字符串里的 /* 当块注释开头，
                        // 非贪婪吞掉其后大量真实代码（含大括号），导致全文配平失衡崩溃
                        // 典型如 MIME 类型字符串 "image/*"
                        c == '/' && (next == '/' || next == '*') -> { sb.append("\\u002F"); changed = true; i++ }
                        c == '*' && next == '/' -> { sb.append("*\\u002F"); changed = true; i += 2 }
                        c == '{' -> { sb.append("\\u007B"); changed = true; i++ }
                        c == '}' -> { sb.append("\\u007D"); changed = true; i++ }
                        else -> { sb.append(c); i++ }
                    }
                }
                "CHAR" -> {
                    when {
                        c == '\\' -> { sb.append(text, i, i + 2); i += 2 }
                        c == '\'' -> { ctx = "CODE"; sb.append(c); i++ }
                        c == '{' -> { sb.append("\\u007B"); changed = true; i++ }
                        c == '}' -> { sb.append("\\u007D"); changed = true; i++ }
                        else -> { sb.append(c); i++ }
                    }
                }
                "RAW" -> {
                    when {
                        c == '"' && next == '"' && i + 2 < n && text[i + 2] == '"' -> {
                            ctx = "CODE"
                            sb.append(text, i, i + 3); i += 3
                        }
                        c == '$' && next == '{' -> {
                            templateStack.addLast("RAW")
                            templateBraces.addLast(0)
                            ctx = "CODE"
                            sb.append(text, i, i + 2); i += 2
                        }
                        c == '/' && (next == '/' || next == '*') -> {
                            // raw string 内的注释启动序列同样会被插件注释剥离误删其后的内容，
                            // raw 不支持反斜杠转义无法自动处理，只能统计后警告
                            rawCommentSeq++
                            sb.append(text, i, i + 2); i += 2
                        }
                        else -> {
                            if (c == '{') rawOpen++ else if (c == '}') rawClose++
                            sb.append(c); i++
                        }
                    }
                }
            }
        }
        if (rawWarnings != null && (rawOpen != rawClose || rawCommentSeq > 0)) {
            rawWarnings.add(
                "raw string 内含大括号失衡({ = $rawOpen, } = $rawClose)或注释启动序列($rawCommentSeq 处), " +
                        "XmlClassGuard 剥离注释时会误删 raw 内容无法自动转义, 请人工处理: $pathForWarn"
            )
        }
        return if (changed) sb.toString() else text
    }

    private fun findKtFiles(dir: File): MutableList<File> {
        val files = mutableListOf<File>()
        val list = dir.listFiles() ?: return files
        for (f in list) {
            if (f.isDirectory) {
                // 排除构建产物目录（与 InsertCode.findJavaFile 同口径）
                if (f.absolutePath.contains("build") || f.name == ".git") continue
                files.addAll(findKtFiles(f))
            } else if (f.name.endsWith(".kt")) {
                files.add(f)
            }
        }
        return files
    }
}
