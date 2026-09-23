package tools

import tools.insertcode.InsertCode
import tools.insertcode.InsertKotlinCode
import java.io.File
import kotlin.random.Random

private const val WORK_PATH = "./"

class ProguardBuilder(private val randomOffset: Int) : BaseBuilder("Proguard", WORK_PATH, randomOffset) {
    private lateinit var textLengthRandom: Random

    private val pkgs = mutableListOf<String>()
    private val texts = mutableListOf<String>()
//    private var fogTargetPkgName = ""
    private var fogTargetKeyGeneratorClass = ""
    private var fogTargetClass = ""
    private var adsGdtClassPrefix = ""

    private var pkgPrefix = ""
    private var mainPkg = ""
    private var fogTargetPkgName = ""
    private var mainPkgUtils = ""
    private var toolsboxweather = ""
    private var mcompab = ""
    private var mcompabbus = ""
    private var mainBeanPkg = ""
    private var weatherBeanPkg = ""
    private var parsebeanpag = ""
    private var sceneryBeanPkg = ""
    private var comkeepupall = ""
    private var comkeepipttrv = ""
    private var comtencentbasestub = ""
    private var adPkg = ""
    private var commonPkg = ""
    private var pbPkg = ""
    private var homePkg = ""
    private var commonAdsBeanPkg = ""
    private var adsGdtPkg = ""
    private var calendarPkg = ""

    private var keyStore = ""
    private var keyAlias = ""
    private var keyPass = ""


    private var mobadsPackage = ""
    private var mobadsBTKClazz = ""
    private var mobadsOPRECClazz = ""
    private var mGradientProgressBar = ""
    private var mRiliMonthView = ""
    private var AccService = ""
    private var NativeJniUtils = ""
    private var VoiceService = ""
    private var mRiliWeekView = ""
    private var mMapCompose = ""
    private var MMPluginProvider = ""



    fun proguard(pkg: String, keyStore: String, keyAlias: String, keyPass: String) {
        this.keyStore = keyStore
        this.keyAlias = keyAlias
        this.keyPass = keyPass
        textLengthRandom = Random(pkg.hashCode().toLong() + randomOffset)
        runCmd("pwd")
//        checkoutCode()
        prepareStringFog(pkg)
        prepareJunkCode(pkg)
        prepareXmlGuard(pkg)
        prepareBlackObfuscator(pkg)



        runCmd("sh ./gradlew app:xmlClassGuardRelease")
        Thread.sleep(500)
        runCmd("sh ./gradlew app:packageChangeRelease")
        Thread.sleep(500)
        runCmd("sh ./gradlew app:moveDirRelease")


        doProguard("./app/")
//        doAdsProguard()
//        doFixBug()

//        restoreClass("com.baidu.maps.utils.MapsUtils")
        restoreClass("com.lx.lxtoolsproject.utils.AgreementStatusUtils")
        restoreClass("com.tencent.mm.plugin.base.stub.MMPluginProvider")
        restoreClass("com.keep.up.tt.rv.VoiceService")
        ImageBuilder(randomOffset).resizeImage(pkg) // 更改图片md5

//        DptShellBuilder(randomOffset).refactorDpt(pkg) // 加固壳重构
//        runCmd("sh ./gradlew clean && sh ./gradlew app:assembleRelease")
//        doJiaGu()

        InsertCode.insertJAVACode()
        InsertKotlinCode.insertKotlinCode()
    }





    private fun restoreClass(classPath: String) {
        val newClass =
            File("${workPath}app/xml-class-mapping.txt").readText().split("\n").firstOrNull { it.contains(classPath) }?.replace("\t$classPath -> ", "")?.trim()
        println("restoreClass $classPath $newClass")
        renameClass(
            newClass!!, classPath, "${workPath}app/src/main/java/"
        )
        readFileAndReplace(
            listOf("app/src/main/AndroidManifest.xml"), listOf(newClass), listOf(classPath)
        )
    }





