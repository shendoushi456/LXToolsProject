package tools

fun main(args: Array<String>) {
    val supportFunAbility = "--ability"
    val supportProguardAbility = "--proguard"
    val supportFunJiaGu = "--jiagu"
    val supportFunPkg = "--pkg"
    val supportOffset = "--offset"
    val supportFunResetGit = "--resetgit"
    val supportFunAllPull = "--allpull"
    val pkg = args.firstOrNull { it.contains(supportFunPkg) }?.replace("${supportFunPkg}=", "")
    val hasAbility = args.firstOrNull { it == supportFunAbility } != null
    val hasProguard = args.firstOrNull { it == supportProguardAbility } != null
    val hasResetGit = args.firstOrNull { it == supportFunResetGit } != null
    val hasAllPull = args.firstOrNull { it == supportFunAllPull } != null
    val hasJiaGu = args.firstOrNull { it == supportFunJiaGu } != null

    //一键还原代码
    if (hasResetGit){
        ResetGitBuilder().initGit()
        return
    }


    //一键更新所有模块代码
    if (hasAllPull){
        ResetGitBuilder().allPullDev()
        return
    }




    val randomOffset =
        args.firstOrNull { it.contains(supportOffset) }?.replace("${supportOffset}=", "")?.toInt()
            ?: 0


    if (pkg == null || (!hasAbility && !hasJiaGu && !hasProguard)) {
        throw Exception(
            """
            ---------------------------------------------------------
            Example: ${supportFunPkg}=com.shanxiyunhui.shiyongji $supportFunAbility $supportOffset=0
            Example: ${supportFunPkg}=com.shanxiyunhui.shiyongji $supportFunJiaGu $supportOffset=0
            Example: ${supportFunPkg}=com.shanxiyunhui.shiyongji $supportProguardAbility $supportOffset=0
            ---------------------------------------------------------
        """.trimIndent()
        )
    }


    if (hasJiaGu) {
        DptShellBuilder(randomOffset).refactorDpt(pkg)
        return
    }



    if (hasProguard) {
        val keyStoreArg = "--ks"
        val keyAliasArg = "--ks-key-alias"
        val keyPassArg = "--ks-pass"
        val keyStore = args.firstOrNull { it.contains(keyStoreArg) }?.replace("${keyStoreArg}=", "") ?: ""
        val keyAlias = args.firstOrNull { it.contains(keyAliasArg) }?.replace("${keyAliasArg}=", "") ?: ""
        val keyPass = args.firstOrNull { it.contains(keyPassArg) }?.replace("${keyPassArg}=", "") ?: ""
        ProguardBuilder(randomOffset).proguard(pkg, keyStore, keyAlias, keyPass)
//    ProguardBuilder(0).proguard("com.lnjrk.zhimou", "/Users/wfx/backup/work/lark/ab/AppAdk_B_New.git/app/zhimou.jks", "zhimou", "123456")
        return
    }
    if (hasAbility) {
        AbilityBuilder(randomOffset).generalAar(pkg)
        return
    }
}
