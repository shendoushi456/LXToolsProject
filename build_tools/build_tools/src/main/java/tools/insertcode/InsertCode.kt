package tools.insertcode

import com.github.javaparser.StaticJavaParser
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration
import com.github.javaparser.ast.body.MethodDeclaration
import com.github.javaparser.ast.stmt.BlockStmt
import com.github.javaparser.ast.visitor.VoidVisitorAdapter
import com.github.javaparser.printer.PrettyPrinter
import tools.insertcode.junkcode.InsertCode
import java.io.File
import java.io.FileWriter


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
            sb.append( InsertCode.generateDynamicJunkCode()+"\n")
            sb.append( InsertCode.generateDynamicJunkCode2()+"\n")
            sb.append( InsertCode.generateDynamicJunkCode3()+"\n")
            sb.append( InsertCode.generateDynamicJunkCode4()+"\n")

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
            val file=(arg as Array<Any>).get(0) as File
            val methodList=(arg as Array<Any>).get(1) as List<MethodDeclaration>
            //从模板文件里随机一个方法进行插入
            val methodDeclaration=methodList.random()
            val body1 = md.body.get()
            val body2 = BlockStmt()
            val firstLineCode = body1.statements.getOrNull(0)?.toString()
            //第一行代码 如果是 int password = 123456;就代表已经插入，则不进行修改
            if (firstLineCode != null ) {
                methodDeclaration.body.get().statements.forEach {
                    body2.addStatement(it)
                }
            }
            body1.statements.forEach {
                body2.addStatement(it)
            }
            md.setBody(body2)
        } catch (e: Exception) {
//            System.out.println(e.message)
        }
    }















}