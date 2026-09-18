package tools

import java.io.File
import java.io.InputStreamReader
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import kotlin.random.Random

open class BaseBuilder(
    val tag: String,
    protected val workPath: String,
    private val randomOffset: Int
) {
    var keyWords = mutableListOf(
        //https://book.kotlincn.net/text/keyword-reference.html
        // 硬关键字,不能用作标识符
        "as", "as?", "break", "class", "continue", "do", "else", "false",
        "for", "fun", "if", "in", "!in", "interface", "is", "!is", "null",
        "object", "package", "return", "super", "this", "throw", "true",
        "try", "typealias", "typeof", "val", "var", "when", "while",
        // 软关键字
        "by", "catch", "constructor", "delegate", "dynamic", "field",
        "file", "finally", "get", "import", "init", "param", "property",
        "receiver", "set", "setparam", "value", "where",
        // 修饰符关键字
        "abstract", "actual", "annotation", "companion", "const",
        "crossinline", "data", "enum", "expect", "external", "final",
        "infix", "inline", "inner", "internal", "lateinit", "noinline",
        "open", "operator", "out", "override", "private", "protected",
        "public", "reified", "sealed", "suspend", "tailrec", "vararg",
        // 特殊标识符
        "field", "it"
    )
    private var random: Random? = null
    fun runCmd(cmd: String, workDir: File = File(workPath)): String {
        log("cmd: $cmd, workDir:${workDir.path}")
        if (!workDir.exists()) workDir.mkdirs()
        val process =
            ProcessBuilder().command("sh", "-c", cmd).directory(workDir).redirectErrorStream(true)
                .start()
        val sb = StringBuffer()
        val outputThread = Thread {
            InputStreamReader(process.inputStream).use { reader ->
                reader.readLines().forEach { line ->
                    sb.append(line)
                    System.out.println("\t\t\t$line")
                }
            }
        }
        outputThread.start()
        process.waitFor()
        outputThread.join()
        return sb.toString()
    }

    open fun log(msg: String, tag: String = this.tag) {
        println("[$tag]-->$msg")
    }

    open fun warningMsg(msg: String) {
        log("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
        log("")
        log("@@@@@@@@ \u001B[31m$msg\u001B[0m @@@@@@@@")
        log("")
        log("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
    }

    fun randomString(
        input: String, length: Int, onlyLowerCase: Boolean = false, onlyUpperCase: Boolean = false
    ): String {
        if (random == null) {
            val seed = input.hashCode().toLong()
            random = Random(seed + randomOffset)
        }
        val chars = if (onlyUpperCase) "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        else if (onlyLowerCase) "abcdefghijklmnopqrstuvwxyz"
        else "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        val stringBuilder = StringBuilder()
        // 生成随机字符串
        for (i in 0 until length) {
            val randomIndex = random!!.nextInt(chars.length)
            stringBuilder.append(chars[randomIndex])
        }
        val result = stringBuilder.toString()
        if (!keyWords.contains(result)) {
            return result
        }
        return randomString(input, length, onlyLowerCase, onlyUpperCase)
    }

    fun readFileAndReplaceWithRegex(
        pathList: List<String>,
        oldText: List<Regex>,
        newText: List<String>,
        workDir: String = workPath
    ) {
        pathList.forEach { path ->
            val file = File("${workDir}$path")
            if (!file.exists()) {
                warningMsg("文件未找到:$path")
                return@forEach
            }
            val fileContent = file.readText()
            var newContent = fileContent
            oldText.forEachIndexed { index, s ->
                newContent = newContent.replace(s, newText[index])
            }
            file.writeText(newContent)
        }
    }

    fun readFileAndReplace(
        pathList: List<String>,
        oldText: List<String>,
        newText: List<String>,
        workDir: String = workPath
    ) {
        pathList.forEach { path ->
            val file = File("${workDir}$path")
            if (!file.exists()) {
                warningMsg("文件未找到:$path")
                return
            }
            val fileContent = file.readText()
            var newContent = fileContent
            oldText.forEachIndexed { index, s ->
                newContent = newContent.replace(s, newText[index])
            }
            file.writeText(newContent)
        }
    }

    fun moveFile(filePath: String, newFolder: String, folder: String = workPath) {
        val folderFile = File("$folder$newFolder")
        if (!folderFile.exists()) folderFile.mkdirs()
        runCmd("mv $filePath $newFolder")
    }

    fun cpFolder(sourceDir: File, targetDir: File) {
        if (!sourceDir.isDirectory) {
            throw IllegalArgumentException("Source is not a directory")
        }
        if (!targetDir.exists()) {
            targetDir.mkdirs()
        }
        sourceDir.listFiles()?.forEach { file ->
            val targetFile = File(targetDir, file.name)
            if (file.isDirectory) {
                cpFolder(file, targetFile)
            } else {
                cpFile(file, targetFile)
            }
        }
    }

    fun findFiles(directory: File, filter: (file: File) -> Boolean): List<File> {
        val javaFiles = mutableListOf<File>()
        if (directory.exists() && directory.isDirectory) {
            directory.listFiles()?.forEach { file ->
                if (file.isDirectory) {
                    javaFiles.addAll(findFiles(file, filter))
                } else if (file.isFile && filter(file)) {
                    javaFiles.add(file)
                }
            }
        }
        return javaFiles
    }

    fun findKtAndJavaFiles(directory: String): List<String> {
        return findFiles(File(directory)) {
            return@findFiles it.extension == "kt" || it.extension == "java"
        }.map { it.path }.toList()
    }

    fun cpFile(file: File, targetFile: File) {
        if (!targetFile.exists()) {
            targetFile.parentFile.mkdirs()
        }
        Files.copy(file.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING)
    }

    fun refactorJavaCodeToPackage(
        classPathList: List<String>, oldPkg: String, newPkg: String
    ) {
        classPathList.forEach { classPath ->
            readFileAndReplace(listOf(classPath), listOf(oldPkg), listOf(newPkg))
            val folder = classPath.split("/").let {
                it.subList(0, it.size - 1).joinToString("/")
            }.replace(oldPkg.replace('.', '/'), newPkg.replace('.', '/'))
            moveFile(classPath, folder)
        }
    }


    fun refactorJavaPkg(
        fileFolder: String, oldPkg: String, newPkg: String, folder: String = workPath
    ) {
        val files = findFiles(File("${folder}$fileFolder")) {
            return@findFiles it.extension == "java"
        }
        refactorJavaCodeToPackage(files.map { return@map it.path.replace(folder, "") }
            .filter { return@filter it.contains(oldPkg.replace('.', '/')) }, oldPkg, newPkg)
        readFileAndReplace(
            files.map { return@map it.path.replace(folder, "") }
                .filter { return@filter !it.contains(oldPkg.replace('.', '/')) },
            listOf(oldPkg),
            listOf(newPkg)
        )
    }

    fun renameClass(oldClassName: String, newClassName: String, folder: String = workPath) {
        val oldName = oldClassName.split(".").last()
        val newName = newClassName.split(".").last()
        val oldClassPath = oldClassName.replace(".$oldName", "").replace('.', '/')
        val newClassPath = newClassName.replace(".$newName", "").replace('.', '/')
        val folderFile = File("${folder}/${oldClassPath}")
        if (!folderFile.exists()) {
            println("Directory does not exist.")
            return
        }
        folderFile.listFiles()?.forEach { file ->
            val extension = file.extension
            if (file.name.split('.').first() == oldName
                && (extension == "kt" || extension == "java")
            ) {
                var content = file.readText()
                content = content.replace(
                    "package ${oldClassPath.replace('/', '.')}",
                    "package ${newClassPath.replace('/', '.')}"
                )
                content = content.replace(oldName, newName)
                file.writeText(content)
                val newFile = File("${folder}/${newClassPath}/${newName}.${extension}")
                newFile.parentFile.mkdirs()
                file.renameTo(newFile)
            }
        }
    }

    fun fixRAndDataBidingBug(folder: String, oldPkg: String, newPkg: String) {
        readFileAndReplaceWithRegex(
            findKtAndJavaFiles(folder),
            listOf(
                Regex("$oldPkg.R"),
                Regex("$oldPkg.BR"),
                Regex("$oldPkg.BuildConfig"),
                Regex("(import)?\\s*(${"$oldPkg.databinding".split(".").joinToString("\\.")})\\s*(.*)")
            ),
            listOf("$newPkg.R", "$newPkg.BR","$newPkg.BuildConfig", "import $newPkg.databinding\$3")
        )
    }
}