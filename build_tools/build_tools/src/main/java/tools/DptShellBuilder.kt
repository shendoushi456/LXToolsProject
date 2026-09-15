package tools

import java.io.File

private const val DPT_SHELL_PATH = "../dpt-shell/"

class DptShellBuilder(randomOffset: Int) : BaseBuilder("DptShell", DPT_SHELL_PATH, randomOffset) {
    fun refactorDpt(pkg: String) {
        checkAndDownloadDptShell()
        refactorZipCodeItemNative(pkg)
        refactorCode(pkg)
        runCmd("sh ./gradlew clean && sh ./gradlew assemble")
        cpFolder(File("${DPT_SHELL_PATH}executable"), File("build_tools/jiagu/"))
    }

    private fun checkAndDownloadDptShell() {
        val repositoryUrl = "https://github.com/luoyesiqiu/dpt-shell.git"
        val desiredTag = "v1.8.4"
        val directory = File(DPT_SHELL_PATH)
        if (directory.exists() && File(directory, ".git").exists()) {
            val curTag = runCmd("git describe --tags")
            if (!curTag.contains(desiredTag)) {
                runCmd("git checkout tags/$desiredTag")
            }
        } else {
            runCmd("git clone $repositoryUrl . && git checkout tags/$desiredTag")
        }
        runCmd("git add . && git checkout -f")
        runCmd("git submodule init && git submodule update")
        cpFile(File("local.properties"), File("${DPT_SHELL_PATH}local.properties"))
    }

    private fun refactorCode(pkg: String) {
        val dptPkgName = "com.luoye.dpt"
        val dptTargetPkgName = "com.${randomString(pkg, "luoye".length, onlyLowerCase = true)}.d"
        val shellPkgName = "com.luoyesiqiu.shell"
        val shellTargetPkgName =
            "com.${randomString(pkg, "luoyesiqiu".length, onlyLowerCase = true)}.s"
        log("$dptPkgName 重构为:$dptTargetPkgName")
        log("$shellPkgName 重构为:$shellTargetPkgName")
        refactorJavaPkg("shell/src/main/java/", dptPkgName, dptTargetPkgName)
        refactorJavaPkg("shell/src/main/java/", shellPkgName, shellTargetPkgName)
        refactorJavaCodeToPackage(
            listOf(
                "junkcode/src/main/java/com/luoye/dpt/junkcode/JunkClass.java",
            ), dptPkgName, dptTargetPkgName
        )
        readFileAndReplace(
            listOf(
                "shell/src/main/cpp/dpt_risk.cpp",
                "shell/src/main/cpp/dpt.cpp",
            ),
            listOf(dptPkgName.replace('.', '/'), shellPkgName.replace('.', '/')),
            listOf(dptTargetPkgName.replace('.', '/'), shellTargetPkgName.replace('.', '/'))
        )
        readFileAndReplace(
            listOf(
                "shell/src/main/assets/app_name",
                "shell/src/main/assets/app_acf",
                "shell/src/main/AndroidManifest.xml",
                "junkcode/proguard-rules.pro",
                "junkcode/build.gradle",
            ), listOf(dptPkgName, shellPkgName), listOf(dptTargetPkgName, shellTargetPkgName)
        )
        readFileAndReplace(
            listOf(
                "dpt/src/main/java/com/luoye/dpt/Const.java"
            ), listOf(shellPkgName), listOf(shellTargetPkgName)
        )
    }

    private fun refactorZipCodeItemNative(pkg: String) {
        val dexZipName = "i11111i111.zip"
        val targetZipName = "${randomString(pkg, dexZipName.length - 4)}.ttf"
        val codeItemName = "OoooooOooo"
        val targetCodeItemName = randomString(pkg, codeItemName.length)
        val nativeScrName = "vwwwwwvwww"
        val targetNativeName = randomString(pkg, nativeScrName.length)
        log("$dexZipName 重构为:$targetZipName")
        log("$codeItemName 重构为:$targetCodeItemName")
        log("$nativeScrName 重构为:$targetNativeName")
        readFileAndReplace(
            listOf(
                "dpt/src/main/java/com/luoye/dpt/buildler/Apk.java",
                "dpt/src/main/java/com/luoye/dpt/util/ZipUtils.java",
                "shell/build.gradle",
                "shell/src/main/java/com/luoyesiqiu/shell/Global.java",
                "shell/src/main/cpp/common/dpt_macro.h",
            ),
            listOf(dexZipName, codeItemName, nativeScrName),
            listOf(targetZipName, targetCodeItemName, targetNativeName)
        )
        val soLibName = "dpt"
        val targetSoLibName = randomString(pkg, soLibName.length, onlyLowerCase = true)
        log("so 库$soLibName 重构为:$targetSoLibName")
        readFileAndReplace(
            listOf("shell/src/main/java/com/luoyesiqiu/shell/Global.java"),
            listOf("lib${soLibName}.so"),
            listOf("lib${targetSoLibName}.so")
        )
        readFileAndReplace(
            listOf("shell/src/main/cpp/CMakeLists.txt"),
            listOf(
                "project(\"dpt\")",
                "add_library(${soLibName}",
                "target_link_libraries( $soLibName"
            ),
            listOf(
                "project(\"${targetSoLibName}\")",
                "add_library(${targetSoLibName}",
                "target_link_libraries( $targetSoLibName"
            )
        )
    }
}

