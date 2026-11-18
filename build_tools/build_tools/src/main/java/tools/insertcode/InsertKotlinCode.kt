package tools.insertcode


import tools.BaseBuilder
import java.io.File
import java.util.Random

// 垃圾代码倍数：每个 fun 插入 5 段随机垃圾代码（原逻辑为四选一插入 1 段）
private const val JUNK_REPEAT_COUNT = 5

// 首语句标记：插入时写到方法体第一行，检测到即跳过该方法，防止重复构建叠加插入
private const val JUNK_MARK_LINE = "val nsr = 123456"

// object 表达式（object : Xxx）特征：用于追踪其花括号体，体内的方法不插入垃圾代码
private val OBJECT_EXPR = Regex("object\\s*:")


class InsertKotlinCode : BaseBuilder("","",1){

    companion object {

        val methodNumMin = 5
        val methodNumMax= 20


//        @JvmStatic
//        fun main(args: Array<String>) {
//
//        }

        fun insertKotlinCode(){
            //当前项目
            val file = File(System.getProperty("user.dir"))
//            System.out.println("main file ${file.absolutePath}")
            //模板代码文件
            findJavaFile(file)
        }








        private fun findJavaFile(file: File) {

            if (file.isDirectory()&& (file.name.equals("androidTest")|| file.name.equals("test") || file.name.equals("insertCode"))){
//                System.out.println("过滤文件 ${file.absolutePath}")
            }else if (file.isDirectory()) {
                file.listFiles().forEach {
                    findJavaFile(it)
                }
            }//不处理build目录下的文件 和模板文件
            else if (!file.absolutePath.contains("build")) {
                val isJavaFile = file.name.endsWith(".kt")
                if (isJavaFile) {
                    injectCodeToFile(file)
//                    getJavaMethods(file.absolutePath)
//                    System.out.println("file ${file.name} 写完了")
                }
            }
        }








        fun getJavaMethods(filePath: String) {

            val file = File(filePath)
            if (!file.exists()) return

            val content = file.readText()

            // 更加严谨的正则表达式：
            // 匹配 fun 关键字 + 空格 + 方法名 + (
            val regex = Regex("""fun\s+([a-zA-Z0-9_]+)\s*[\(<]""")

            val matches = regex.findAll(content)
            matches.forEach { matchResult ->
                // groupValues[1] 是括号里匹配到的第一个组（即方法名）
                val methodName = matchResult.groupValues[1]
//                println("从源码中找到方法: $methodName")
            }
        }






        fun injectCodeToFile(file: File) {
            val lines = file.readLines()
            // 第一遍：定位每个 fun 方法体，把 5 段垃圾代码随机分散到方法体内的一级语句边界后
            val insertions = findScatterInsertPoints(lines)
            if (insertions.isEmpty()) return

            // 第二遍：重写文件，在对应行后插入垃圾段（行末统一补换行，保持 POSIX 格式）
            val sb = StringBuilder()
            lines.forEachIndexed { index, line ->
                sb.append(line)
                insertions[index]?.forEach { (segment, extraIndent) ->
                    val indent = line.takeWhile { it.isWhitespace() } + " ".repeat(extraIndent)
                    sb.append('\n')
                    sb.append(segment.lines().joinToString("\n") { if (it.isBlank()) it else indent + it })
                }
                sb.append('\n')
            }
            file.writeText(sb.toString())
//            println("已修改: ${file.absolutePath}")
        }


        fun getRandomJunkSegment(): String {
            // 单段垃圾代码：四选一随机；分散插入时每段独立分配插入位置
            return when ((0..3).random()) {
                0 -> generateDynamicJunkCode()
                1 -> generateDynamicJunkCode2()
                2 -> generateDynamicJunkCode3()
                else -> generateDynamicJunkCode4()
            }
        }


        /**
         * 扫描文件，为每个 fun 方法体确定垃圾段的插入位置。
         * 插入位置为方法体内一级语句边界行（该行处理完大括号深度与方法体一致），段随机分散到各边界；
         * 方法体无可用边界（空方法体等）时回退插在 fun 声明行后（原逻辑行为）。
         * 局限：基于文本的大括号配对，已剥离字符串/字符字面量与注释并跳过跨行 raw string，
         * 但极端语法（如 raw string 内不配对大括号且跨行等）仍可能漏判，漏判时表现为漏插而非语法破坏。
         */
        private fun findScatterInsertPoints(lines: List<String>): Map<Int, List<Pair<String, Int>>> {
            // 行索引 -> (垃圾段, 追加缩进空格数) 列表；语句边界插入缩进与该行同级(0)，fun 行回退为 4
            val insertions = mutableMapOf<Int, MutableList<Pair<String, Int>>>()
            var depth = 0
            var inFun = false
            var funDepth = -1
            var funLineIndex = -1
            var edgePoints = mutableListOf<Int>()
            var inRawString = false
            var inBlockComment = false
            var inTemplateString = false
            var alreadyMarked = false
            // 活跃的 object 表达式花括号体深度集合；位于其内的 fun 方法不插入垃圾代码
            var objectBodyDepths = mutableSetOf<Int>()
            var skipObject = false
            // "object :" 与花括体不同行开启时的挂起标记（如 return object :\n CB() {）
            var pendingObject = false

            fun finishFun() {
                //首语句标记检测：该方法已插入过垃圾代码，跳过，防止重复构建叠加；
                //object: 之后防护：位于 object 表达式体内或表达式体为块的方法同样跳过
                if (alreadyMarked || skipObject) return
                val segments = List(JUNK_REPEAT_COUNT) { getRandomJunkSegment() }
                //首语句标记：插在 fun 声明行后第一行（即方法体第一行），供下次构建检测
                insertions.getOrPut(funLineIndex) { mutableListOf() }.add(0, JUNK_MARK_LINE to 4)
                if (edgePoints.isEmpty()) {
                    // 回退：全部插在 fun 声明行后（标记之后）
                    insertions.getOrPut(funLineIndex) { mutableListOf() }.addAll(segments.map { it to 4 })
                } else {
                    // 桶分配：5 段随机散落到各语句边界，保持每段完整
                    val buckets = List(edgePoints.size) { mutableListOf<String>() }
                    segments.forEach { buckets[Random().nextInt(edgePoints.size)].add(it) }
                    edgePoints.forEachIndexed { pointIndex, lineIndex ->
                        buckets[pointIndex].forEach { segment ->
                            insertions.getOrPut(lineIndex) { mutableListOf() }.add(segment to 0)
                        }
                    }
                }
            }

            for ((index, rawLine) in lines.withIndex()) {
                if (inRawString) {
                    // 跨行 raw string 内容行：不参与大括号计数，不可作为插入点
                    if (countOccurrences(rawLine, "\"\"\"") % 2 == 1) {
                        inRawString = false
                    }
                    continue
                }
                if (inBlockComment) {
                    // 跨行块注释内容行：闭合后恢复，本行不计数不收集
                    if (rawLine.contains("*/")) {
                        inBlockComment = false
                    }
                    continue
                }

                // 计算可参与大括号计数的文本：剥离字符串/字符字面量与注释，处理跨行 raw string 与块注释
                var text = rawLine
                if (countOccurrences(text, "\"\"\"") % 2 == 1) {
                    // 跨行 raw string 从本行开始：仅 raw 之前的部分参与计数
                    inRawString = true
                    text = text.substringBefore("\"\"\"")
                }
                text = text
                    .replace(Regex("\"(?:\\\\.|[^\"\\\\])*\""), "")   // 字符串字面量
                    .replace(Regex("'(?:\\\\.|[^'\\\\])*'"), "")     // 字符字面量
                    .replace(Regex("/\\*.*?\\*/"), "")               // 同行闭合的块注释
                    .replace(Regex("//.*"), "")                     // 行注释
                // 跨行字符串模板（"abc ${...} def" 的 ${} 块跨行）：剥离后仍剩奇数引号即模板未闭合
                if (inTemplateString) {
                    // 模板内容行：出现新的未闭合引号说明模板收尾，本行整体跳过不计数（与开启行的截断计数配对）
                    if (text.count { it == '"' } % 2 == 1) {
                        inTemplateString = false
                    }
                    continue
                }
                if (text.count { it == '"' } % 2 == 1) {
                    // 模板从本行开始：仅第一个引号前的部分参与计数，模板内的大括号一律不计
                    inTemplateString = true
                    text = text.substringBefore('"')
                }
                if (text.contains("/*")) {
                    // 跨行块注释从本行开始：仅 /* 之前的部分参与计数
                    inBlockComment = true
                    text = text.substringBefore("/*")
                }
                val delta = text.count { it == '{' } - text.count { it == '}' }
                val prevDepth = depth
                depth += delta
                // object 表达式上下文："object :" 行若同时开启花括体则直接记录；
                // 否则挂起，等待下一行（体开启行）补录；depth 回落则移除过期记录
                if (OBJECT_EXPR.containsMatchIn(rawLine)) {
                    if (depth > prevDepth) {
                        objectBodyDepths.add(depth)
                    } else {
                        pendingObject = true
                    }
                }
                if (pendingObject && depth > prevDepth) {
                    objectBodyDepths.add(depth)
                    pendingObject = false
                }
                objectBodyDepths.removeAll { it > depth }

                if (!inFun) {
                    // 匹配原则：包含 fun 关键字，且包含 {；排除接口和抽象方法；
                    // "fun interface" 是类型声明而非方法，防止语句被误插进声明体
                    if (rawLine.contains("fun ") && rawLine.contains("{") && !rawLine.contains("interface")
                        && !rawLine.contains("abstract ") && !rawLine.contains("}") && !rawLine.contains("//")
                    ) {
                        // Compose 函数过滤：@Composable 注解的下一行即声明行（或注解与声明同行）时跳过。
                        // Compose 函数体是编译器组追踪的 UI DSL，插入死分支 if 等语句会干扰其插桩
                        val prevLine = lines.getOrNull(index - 1)?.trim() ?: ""
                        if (rawLine.contains("@Composable") || prevLine.startsWith("@Composable")) {
                            continue
                        }
                        inFun = true
                        funLineIndex = index
                        funDepth = depth
                        edgePoints = mutableListOf()
                        alreadyMarked = false
                        // object: 之后防护：
                        // 1. 方法位于 object 表达式体内（如类成员 val cb = object : CB() { 的 override fun）→ 跳过；
                        // 2. 方法表达式体本身是块（如 fun make() = object : R { / fun x() = when (y) {）
                        //    其"方法体"实为成员/分支区，任何插入都会破坏语法 → 跳过
                        val expressionBodyBlock = rawLine.contains("=") && rawLine.trim().endsWith("{")
                        skipObject = expressionBodyBlock || objectBodyDepths.any { it < depth }
                        continue
                    }
                    continue
                }

                // 方法体内：首语句标记检测（两侧都归一化去除空白后比较，命中说明已插入过）
                if (rawLine.filter { !it.isWhitespace() } == JUNK_MARK_LINE.filter { !it.isWhitespace() }) {
                    alreadyMarked = true
                }

                // 方法体内：该行处理完后深度与方法体一致，说明是完整的一级语句边界
                if (depth == funDepth && isStatementEdge(text, lines, index)) {
                    edgePoints.add(index)
                }
                if (depth < funDepth) {
                    // 方法体结束：分配垃圾段
                    finishFun()
                    inFun = false
                }
            }
            return insertions
        }

        /**
         * 判断该行是否为可靠的语句边界：
         * 1. 当前行不以续行/依附特征开头（链式调用 .map、布尔续行、else/catch/finally 等复合语句中段）；
         * 2. 当前行不以续行符结尾（多行语句中间）；
         * 3. 下一行不以续行特征开头。
         */
        private fun isStatementEdge(countable: String, lines: List<String>, index: Int): Boolean {
            val trimmed = countable.trim()
            // 剥离后为空：纯注释/字符串行，不作为边界
            if (trimmed.isEmpty()) return false
            // 当前行以续行/依附特征开头：多行链式调用、参数表、布尔表达式、else/catch 等复合语句中段
            if (listOf(".", "?.", "?:", "&&", "||", ",", "else", "catch", "finally", "->", "{")
                    .any { trimmed.startsWith(it) }) {
                return false
            }
            // 当前行以续行符结尾：多行语句中间
            // （二元操作符、!! 断链、类型/object 冒号跨行、in/is 关键字跨行）
            if (listOf(".", ",", "(", "&&", "||", "=", "->", "?:", "+", "-", "*", "/", "%", "<", ">", "!!", ":", " in", " is")
                    .any { trimmed.endsWith(it) }) {
                return false
            }
            // 当前行圆括号/方括号未配平（欠开括号）：多行表达式中间。
            // 典型如 if 条件跨行：if (checkPermission(x)\n != GRANTED\n) {
            // 垃圾插进条件中间会直接破坏语法
            if (countable.count { it == '(' || it == '[' } > countable.count { it == ')' || it == ']' }) {
                return false
            }
            // 下一行以续行特征开头：当前位置是多行语句中间；
            // "}" 开头意味着当前行是方法体最后一条语句（一级 return/throw 必为最后语句），
            // 其后插入点禁用，防止垃圾代码出现在 return 之后形成不可达代码；
            // "!!"/"+"/"]" 等：断链续行、操作符开头续行、数组下标收尾，插入会破坏表达式
            val next = lines.getOrNull(index + 1)?.trim() ?: return false
            if (listOf(".", "?.", "?:", "&&", "||", ",", ")", "]", "else", "catch", "finally", "->", "}", "{",
                    "!!", "+", "-", "*", "/", "%", "<", ">")
                    .any { next.startsWith(it) }) {
                return false
            }
            return true
        }

        private fun countOccurrences(text: String, token: String): Int {
            var count = 0
            var index = text.indexOf(token)
            while (index >= 0) {
                count++
                index = text.indexOf(token, index + token.length)
            }
            return count
        }




        fun generateDynamicJunkCode4(): String {

            val random = Random()
            val arrayName =   getRandomString()
            val codeMethod1 = getRandomString()
            return """
                   val ${arrayName} : Any = if (kotlin.random.Random.nextBoolean()) ${random.nextInt(100)} else "jH6"
                // 尝试将数字安全转为字符串，失败则触发 Elvis
                val ${codeMethod1}  = (${arrayName}  as? String)?.reversed() ?: "${getRandomString()}_${arrayName .hashCode()}"
                
                if (${codeMethod1}  == "${getRandomString()}") {
                    java.lang.System.out.print(${codeMethod1} )
                }
    """.trimIndent()
        }





        /**
         * 动态生成 Kotlin 格式的垃圾代码字符串
         */
        fun generateDynamicJunkCode3(): String {
            val random = Random()

            val arrayName =   getRandomString()
            val mCodeMethod1 = getRandomString()
            val mCodeMethod2 = getRandomString()


           return """
                val ${arrayName} : String? = if (java.lang.System.nanoTime() % 2 == 0L) "vN8" else null
                    val ${mCodeMethod1}  = ${arrayName} ?.let { 
                        it.repeat(kotlin.random.Random.nextInt(100)) 
                    } ?: run { 
                        "zY0_${kotlin.random.Random.nextInt(50)}" 
                    }
                    if (${mCodeMethod1} .startsWith("${mCodeMethod2} ")) {
                        android.util.Log.v("TAG", ${mCodeMethod1} )
                    }
""".trimIndent()


        }
        fun generateDynamicJunkCode2(): String {

            val random = Random()

            val arrayName = "arr_${getRandomString()}"
            val mMethodName1 = "ad_${getRandomString()}"

            return  """
               val ${arrayName}  = listOf("${getRandomString()}", "${getRandomString()}", "${getRandomString()}").map { 
                    it + kotlin.random.Random.nextInt(10) 
                }
                val ${mMethodName1}  = ${arrayName} .filter { it.length > ${random.nextInt(100)} }
                if (${mMethodName1} .isNotEmpty() && java.lang.System.currentTimeMillis() < ${random.nextInt(100)}) {
                    ${mMethodName1} .forEach { _ ->  }
                }
""".trimIndent()

        }



        fun generateDynamicJunkCode(): String {

            val random = Random()

            // 随机生成变量名和函数名
            val arrayName = "arr_${getRandomString()}"
            val outerLoop = "i_${getRandomString()}"
            val innerLoop = "j_${getRandomString()}"
            val tempVar = "tmp_${getRandomString()}"

            return """
               val ${arrayName} = kotlin.random.Random.nextInt(100)
                // Kotlin 风格的位运算：shl (<<), shr (>>), xor
                val ${outerLoop}  = (${arrayName}  shl ${random.nextInt(100)}) xor (${arrayName}  shr ${random.nextInt(100)})
                val ${innerLoop}  = ${outerLoop} .inv() and 0xFFFF
                if (${innerLoop}  == 0xBADB) { // 极低概率匹配
                    kotlin.io.print("Junk Value: ${tempVar}")
                }
            """.trimIndent()

        }










        /**
         * 生成随机字符串
         */
        // 已使用的随机名集合：保证单次构建内五倍垃圾代码的所有变量名唯一
        private val usedNames = mutableSetOf<String>()

        private fun getRandomString(): String {
            val random = Random()
            val length =  random.nextInt(methodNumMin,methodNumMax)
            val chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
            // Kotlin 风格的随机字符串生成：更加简洁
            val name = (1..length)
                .map { chars.random() }
                .joinToString("")
            return if (usedNames.add(name)) name else getRandomString()
        }


    }

}

