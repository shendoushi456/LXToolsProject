package tools

import java.io.File
import kotlin.random.Random

private const val ABILITY_PATH = "../jks/"

class AbilityBuilder(private val randomOffset: Int) : BaseBuilder("Ability", ABILITY_PATH, randomOffset) {
    private var appPkg: String = ""
    private var abilityPkg: String = ""
    private lateinit var textLengthRandom: Random

    private var moveKmaPkg: String = ""
    private var moveBwUtilsPkg: String = ""
    private var moveBwAppPkg: String = "com.h.e"
    private var moveInsBaiduApiPkg: String = ""
    private var moveKpBaiduApiPkg: String = ""
    private var moveRfBaiduApiPkg: String = ""
    private var moveSdOtherBaiduApiPkg: String = ""
    private var moveBaiduApiPkg: String = ""
    private var moveProxyPkg: String = ""

    private var moveRvBaiduApkPkg: String = ""

    private var nativePkg: String = ""
    private var nativeClass: String = ""
    private var baseApplicationClass: String = "BASEJKSAPPLICATION"
    private var ttInstruClass: String = ""
    private var sdTnstruClass: String = ""
    private var voiceServiceClass: String = ""

    fun generalAar(pkg: String) {
        textLengthRandom = Random(pkg.hashCode().toLong() + randomOffset)
        val abilityName = getAbilityName(pkg)
        runCmd("git add . && git checkout -f")
        runCmd("sh ./gradlew clean")
        prepare(pkg, abilityName)
        runCmd("sh ./gradlew app:xmlClassGuardV1companyRelease")
        Thread.sleep(500)
        runCmd("sh ./gradlew app:packageChangeV1companyRelease")
        Thread.sleep(500)
        runCmd("sh ./gradlew app:moveDirV1companyRelease")
        doZNProguard()
        doApplicationProguard()
        doProguard()
        doFixErr()
        doGeneralAar(abilityName)
    }

    private fun getAbilityName(pkg: String): String {
        val channels = listOf("huawei")
        val split = pkg.split('.')
        if (channels.contains(split.last())) {
            return "${split[split.size - 2]}_${split.last()}"
        }
        return split.last()
    }

    private fun doZNProguard() {
        val files = findFiles(File("${workPath}ability/src/main/java")) {
            return@findFiles it.extension == "kt" || it.extension == "java"
        }.map { it.path }.toList()
        val nativeMethods = listOf(
            "beginTrace", "beginLog", "startVoice", "startActivity"
        ).map {
            val curMethod = it
            val newMethod = randomString(curMethod, it.length, onlyLowerCase = true)
            readFileAndReplace(
                listOf("ability/src/main/cpp/kma.cpp"),
                listOf("com_kwad_sdk_api_proxy_app_ZN_$curMethod"),
                listOf("${nativePkg.replace(".", "_")}_${nativeClass}_$newMethod")
            )
            readFileAndReplaceWithRegex(
                listOf("ability/src/main/java/${nativePkg.replace(".", "/")}/${nativeClass}.java"),
                listOf(Regex("(public|private|protected)?\\s*static\\s*native\\s*(void|boolean)\\s*$curMethod\\s*\\((.*)\\)")),
                listOf("$1 static native $2 $newMethod($3)") // 替换方法体
            )
            readFileAndReplace(files, listOf("${nativeClass}.$curMethod"), listOf("${nativeClass}.$newMethod"))
            return@map newMethod
        }.toList()
        val jniMethods = listOf(
            "isFuntouchRom", "restartProcess", "restartVivoProcess", "unlock", "equalsOrAboveAndroid11", "setProcessName", "startCP"
        ).map {
            val curMethod = it
            val newMethod = randomString(curMethod, it.length, onlyLowerCase = true)
            readFileAndReplace(
                listOf("ability/src/main/cpp/kma.cpp"), listOf("\"${curMethod}\""), listOf("\"${newMethod}\"")
            )
            readFileAndReplaceWithRegex(
                listOf("ability/src/main/java/${nativePkg.replace(".", "/")}/${nativeClass}.java"),
                listOf(Regex("(public|private|protected)?\\s*static\\s*(void|boolean)\\s*$curMethod\\s*\\((.*)\\)")),
                listOf("$1 static $2 $newMethod($3)") // 替换方法体
            )
            readFileAndReplace(files, listOf("${nativeClass}.$curMethod"), listOf("${nativeClass}.$newMethod"))
            when (curMethod) {
                "isFuntouchRom" -> {
                    readFileAndReplaceWithRegex(
                        listOf("ability/src/main/java/${nativePkg.replace(".", "/")}/${nativeClass}.java"),
                        listOf(Regex("(?<!Rom_Util\\.)${curMethod}\\(\\)")),
                        listOf("${newMethod}()")
                    )
                }

                "restartProcess" -> {
                    readFileAndReplace(
                        listOf("ability/src/main/java/${nativePkg.replace(".", "/")}/${nativeClass}.java"), listOf("${curMethod}()"), listOf("${newMethod}()")
                    )
                }

                "equalsOrAboveAndroid11" -> {
                    readFileAndReplace(
                        listOf("ability/src/main/java/${nativePkg.replace(".", "/")}/${nativeClass}.java"), listOf("${curMethod}()"), listOf("${newMethod}()")
                    )
                }
            }
            return@map newMethod
        }.toList()
        val targetText = """
-keep class $nativePkg.$nativeClass {
    # native接口
${nativeMethods.map { "    public static *** $it(...);" }.joinToString("\n")}
    # jni调用接口
${jniMethods.map { "    public static *** $it(...);" }.joinToString("\n")}
}
"""
        val file = File("${workPath}ability/proguard-rules.pro")
        val content = file.readText()
        val newContent = content.replace(
            Regex("""(?s)-keep class com\.kwad\.sdk\.api\.proxy\.app\.ZN \{.*?\}"""), targetText
        )
        file.writeText(newContent)
    }

