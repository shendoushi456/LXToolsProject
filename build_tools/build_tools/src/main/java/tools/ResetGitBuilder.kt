package tools

private const val WORK_PATH = "./"
class ResetGitBuilder :BaseBuilder("ResetGit",WORK_PATH,1) {
   fun initGit(){
       log("hasResetGit===initGit")

        runCmd("git add . && git checkout -f")
        runCmd("cd modules && git add . && git checkout -f")
        runCmd("cd modules/third-module && git add . && git checkout -f")
        runCmd("cd modules/custom_honor_ad_library/c_interface_library && git add . && git checkout -f")
        runCmd("cd modules/custom_honor_ad_library/custom_honor_library && git add . && git checkout -f")
        runCmd("cd modules/modulesUI/third-module && git add . && git checkout -f")
        runCmd("cd modules/modulesUI/tools-ui-library && git add . && git checkout -f")
        runCmd("cd modules/modulesUI/toolsbox_moduel && git add . && git checkout -f")
    }



    fun allPullDev(){
        log("hasResetGit===allPullDev")

        runCmd("git pull")
        runCmd("cd bae-ad-modules && git pull")
        runCmd("cd modules/third-module && git pull")
        runCmd("cd modules/tools-ui-library && git pull")
        runCmd("cd modules/toolsbox_moduel && git pull")
        runCmd("cd modules/tools_common_module && git pull")


    }
}