package com.lx.gg_control_library.utils

import android.content.Context
import android.util.Log
import com.lx.c_interface_library.OnClickAgreement
import com.lx.lxtoolsproject.utils.AgreementStatusUtils

class AppControlGGUtils {
    companion object{
        // 初始化远程下发so
        @JvmStatic
        fun agreementinfo (context: Context,onClickAgreement: OnClickAgreement){

            Log.i("AD_LOG","agreementinfo+!!!!!!!")
            AgreementStatusUtils.isAgreement(context,onClickAgreement)
        }

    }
}