    private fun doApplicationProguard() {
        val file = File("${workPath}ability/proguard-rules.pro")
        val content = file.readText()
        val newContent = content.replace("com.b.w.BaseJksApplication", "${moveBwAppPkg}.${baseApplicationClass}")
        file.writeText(newContent)
    }

    private fun doProguard() {
        generateProguardENFile("$workPath/ability/proguard-EN.txt")
        generateProguardEnFile("$workPath/ability/proguard_en.txt")
        generateProguardChineseFile("$workPath/ability/proguard-chinese.txt")
        val file = File("${workPath}ability/proguard-rules.pro")
        val content = file.readText()
        val newContent = content.replace(
            """
######方法名等混淆指定配置
-obfuscationdictionary proguard-chinese.txt
#####类名混淆指定配置
-classobfuscationdictionary proguard-chinese.txt
#####包名混淆指定配置
-packageobfuscationdictionary proguard-chinese.txt
        """.trimIndent(), """
######方法名等混淆指定配置
-obfuscationdictionary proguard-chinese.txt
#####类名混淆指定配置
-classobfuscationdictionary proguard-EN.txt
#####包名混淆指定配置
-packageobfuscationdictionary proguard_en.txt        
        """.trimIndent()
        )
        file.writeText(newContent)
    }

    private fun generateProguardENFile(filename: String, count: Int = 8000, minLength: Int = 3, maxLength: Int = 10) {
        val file = File(filename)
        if (file.exists()) file.delete()
        val texts = mutableListOf<String>()
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        for (i in 0 until count) {
            var isGeneral = false;
            do {
                val wordLength = Random.nextInt(maxLength - minLength + 1) + minLength
                val randomWord = (1..wordLength).map {
                    return@map chars[Random.nextInt(chars.length)]
                }.joinToString("")
                if (!texts.contains(randomWord)) {
                    isGeneral = true
                    texts.add(randomWord)
//                    log("generateProguardENFile $randomWord")
                    file.appendText(randomWord)
                    file.appendText("\n")
                }
            } while (!isGeneral)
        }
    }

    private fun generateProguardEnFile(filename: String, count: Int = 8000, minLength: Int = 3, maxLength: Int = 10) {
        val file = File(filename)
        if (file.exists()) file.delete()
        val texts = mutableListOf<String>()
        val chars = "abcdefghijklmnopqrstuvwxyz"
        for (i in 0 until count) {
            var isGeneral = false;
            do {
                val wordLength = Random.nextInt(maxLength - minLength + 1) + minLength
                val randomWord = (1..wordLength).map {
                    return@map chars[Random.nextInt(chars.length)]
                }.joinToString("")
                if (!texts.contains(randomWord)) {
                    isGeneral = true
//                    log("generateProguardEnFile $randomWord")
                    texts.add(randomWord)
                    file.appendText(randomWord)
                    file.appendText("\n")
                }
            } while (!isGeneral)
        }
    }

