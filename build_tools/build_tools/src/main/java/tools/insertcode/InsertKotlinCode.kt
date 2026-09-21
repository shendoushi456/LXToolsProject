package tools.insertcode


import tools.BaseBuilder
import java.io.File
import java.util.Random


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
            val result = mutableListOf<String>()
            var modified = false

            for (line in lines) {
                result.add(line)

                // 匹配原则：包含 fun 关键字，且包含 {
                // 排除：已经包含 insertCode1 的方法（防止重复插入），排除接口和抽象方法
                if (line.contains("fun ") && line.contains("{")
                    && !line.contains("abstract ") && !line.contains("}")&& !line.contains("//")
                ) {
                    // 获取缩进量
                    val indent = line.takeWhile { it.isWhitespace() }
                    val innerIndent = "$indent    "

                    // 将垃圾代码按行处理并添加缩进
                    val formattedJunkCode =  getRandomFun().lines().joinToString("\n") {
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


        fun getRandomFun(): String{
//            val JUNK_LIST = listOf(generateDynamicJunkCode(), generateDynamicJunkCode2(), generateDynamicJunkCode3(), generateDynamicJunkCode4())
            val random = (0..3).random()
//            println("nextInt======: ${random}")
            when(random){
                0->{
                    return generateDynamicJunkCode()
                }

                1->{
                    return generateDynamicJunkCode2()
                }

                2->{
                    return generateDynamicJunkCode3()
                }

                3->{
                    return generateDynamicJunkCode4()
                }
            }


            return generateDynamicJunkCode()
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
        private fun getRandomString(): String {
            val random = Random()
            val length =  random.nextInt(methodNumMin,methodNumMax)
            val chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
            // Kotlin 风格的随机字符串生成：更加简洁
            return (1..length)
                .map { chars.random() }
                .joinToString("")
        }


    }

}