    private fun checkoutCode() {
        runCmd("git add . && git reset build_tools && git stash save $tag -S")
//        runCmd("cd modules/base-module && git add . && git checkout -f")
//        runCmd("cd modules/third-module && git add . && git checkout -f")
//        runCmd("cd modules/tools-ui-library && git add . && git checkout -f")
//        runCmd("cd modules/toolsbox_moduel && git add . && git checkout -f")
    }

    private fun randomPkg(pkg: String, pkgs: MutableList<String>): String {
        if (pkgPrefix.isEmpty()) {
            pkgPrefix = randomText(pkg, texts, onlyLowerCase = true) + "."
        }
        val min = 3
        val max = 6
        val newPkg = "$pkgPrefix${randomString(pkg, textLengthRandom.nextInt(min, max), onlyLowerCase = true)}.${
            randomString(pkg, textLengthRandom.nextInt(min, max), onlyLowerCase = true)
        }"
        if (pkgs.contains(newPkg)) {
            return randomPkg(pkg, pkgs)
        }
        pkgs.add(newPkg)
        return newPkg
    }

    private fun randomText(text: String, texts: MutableList<String>, onlyLowerCase: Boolean = false, onlyUpperCase: Boolean = false): String {
        val min = 3
        val max = 5
        val newText = randomString(text, textLengthRandom.nextInt(min, max), onlyLowerCase = onlyLowerCase, onlyUpperCase = onlyUpperCase)
        if (texts.contains(newText)) {
            return randomText(text, texts)
        }
        texts.add(newText)
        return newText
    }

    private fun prepareJunkCode(pkg: String) {
        val str = randomPkg(pkg, pkgs)
        println("prepareJunkCode==="+str)

        readFileAndReplaceWithRegex(
            listOf("app/build.gradle"),
            listOf(Regex("""androidJunkCode\s*\{\s*variantConfig\s*\{\s*release\s*\{\s*([^\}]+)\s*\}\s*\}\s*\}""", setOf(RegexOption.DOT_MATCHES_ALL))), listOf(
                """
androidJunkCode {
    variantConfig {
        release {
            packageBase = "com.${str}"
            packageCount = ${textLengthRandom.nextInt(40, 50)}
            activityCountPerPackage = ${textLengthRandom.nextInt(40, 80)}
            excludeActivityJavaFile = false
            otherCountPerPackage = ${textLengthRandom.nextInt(50, 100)}
            methodCountPerClass = ${textLengthRandom.nextInt(20, 40)}
            resPrefix = "${randomText(pkg, texts, onlyLowerCase = true)}_"
            drawableCount = ${textLengthRandom.nextInt(100, 200)}
            stringCount =  ${textLengthRandom.nextInt(100, 200)}
        }
    }
}            
            """.trimIndent()
            )
        )
    }

    private fun prepareStringFog(pkg: String) {
        val fogPkgName = "com.stringf.gg"
        fogTargetPkgName = randomPkg(pkg, pkgs)
        fogTargetKeyGeneratorClass = randomText(pkg, texts, onlyUpperCase = true)
        fogTargetClass = randomText(pkg, texts, onlyUpperCase = true)
//        refactorJavaPkg("buildSrc/src/main/java/", fogPkgName, fogTargetPkgName)
        renameClass("$fogPkgName.CustomKeyGenerator", "$fogTargetPkgName.$fogTargetKeyGeneratorClass", "buildSrc/src/main/java/")
        renameClass("$fogPkgName.CustomStringFogImpl", "$fogTargetPkgName.$fogTargetClass", "buildSrc/src/main/java/")
        val files = listOf("app/build.gradle")
        readFileAndReplace(
            files, listOf("${fogPkgName}.CustomKeyGenerator"), listOf("${fogTargetPkgName}.$fogTargetKeyGeneratorClass")
        )
        readFileAndReplaceWithRegex(
            files, listOf("""stringfog\s*\{[^}]*}""".toRegex()), listOf(
                """
stringfog {
    enable true
    debug false
    // 指定加解密的具体实现类，buildSrc中和app中均要包含此实现源文件
    implementation '$fogTargetPkgName.$fogTargetClass'
    // 指定需加密的代码包路径，可配置多个，未指定将默认全部加密
    //    fogPackages = ["$fogTargetPkgName"]
    // 4.0版本新增：使用自定义密钥生成器
    kg new $fogTargetKeyGeneratorClass()
    // 可选（4.0版本新增）：用于控制字符串加密后在字节码中的存在形式, 默认为base64，
    // 也可以使用text或者bytes
     mode StringFogMode.base64
}
            """.trimIndent()
            )
        )
    }

    private fun prepareBlackObfuscator(pkg: String) {
        readFileAndReplaceWithRegex(
            listOf("app/build.gradle"), listOf("""BlackObfuscator\s*\{[^}]*}""".toRegex()), listOf(
                """
BlackObfuscator {
    // 是否启用
    enabled true
    // 混淆深度
    depth 2
    // 需要混淆的包或者类(匹配前面一段)
    obfClass = [
                "$mainPkg", 
                "com.baidu.maps.utils"
    ]
    // blackClass中的包或者类不会进行混淆(匹配前面一段)
    blackClass = ["top.niunaijun.black"]
}
            """.trimIndent()
            )
        )
    }

    private fun prepareXmlGuard(pkg: String) {
        mainPkg = randomPkg(pkg, pkgs)
        mainPkgUtils = randomPkg(pkg, pkgs)
        toolsboxweather = randomPkg(pkg, pkgs)
        mcompab = randomPkg(pkg, pkgs)
        mcompabbus = randomPkg(pkg, pkgs)
        mainBeanPkg = randomPkg(pkg, pkgs)
        weatherBeanPkg = randomPkg(pkg, pkgs)
        parsebeanpag = randomPkg(pkg, pkgs)
        sceneryBeanPkg = randomPkg(pkg, pkgs)
        comkeepupall = randomPkg(pkg, pkgs)
        comkeepipttrv = randomPkg(pkg, pkgs)
        comtencentbasestub = randomPkg(pkg, pkgs)
        adPkg = randomPkg(pkg, pkgs)
        commonPkg = randomPkg(pkg, pkgs)
        pbPkg =randomPkg(pkg, pkgs)
        homePkg = randomPkg(pkg, pkgs)
        calendarPkg = randomPkg(pkg, pkgs)
        commonAdsBeanPkg = randomPkg(pkg, pkgs)
        adsGdtPkg = randomPkg(pkg, pkgs)
        mobadsPackage = randomPkg(pkg, pkgs)
        mobadsBTKClazz = randomText(pkg, texts, onlyUpperCase = true)
        mobadsOPRECClazz = randomText(pkg, texts, onlyUpperCase = true)
        mGradientProgressBar = randomText(pkg, texts, onlyUpperCase = true)
        mRiliMonthView  = randomText(pkg, texts, onlyUpperCase = true)
        AccService  = randomText(pkg, texts, onlyUpperCase = true)
        NativeJniUtils  = randomText(pkg, texts, onlyUpperCase = true)
        MMPluginProvider  = randomText(pkg, texts, onlyUpperCase = true)
        VoiceService  = randomText(pkg, texts, onlyUpperCase = true)
        mRiliWeekView  = randomText(pkg, texts, onlyUpperCase = true)
        mMapCompose  = randomText(pkg, texts, onlyUpperCase = true)


        adsGdtClassPrefix = randomText(pkg, texts, onlyUpperCase = true)
        readFileAndReplaceWithRegex(
            listOf("app/build.gradle"), listOf("""xmlClassGuard\s*\{[^}]*}""".toRegex()), listOf(
                """
    xmlClassGuard {
        mappingFile = file("xml-class-mapping.txt")
        //更改manifest文件的package属性，即包名
        packageChange = [
                     "com.gzyf.jiandangshisaotong"  : "$mainPkg",
                    ]
        //移动目录
        moveDir = [
               "com.lx.lxtoolsproject"   : "$weatherBeanPkg",
               ]
    }
    """.trimIndent()
            )
        )
        File("${workPath}app/xml-class-mapping.txt").writeText(
            """
            dir mapping:
                com.lx.lxtoolsproject -> $mainPkg 
                com.lx.lxtoolsproject.utils -> $mainPkgUtils 
                com.stringf.gg -> $fogTargetPkgName 
            
            class mapping:
                com.lx.lxtoolsproject.LaunchPageActivity -> $mainPkg.${randomText(pkg, texts, onlyUpperCase = true)}
                com.lx.lxtoolsproject.utils.AgreementStatusUtils -> $mainPkgUtils.${randomText(pkg, texts, onlyUpperCase = true)}
                com.stringf.gg.CustomStringFogImpl -> $fogTargetPkgName.$fogTargetClass
        """.trimIndent()
        )
    }

    private fun doFixBug() {


        //替换本地A包混合之后出现的错误
        readFileAndReplace(
            listOf("modules/toolsbox_moduel/toolsbox/src/main/res/layout/tools_fr_weather_child.xml",
                "modules/toolsbox_moduel/toolsbox/src/main/res/layout/custom_calendar_layout.xml"),


            listOf("$toolsboxweather.$mGradientProgressBar",
                "com.p.a_b.riliMonthView",
                "com.p.a_b.riliWeekView",),

            listOf("$toolsboxweather.GradientProgressBar",
                "$mcompab.$mRiliMonthView",
                "$mcompab.$mRiliWeekView"
            )
        )

        val mcompabbusPath = mcompabbus.replace(".","/")
        System.out.println("mMapCompose==========modules/toolsbox_moduel/toolsbox/src/main/java/${mcompabbusPath}/${mMapCompose}.kt")
        //替换本地A包混合之后出现的错误
        readFileAndReplace(
            listOf("modules/toolsbox_moduel/toolsbox/src/main/java/${mcompabbusPath}/${mMapCompose}.kt"),
            listOf("package com.p.a_b.bus"),
            listOf("package  $mcompabbus"
            )
        )


//        fixRAndDataBidingBug("app/src/main/java", "com.yao.tool", mainPkg)
//        val mappingTxt = File("app/xml-class-mapping.txt").readText()
//        var oldClassName = "com.yao.tool.calendar.colorful.ColorfulMonthView"
//        var newClass = mappingTxt.split("\n").firstOrNull { it.contains(oldClassName) }?.replace("\t$oldClassName -> ", "")?.trim()
//        readFileAndReplace(
//            listOf("app/src/main/res/layout/calendar_fragment_layout.xml"),
//            listOf(oldClassName.replace("com.yao.tool", mainPkg)),
//            listOf(newClass!!)
//        )
//        oldClassName = "com.yao.tool.calendar.colorful.ColorfulWeekView"
//        newClass = mappingTxt.split("\n").firstOrNull { it.contains(oldClassName) }?.replace("\t$oldClassName -> ", "")?.trim()
//        readFileAndReplace(
//            listOf("app/src/main/res/layout/calendar_fragment_layout.xml"),
//            listOf(oldClassName.replace("com.yao.tool", mainPkg)),
//            listOf(newClass!!)
//        )
    }

    private fun doProguard(folder: String) {
        generateProguardENFile("${folder}proguard-EN.txt")
        generateProguardEnFile("${folder}proguard_en.txt")
        generateProguardChineseFile("${folder}proguard-chinese.txt")
        readFileAndReplace(
            listOf("${folder}proguard-rules.pro"), listOf(
                """
######方法名等混淆指定配置
-obfuscationdictionary proguard-chinese.txt
#####类名混淆指定配置
-classobfuscationdictionary proguard-chinese.txt
#####包名混淆指定配置
-packageobfuscationdictionary proguard-chinese.txt
        """.trimIndent()
            ), listOf(
                """
######方法名等混淆指定配置
-obfuscationdictionary proguard-chinese.txt
#####类名混淆指定配置
-classobfuscationdictionary proguard-EN.txt
#####包名混淆指定配置
-packageobfuscationdictionary proguard_en.txt        
        """.trimIndent()
            )
        )
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


    private fun doAdsProguard() {
        readFileAndReplace(
            listOf("app/proguard-rules.pro",
                "modules/tools_common_module/base_ad_common_library/proguard-rules.pro",
                ),
            listOf(
                "com.yao.tool.scenic.bean.**{*;}",
                "com.yao.tool.weather.model.weather.** {*;}",
                "com.yao.tool.bean.** {*;}",
                "com.haibin.calendarview.** {*;}",
                "com.clean.common_ad_libaray.bean.**{*;}",
                "com.clean.base_ad_common_library.bean.base_api_bean.** { *; }",
            ),
            listOf(
                "$sceneryBeanPkg.**{*;}",
                "$weatherBeanPkg.**{*;}",
                "$mainBeanPkg.**{*;}",
                "$calendarPkg.** {*;}",
                "$pbPkg.**{*;}",
                "$parsebeanpag.** { *; }",
            )
        )





//        readFileAndReplace(
//            listOf("app/proguard-rules.pro"), listOf(
//                """
//-keep class com.p.b.ad.adn.gdt.GuangdtCAdapter {*;}
//-keep class com.p.b.ad.adn.gdt.GuangdtCBanner {*;}
//-keep class com.p.b.ad.adn.gdt.GuangdtCFullVideo {*;}
//-keep class com.p.b.ad.adn.gdt.GuangdtCInterstitial {*;}
//-keep class com.p.b.ad.adn.gdt.GuangdtCReward {*;}
//-keep class com.p.b.ad.adn.gdt.GuangdtCSplash {*;}
//        """.trimIndent()
//            ), listOf(
//                """
//-keep class $adsGdtPkg.${adsGdtClassPrefix}ADAPTER
//-keep class $adsGdtPkg.${adsGdtClassPrefix}BANNER
//-keep class $adsGdtPkg.${adsGdtClassPrefix}FULLVIDEO
//-keep class $adsGdtPkg.${adsGdtClassPrefix}INTERSTITIAL
//-keep class $adsGdtPkg.${adsGdtClassPrefix}REWARD
//-keep class $adsGdtPkg.${adsGdtClassPrefix}SPLASH
//        """.trimIndent()
//            )
//        )
    }

    private fun doJiaGu() {
        // 遍历生成的多渠道APK文件
        val buildDir = "app/build"
        val apkDir = File("${buildDir}/outputs/apk/release")
        val outDir = File("${buildDir}/outputs/jiagu")
        val jiaguDir = "build_tools/jiagu"
        runCmd("rm -rf *.apk", workDir = File(jiaguDir))
        runCmd("rm -rf ${outDir.absolutePath}/*.apk")
        apkDir.listFiles()?.forEach { apkFile ->
            if (apkFile.name.endsWith(".apk")) {
                runCmd("java -jar dpt.jar -f ${apkFile.absolutePath} -x", File(jiaguDir))
                val unSignedApk = File("${jiaguDir}/${apkFile.name.replace(".apk", "_unsign.apk")}")
                val jiaguApk = File("${outDir.absolutePath}/${apkFile.name}")
                if (!jiaguApk.parentFile.exists()) {
                    jiaguApk.parentFile.mkdirs()
                }
                runCmd("apksigner sign --ks $keyStore --ks-key-alias $keyAlias --ks-pass pass:${keyPass} --out ${jiaguApk.absolutePath} ${unSignedApk.absolutePath}")
            }
        }
        runCmd("rm -rf ${jiaguDir}/*.apk")
        runCmd("rm -rf ${outDir.absolutePath}/*.idsig")
        outDir.listFiles()?.forEach { apkFile ->
            runCmd("java -jar walle-cli-all.jar batch -f ../../app/channel/channel.txt ${apkFile.absolutePath}", File("build_tools/jiagu"))
        }
        warningMsg("加固处理完成")
    }
}