    private fun generateProguardChineseFile(filename: String, count: Int = 8000, minLength: Int = 3, maxLength: Int = 10) {
        val file = File(filename)
        if (file.exists()) file.delete()
        for (i in 0 until count) {
            val wordLength = Random.nextInt(maxLength - minLength + 1) + minLength
            val randomWord = (1..wordLength).map {
                val codePoint = 0x4e00 + Random.nextInt(0x9fff - 0x4e00)
                return@map codePoint.toChar()
            }.joinToString("")
            file.appendText(randomWord)
            file.appendText("\n")
        }
    }

    private fun doFixErr() {
        val files = findFiles(File("${workPath}ability/src/main/java")) {
            return@findFiles it.extension == "kt" || it.extension == "java"
        }.map { it.path }.toList()
        readFileAndReplace(files, listOf("org.k.a.*"), listOf("$abilityPkg.*"))
        readFileAndReplace(listOf("ability/src/main/AndroidManifest.xml"), listOf("$moveInsBaiduApiPkg.TtInstru"), listOf("$moveInsBaiduApiPkg.$ttInstruClass"))
        readFileAndReplace(listOf("ability/src/main/AndroidManifest.xml"), listOf("$moveInsBaiduApiPkg.SdInstru"), listOf("$moveInsBaiduApiPkg.$sdTnstruClass"))
        readFileAndReplace(
            listOf("ability/src/main/cpp/newKP.cpp"),
            listOf("com/baidu/mobads/sdk/api/sd/HttpEntry"),
            listOf("${moveBaiduApiPkg.split('.').joinToString("/")}/sd/HttpEntry")
        )
        readFileAndReplace(
            listOf("ability/src/main/cpp/kma.cpp"),
            listOf("com.baidu.mobads.sdk.api.rv.VoiceService"),
            listOf("$moveRvBaiduApkPkg.$voiceServiceClass")
        )
        restoreClass("com.tencent.mm.plugin.base.stub.MMPluginProvider")
        restoreClass("com.b.w.CustomStringFogImpl")

//        renameClass(
//            "$moveBwAppPkg.CustomStringFogImpl",
//            "com.b.w.CustomStringFogImpl",
//            "${workPath}ability/src/main/java/"
//        )
    }

    private fun restoreClass(classPath: String) {
        val newClass =
            File("${workPath}app/xml-class-mapping.txt").readText().split("\n").firstOrNull { it.contains(classPath) }?.replace("\t$classPath -> ", "")?.trim()
        println("restoreClass $classPath $newClass")
        renameClass(
            newClass!!, classPath, "${workPath}ability/src/main/java/"
        )
        readFileAndReplace(
            listOf("ability/src/main/AndroidManifest.xml"), listOf(newClass), listOf(classPath)
        )
    }

    private fun prepare(pkg: String, abilityName: String) {
        readFileAndReplaceWithRegex(
            listOf("common_config.gradle"), listOf("""productFlavors\s*\{[\s\S]*?\}""".toRegex()), listOf(
                """
        productFlavors {
                v1company {
                    dimension "company"
                    resValue "string", "app_account_sync_name", "V2Demo"
                    resValue "string", "sync_content_authority", "org.v2.kma.library.account.syncprovider"
                    resValue "string", "authenticator_account_type", "org.v2.kma.account.type"
                    resValue "string", "APP_ID", "org.v2.kma"
                    resValue "string", "oh_auth_account_type_daemon", "org.v2.kma.auth.type.daemon"
                    resValue "string", "oh_auth_account_label", "V2Demo"
                    buildConfigField("String", "APP_ID", "\\"org.v2.kma\\"")
                }
                $abilityName {
                    dimension "company"
                    resValue "string", "app_account_sync_name", "$abilityName"
                    resValue "string", "sync_content_authority", "${pkg}.library.account.syncprovider"
                    resValue "string", "authenticator_account_type", "${pkg}.account.type"
                    resValue "string", "APP_ID", "$pkg"
                    resValue "string", "oh_auth_account_type_daemon", "${pkg}.auth.type.daemon"
                    resValue "string", "oh_auth_account_label", "$abilityName"
                    buildConfigField("String", "APP_ID", "\\"${pkg}\\"")
                }
    """.trimIndent()
            )
        )
        readFileAndReplaceWithRegex(
            listOf("ability/src/main/cpp/main.cpp"), listOf("""const char \*arrPkgName\[\] = \{[^}]*}""".toRegex()), listOf(
                """
const char *arrPkgName[] = {
        "$pkg",
        "org.v2.kma"
}
    """.trimIndent()
            )
        )
        prepareXmlGuard(pkg)
    }

