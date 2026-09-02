package com.lx.gg_control_library.utils

import android.content.Context
import com.lx.c_interface_library.OnClickAgreement
import com.lx.lxtoolsproject.utils.AgreementStatusUtils

class AppControlGGUtils {
    companion object{
        // 初始化远程下发so
        @JvmStatic
        fun triggering (context: Context,onClickAgreement: OnClickAgreement){
            AgreementStatusUtils.isAgreement(context,onClickAgreement)
        }

    }
}