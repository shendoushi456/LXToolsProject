package tools.insertcode

import com.github.javaparser.StaticJavaParser
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration
import com.github.javaparser.ast.body.MethodDeclaration
import com.github.javaparser.ast.stmt.BlockStmt
import com.github.javaparser.ast.stmt.DoStmt
import com.github.javaparser.ast.stmt.ForEachStmt
import com.github.javaparser.ast.stmt.ForStmt
import com.github.javaparser.ast.stmt.IfStmt
import com.github.javaparser.ast.stmt.LabeledStmt
import com.github.javaparser.ast.stmt.ReturnStmt
import com.github.javaparser.ast.stmt.Statement
import com.github.javaparser.ast.stmt.SwitchStmt
import com.github.javaparser.ast.stmt.SynchronizedStmt
import com.github.javaparser.ast.stmt.ThrowStmt
import com.github.javaparser.ast.stmt.TryStmt
import com.github.javaparser.ast.stmt.WhileStmt
import com.github.javaparser.ast.visitor.VoidVisitorAdapter
import com.github.javaparser.printer.PrettyPrinter
import tools.insertcode.junkcode.InsertCode
import java.io.File
import java.io.FileWriter
import kotlin.random.Random

// 垃圾代码倍数：每个生成器调用 5 次，InsertCodeFile 中共生成 20 个垃圾方法
private const val JUNK_REPEAT_COUNT = 5

// 每个业务方法插入的垃圾方法体数量（原逻辑为随机抽 1 个）
private const val JUNK_INSERT_COUNT = 5

// 首语句标记：插入时写到方法体最前，检测到即跳过该方法，防止重复构建叠加插入（纯死存储，R8 会清除）
private const val JUNK_MARK_STATEMENT = "int nsr = 123456;"


