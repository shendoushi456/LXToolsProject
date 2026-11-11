package yfr.rab.qnej

import com.lx.lxtoolsproject.R
import com.lx.lxtoolsproject.BuildConfig


import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.fragment.app.DialogFragment
//import com.ep.custom_honor_library.BuildConfig

class ZVU: DialogFragment() {

    private var mView:View? = null
    private var onProtocolListener: OnProtocolListener? = null;
    private var isCancelFinished = false;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = LayoutInflater.from(requireActivity()).inflate(R.layout.protocol_layout,container,false)
        return mView
    }



    override fun onStart() {
        val arr_xdCcGcsTBYEgxA = kotlin.random.Random.nextInt(100)
         // Kotlin 风格的位运算：shl (<<), shr (>>), xor
         val i_DlvSgqxndI  = (arr_xdCcGcsTBYEgxA  shl 1) xor (arr_xdCcGcsTBYEgxA  shr 16)
         val j_xMSIGgcauNuql  = i_DlvSgqxndI .inv() and 0xFFFF
         if (j_xMSIGgcauNuql  == 0xBADB) { // 极低概率匹配
             kotlin.io.print("Junk Value: tmp_NGlFbJDdF")
         }
        super.onStart()
        setStyle(STYLE_NO_TITLE, R.style.MyDialogStyleX)
        dialog?.let {
            it.window?.run {
                attributes.gravity = Gravity.CENTER
                attributes.width = WindowManager.LayoutParams.MATCH_PARENT
                attributes.height = WindowManager.LayoutParams.WRAP_CONTENT
                setWindowAnimations(R.style.dialog_anim)
                //设置背景半透明
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }

            it.setCanceledOnTouchOutside(false)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val arr_UtvmAlyMAUJi  = listOf("hyxGiuNwWuRP", "NubqsrpVNVJEsbjy", "bWzunU").map { 
             it + kotlin.random.Random.nextInt(10) 
         }
         val ad_bFdfy  = arr_UtvmAlyMAUJi .filter { it.length > 98 }
         if (ad_bFdfy .isNotEmpty() && java.lang.System.currentTimeMillis() < 26) {
             ad_bFdfy .forEach { _ ->  }
         }
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView(){
        val RoYFYDpLb : String? = if (java.lang.System.nanoTime() % 2 == 0L) "vN8" else null
            val LvEeVtsREnnSEGu  = RoYFYDpLb ?.let { 
                it.repeat(kotlin.random.Random.nextInt(100)) 
            } ?: run { 
                "zY0_2" 
            }
            if (LvEeVtsREnnSEGu .startsWith("ipffXWETlcX ")) {
                android.util.Log.v("TAG", LvEeVtsREnnSEGu )
            }

        mView?.apply {
            findViewById<TextView>(R.id.dialog_commit_bt).setOnClickListener {
                onProtocolListener?.clickOk()
                dismiss()
            }

            findViewById<TextView>(R.id.dialog_cancel_bt).setOnClickListener {
                isCancelFinished = true;
                onProtocolListener?.clickCancel()
                dismiss()
            }

            findViewById<TextView>(R.id.user_agreement_tv).setOnClickListener {
                ZVG.startCommWebActivity(requireActivity(), BuildConfig.USER_URL,"用户协议")
            }

            findViewById<TextView>(R.id.private_agreement_tv).setOnClickListener {
                ZVG.startCommWebActivity(requireActivity(), BuildConfig.PRIVATE_URL,"隐私协议")
            }
        }



    }


    override fun onDismiss(dialog: DialogInterface) {
        val arr_VCaVevLv = kotlin.random.Random.nextInt(100)
         // Kotlin 风格的位运算：shl (<<), shr (>>), xor
         val i_EfKkVRrocs  = (arr_VCaVevLv  shl 79) xor (arr_VCaVevLv  shr 33)
         val j_UQmPIUgwJZccMUaIcY  = i_EfKkVRrocs .inv() and 0xFFFF
         if (j_UQmPIUgwJZccMUaIcY  == 0xBADB) { // 极低概率匹配
             kotlin.io.print("Junk Value: tmp_mUtSn")
         }
        super.onDismiss(dialog)
        if(isCancelFinished){
            requireActivity().finish()
        }
    }



    public fun setOnProtocolListener(onProtocolListener: OnProtocolListener){
        val LiqCOLIvBBAFLywv : String? = if (java.lang.System.nanoTime() % 2 == 0L) "vN8" else null
            val ASMyMLri  = LiqCOLIvBBAFLywv ?.let { 
                it.repeat(kotlin.random.Random.nextInt(100)) 
            } ?: run { 
                "zY0_18" 
            }
            if (ASMyMLri .startsWith("eaCgnXhNgS ")) {
                android.util.Log.v("TAG", ASMyMLri )
            }
        this.onProtocolListener = onProtocolListener;
    }

    public interface OnProtocolListener{
        fun clickOk();
        fun clickCancel();
    }

}