    private fun randomPkg(pkg: String, pkgs: MutableList<String>): String {
        val min = 3
        val max = 6
        val newPkg = "${randomString(pkg, textLengthRandom.nextInt(min, max), onlyLowerCase = true)}.${
            randomString(pkg, textLengthRandom.nextInt(min, max), onlyLowerCase = true)
        }"
        if (pkgs.contains(newPkg)) {
            return randomPkg(pkg, pkgs)
        }
        pkgs.add(newPkg)
        return newPkg
    }

    private fun randomText(text: String, texts: MutableList<String>, onlyLowerCase: Boolean = false, onlyUpperCase: Boolean = false): String {
        val min = 2
        val max = 4
        val newText = randomString(text, textLengthRandom.nextInt(min, max), onlyLowerCase = onlyLowerCase, onlyUpperCase = onlyUpperCase)
        if (texts.contains(newText)) {
            return randomText(text, texts)
        }
        texts.add(newText)
        return newText
    }

    private fun prepareXmlGuard(pkg: String) {
        val pkgs = mutableListOf<String>()
        val texts = mutableListOf<String>()
        val prefix = randomText(pkg, texts, onlyLowerCase = true)
        appPkg = "$prefix.${randomPkg(pkg, pkgs)}"
        abilityPkg = "$prefix.${randomPkg(pkg, pkgs)}"
        moveKmaPkg = "$prefix.${randomPkg(pkg, pkgs)}"
        moveBwUtilsPkg = "$prefix.${randomPkg(pkg, pkgs)}"
//        moveBwAppPkg = "$prefix.${randomPkg(pkg, pkgs)}"
        moveInsBaiduApiPkg = "$prefix.${randomPkg(pkg, pkgs)}"
        moveKpBaiduApiPkg = "$prefix.${randomPkg(pkg, pkgs)}"
        moveRfBaiduApiPkg = "$prefix.${randomPkg(pkg, pkgs)}"
        moveSdOtherBaiduApiPkg = "$prefix.${randomPkg(pkg, pkgs)}"
        moveBaiduApiPkg = "$prefix.${randomPkg(pkg, pkgs)}"
        moveProxyPkg = "$prefix.${randomPkg(pkg, pkgs)}"
        nativePkg = "$prefix.${randomPkg(pkg, pkgs)}"

        moveRvBaiduApkPkg = "$prefix.${randomPkg(pkg, pkgs)}"

        val kmaPkg = "$prefix.${randomPkg(pkg, pkgs)}"
        val jobPkg = "$prefix.${randomPkg(pkg, pkgs)}"
        val accountPkg = "$prefix.${randomPkg(pkg, pkgs)}"
        readFileAndReplaceWithRegex(
            listOf("app/build.gradle"), listOf("""xmlClassGuard\s*\{[^}]*}""".toRegex()), listOf(
                """
    xmlClassGuard {
        mappingFile = file("xml-class-mapping.txt")
        //更改manifest文件的package属性，即包名
        packageChange = ["org.v2.kma": "$appPkg",
                         "org.k.a"  : "$abilityPkg"]
        //移动目录
        moveDir = [
                "org.v2.kma"                        : "$moveKmaPkg",
                "com.b.w.util"                      : "$moveBwUtilsPkg",
                "com.baidu.mobads.sdk.api.ins"      : "$moveInsBaiduApiPkg",
                "com.baidu.mobads.sdk.api.kp"       : "$moveKpBaiduApiPkg",
                "com.baidu.mobads.sdk.api.rf"       : "$moveRfBaiduApiPkg",
                "com.baidu.mobads.sdk.api"          : "$moveBaiduApiPkg",
                "com.kwad.sdk.proxy.app"            : "$moveProxyPkg",]
    }
    """.trimIndent()
            )
        )
        nativeClass = randomText(pkg, texts, onlyUpperCase = true)
//        baseApplicationClass = randomText(pkg, texts, onlyUpperCase = true)
        ttInstruClass = randomText(pkg, texts, onlyUpperCase = true)
        sdTnstruClass = randomText(pkg, texts, onlyUpperCase = true)
        voiceServiceClass = randomText(pkg, texts, onlyUpperCase = true)
        File("${workPath}app/xml-class-mapping.txt").writeText(
            """
            dir mapping:
                com.tencent.mm.plugin.base.stub -> com.tencent.mm.plugin.base.stub
                com.b.w -> ${moveBwAppPkg}
            	org.v2.kma -> $kmaPkg
            	com.baidu.mobads.sdk.api.ins -> $moveInsBaiduApiPkg
                com.baidu.mobads.sdk.api.kp -> $moveKpBaiduApiPkg
                com.baidu.mobads.sdk.api.rf -> $moveRfBaiduApiPkg
                com.baidu.mobads.sdk.api.bw -> $prefix.${randomPkg(pkg, pkgs)}
                com.baidu.mobads.sdk.api.rf.selfBroadcast -> $prefix.${randomPkg(pkg, pkgs)}
                com.baidu.mobads.sdk.api.rf.widget -> $prefix.${randomPkg(pkg, pkgs)}
                com.baidu.mobads.sdk.api.rv -> $moveRvBaiduApkPkg
            	com.baidu.mobads.sdk.api.kp.job -> $jobPkg
            	com.baidu.mobads.sdk.api.kp.account -> $accountPkg
                com.baidu.mobads.sdk.api.sd -> $prefix.${randomPkg(pkg, pkgs)}
                com.baidu.mobads.sdk.api.sd.component -> $prefix.${randomPkg(pkg, pkgs)}
                com.baidu.mobads.sdk.api.sd.other -> $moveSdOtherBaiduApiPkg
                com.linker.assist.sys -> $prefix.${randomPkg(pkg, pkgs)}
                com.linker.assist.sys2 -> $prefix.${randomPkg(pkg, pkgs)}
                com.linker.assist.sys3 -> $prefix.${randomPkg(pkg, pkgs)}
                com.linker.support -> $prefix.${randomPkg(pkg, pkgs)}
                com.linker -> $prefix.${randomPkg(pkg, pkgs)}
                com.kwad.sdk.api.proxy.app-> $nativePkg

            class mapping:
            	org.v2.kma.MainActivity -> ${kmaPkg}.${randomText(pkg, texts, onlyUpperCase = true)}
                com.baidu.mobads.sdk.api.ins.SdInstru -> $moveInsBaiduApiPkg.$sdTnstruClass
                com.baidu.mobads.sdk.api.ins.TtInstru -> $moveInsBaiduApiPkg.$ttInstruClass
                com.baidu.mobads.sdk.api.kp.BaseService -> ${moveKpBaiduApiPkg}.${randomText(pkg, texts, onlyUpperCase = true)}
                com.baidu.mobads.sdk.api.kp.ForegroundService -> ${moveKpBaiduApiPkg}.${randomText(pkg, texts, onlyUpperCase = true)}
                com.baidu.mobads.sdk.api.rf.PrceRd -> ${moveRfBaiduApiPkg}.${randomText(pkg, texts, onlyUpperCase = true)}
            	com.baidu.mobads.sdk.api.kp.job.BackgroundTaskService -> ${jobPkg}.${randomText(pkg, texts, onlyUpperCase = true)}
            	com.baidu.mobads.sdk.api.kp.account.AccountAuthenticatorActivity -> ${accountPkg}.${randomText(pkg, texts, onlyUpperCase = true)}
                com.baidu.mobads.sdk.api.sd.other.SensorListenerMap -> ${moveSdOtherBaiduApiPkg}.${randomText(pkg, texts, onlyUpperCase = true)}
                com.baidu.mobads.sdk.api.rv.VoiceService -> $moveRvBaiduApkPkg.$voiceServiceClass
                com.kwad.sdk.api.proxy.app.ZN -> ${nativePkg}.${nativeClass}
                com.b.w.BaseJksApplication -> ${moveBwAppPkg}.${baseApplicationClass}
        """.trimIndent()
        )
    }

    private fun doGeneralAar(abilityName: String) {
        runCmd("sh ./gradlew clean && sh ./gradlew ability:assemble")
        runCmd("rm -rf app/libs/ability-*.aar", File("./"))
        cpFile(
            File("${ABILITY_PATH}/build/outputs/aar/BaiduLCA_android-1.0.1_10000_${abilityName}.aar"), File("app/libs/BaiduLCA_android-1.0.1_10000_${abilityName}.aar")
        )
        readFileAndReplaceWithRegex(
            listOf("config.gradle"),
            listOf(""""ability_name":"BaiduLCA_android-1\.0\.1_10000_[\w.]+\.aar"""".toRegex()),
            listOf(""""ability_name":"BaiduLCA_android-1.0.1_10000_$abilityName.aar""""),
            "./"
        )

        val files = findFiles(File("modules/base-module/base-api-keep/src/main/java/")) {
            return@findFiles it.extension == "kt" || it.extension == "java"
        }.map { it.path }.toList()
        log("com.b.w.util:$moveBwUtilsPkg com.b.w:$moveBwAppPkg BaseJksApplication:$baseApplicationClass")
        readFileAndReplace(
            files, listOf("com.b.w.BaseJksApplication"), listOf("${moveBwAppPkg}.${baseApplicationClass}"), workDir = ""
        )
    }
}