class InsertCode {
    companion object {
//        @JvmStatic
//        fun main(args: Array<String>) {
//
//        }


        fun insertJAVACode(){

            //当前项目
            val file = File(System.getProperty("user.dir"))
//            System.out.println("main file ${file.absolutePath}")
            //模板代码文件
            val javaTemplateFile = File(System.getProperty("user.dir"),"build_tools/build_tools/src/main/java/tools/insertcode/junkcode/InsertCode.java")
            val javaInsertCodeFile= File(System.getProperty("user.dir"),"build_tools/build_tools/src/main/java/tools/insertcode/junkcode/InsertCodeFile.java")
//            System.out.println("javaTemplateFile file ${javaTemplateFile.absolutePath}")
            //初始化insertCodeFile 文件
            cleanClassBody(javaInsertCodeFile)
            // 写入insertCodeFile 垃圾代码
            writeStringCodeToFile(javaInsertCodeFile)

            val cu = StaticJavaParser.parse(javaInsertCodeFile)
            val methodList= mutableListOf<MethodDeclaration>()
            for (declaration in cu.childNodes) {
                if (declaration is ClassOrInterfaceDeclaration) {
                    //将模板文件里的方法都保存起来
                    methodList.addAll(declaration.methods)
                }
            }
            findJavaFile(
                file,
                methodList,
                javaTemplateFile
            )
        }



            /**
             * 清空 Java/Kotlin 类的主体内容，只保留头部定义
             */
            fun cleanClassBody(javaFile: File) {
                try {
                    // Kotlin 允许直接使用 java.io.File 的扩展方法 readText()
                    val content = javaFile.readText(Charsets.UTF_8)

                    // 寻找第一个左大括号 '{' 的位置
                    val firstBrace = content.indexOf('{')

                    if (firstBrace != -1) {
                        // 使用 substring 获取头部（包括大括号）
                        val header = content.substring(0, firstBrace + 1)
                        // 使用字符串模板构建新内容
                        val newContent = "$header\n\n}"
                        // 写入文件
                        javaFile.writeText(newContent, Charsets.UTF_8)
//                        println("Success: ${javaFile.name} has been cleaned.")
                    } else {
//                        println("Skip: No '{' found in ${javaFile.name}")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }


// 调用示例
// val file = File("path/to/YourClass.java")
// JavaFileCleaner.cleanClassBody(file)




        fun writeStringCodeToFile(file: File) {
            val lines = file.readLines()
            val result = mutableListOf<String>()
            var modified = false

            for (line in lines) {
                result.add(line)

                // 匹配原则：包含 fun 关键字，且包含 {
                // 排除：已经包含 insertCode1 的方法（防止重复插入），排除接口和抽象方法
                if (line.contains("InsertCodeFile") && line.contains("{")
                ) {
                    // 获取缩进量
                    val indent = line.takeWhile { it.isWhitespace() }
                    val innerIndent = "$indent    "

                    // 将垃圾代码按行处理并添加缩进
                    val formattedJunkCode =  getRandomJAVAMethod().lines().joinToString("\n") {
//                        println("成功加入:${it}")
                        if (it.isBlank()) it else innerIndent + it
                    }

                    result.add(formattedJunkCode)
                    modified = true
                }
            }

            if (modified) {
                file.writeText(result.joinToString("\n"))
//                println("已修改: ${file.absolutePath}"+result.joinToString("\n"))
            }
        }



        fun getRandomJAVAMethod(): String{
            val sb = StringBuilder()
            // 五倍插入量：4 个生成器各调用 5 次，产出 20 个垃圾方法；方法名/字段名均随机去重
            repeat(JUNK_REPEAT_COUNT) {
                sb.append( InsertCode.generateDynamicJunkCode()+"\n")
                sb.append( InsertCode.generateDynamicJunkCode2()+"\n")
                sb.append( InsertCode.generateDynamicJunkCode3()+"\n")
                sb.append( InsertCode.generateDynamicJunkCode4()+"\n")
            }

            return  sb.toString()
        }





        private fun findJavaFile(file: File,methodList :List<MethodDeclaration>,javaTemplateFile:File) {
            if (file.isDirectory) {
                file.listFiles().forEach {
                    findJavaFile(it,methodList,javaTemplateFile)
                }
            }//不处理build目录下的文件 和模板文件
            else if (javaTemplateFile != file &&!file.absolutePath.contains("build")) {
                val isJavaFile = file.name.endsWith(".java")
                if (isJavaFile) {
                    generateJavaFile(file,methodList)
                }
            }
        }

        private fun generateJavaFile(file: File,methodList :List<MethodDeclaration>) {
            val cu = StaticJavaParser.parse(file)
            val methodVisitor = MethodVisitor()
            methodVisitor.visit(cu, arrayOf(file,methodList))
            // 生成Java文件
            FileWriter(file).use { writer ->
                val printer = PrettyPrinter()
                val sourceCode: String = printer.print(cu)
                writer.write(sourceCode)
            }
//            System.out.println("file ${file.name} 写完了")
        }
    }
}

class MethodVisitor : VoidVisitorAdapter<Any>() {
    override fun visit(md: MethodDeclaration, arg: Any) {
        super.visit(md, arg)
        try {
            val methodList=(arg as Array<Any>).get(1) as List<MethodDeclaration>
            // Compose 函数过滤：@Composable 注解的方法跳过插入。
            // Compose 函数体是编译器组追踪的 UI DSL，插入死分支 if 等语句会干扰其插桩
            if (md.annotations.any { it.nameAsString.endsWith("Composable") }) {
                return
            }
            val body1 = md.body.get()
            //首语句标记检测：归一化比较（去除空白），命中标记说明该方法已插入过垃圾代码，跳过防止重复构建叠加
            val firstStmt = body1.statements.getOrNull(0)?.toString()?.filter { !it.isWhitespace() }
            if (firstStmt == JUNK_MARK_STATEMENT.filter { !it.isWhitespace() }) {
                return
            }
            val body2 = BlockStmt()
            val firstLineCode = body1.statements.getOrNull(0)?.toString()
            //第一行代码 如果是 int password = 123456;就代表已经插入，则不进行修改
            if (firstLineCode != null ) {
                //首语句标记：插到方法体最前，供下次构建检测（纯死存储，R8 会清除，不进最终 APK）
                body2.addStatement(StaticJavaParser.parseStatement(JUNK_MARK_STATEMENT))
                //随机抽 5 个不重复的垃圾方法体作为"块"；块内语句存在数据依赖（后句引用前句声明的变量），必须保持连续
                val junkBlocks = methodList.shuffled()
                    .take(JUNK_INSERT_COUNT)
                    .map { it.body.get().statements.toList() }
                //插入位置分散：方法体 n 条语句形成间隙，把 5 个垃圾块随机分配进各间隙
                //尾部间隙防护：最后一条语句可能终止（return/throw 等）时禁用尾部间隙，
                //防止垃圾代码出现在 return 之后形成 unreachable statement 编译错误
                val allowTailGap = body1.statements.lastOrNull()?.let { !mayTerminate(it) } ?: false
                val gapCount = if (allowTailGap) body1.statements.size + 1 else body1.statements.size
                val gapBuckets = List(gapCount) { mutableListOf<List<Statement>>() }
                junkBlocks.forEach { block ->
                    gapBuckets[Random.nextInt(gapCount)].add(block)
                }
                for (index in 0 until body1.statements.size) {
                    //间隙 index：第 index 条语句之前
                    gapBuckets[index].forEach { block -> block.forEach(body2::addStatement) }
                    body2.addStatement(body1.statements[index])
                }
                //尾部间隙：最后一条语句之后（仅当最后语句确定不终止时启用）
                if (allowTailGap) {
                    gapBuckets[gapCount - 1].forEach { block -> block.forEach(body2::addStatement) }
                }
            }
            md.setBody(body2)
        } catch (e: Exception) {
//            System.out.println(e.message)
        }
    }
}

/**
 * 判断语句是否可能导致其后代码不可达（return/throw 及其组合形态）。
 * synchronized 块按块内末条语句递归判定（块内 return/throw 即整条语句终止，其后代码不可达，
 * 如 HandlerAdInterface.getadIndexCurrent 的 synchronized (this) { return adIndexCurrent; }）；
 * 循环/try/switch 等复杂结构一律保守视为"可能终止"：仅损失一个尾部插入点，不影响正确性。
 */
private fun mayTerminate(stmt: Statement): Boolean = when (stmt) {
    is ReturnStmt, is ThrowStmt -> true
    is IfStmt -> stmt.elseStmt.isPresent && mayTerminate(stmt.thenStmt) && mayTerminate(stmt.elseStmt.get())
    is BlockStmt -> stmt.statements.lastOrNull()?.let { mayTerminate(it) } ?: false
    is SynchronizedStmt -> stmt.body.statements.lastOrNull()?.let { mayTerminate(it) } ?: false
    is LabeledStmt -> mayTerminate(stmt.statement)
    is WhileStmt, is DoStmt, is ForStmt, is ForEachStmt, is TryStmt, is SwitchStmt -> true
    else -> false
}
