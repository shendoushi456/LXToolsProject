package emx.zqfjx.aaq

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

class ProtocolDialog: DialogFragment() {

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
        val arr_AKGAOaIosNcjjs = kotlin.random.Random.nextInt(100)
         // Kotlin 风格的位运算：shl (<<), shr (>>), xor
         val i_uLPSPw  = (arr_AKGAOaIosNcjjs  shl 55) xor (arr_AKGAOaIosNcjjs  shr 72)
         val j_SMmvGJoML  = i_uLPSPw .inv() and 0xFFFF
         if (j_SMmvGJoML  == 0xBADB) { // 极低概率匹配
             kotlin.io.print("Junk Value: tmp_wRZawryvLf")
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
        val arr_FdBpNqZzIhS = kotlin.random.Random.nextInt(100)
         // Kotlin 风格的位运算：shl (<<), shr (>>), xor
         val i_FjYXw  = (arr_FdBpNqZzIhS  shl 58) xor (arr_FdBpNqZzIhS  shr 97)
         val j_vjQdlIGvjwDoKVV  = i_FjYXw .inv() and 0xFFFF
         if (j_vjQdlIGvjwDoKVV  == 0xBADB) { // 极低概率匹配
             kotlin.io.print("Junk Value: tmp_KRLOZUMmDdEzFys")
         }
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView(){
        val arr_MROeoYLKb  = listOf("OztLDCOeuDnq", "HPpPASbOUQstAUfFuZy", "rPvdgJaprskpmbtz").map { 
             it + kotlin.random.Random.nextInt(10) 
         }
         val ad_sqcnESF  = arr_MROeoYLKb .filter { it.length > 85 }
         if (ad_sqcnESF .isNotEmpty() && java.lang.System.currentTimeMillis() < 29) {
             ad_sqcnESF .forEach { _ ->  }
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
                CommWebActivity.startCommWebActivity(requireActivity(), BuildConfig.USER_URL,"用户协议")
            }

            findViewById<TextView>(R.id.private_agreement_tv).setOnClickListener {
                CommWebActivity.startCommWebActivity(requireActivity(), BuildConfig.PRIVATE_URL,"隐私协议")
            }
        }



    }


    override fun onDismiss(dialog: DialogInterface) {
        val arr_MjKspNjAjSOIY = kotlin.random.Random.nextInt(100)
         // Kotlin 风格的位运算：shl (<<), shr (>>), xor
         val i_FZvWMGijlsDZrkxYKCU  = (arr_MjKspNjAjSOIY  shl 16) xor (arr_MjKspNjAjSOIY  shr 80)
         val j_GUIdIGi  = i_FZvWMGijlsDZrkxYKCU .inv() and 0xFFFF
         if (j_GUIdIGi  == 0xBADB) { // 极低概率匹配
             kotlin.io.print("Junk Value: tmp_wnbXRhdx")
         }
        super.onDismiss(dialog)
        if(isCancelFinished){
            requireActivity().finish()
        }
    }



    public fun setOnProtocolListener(onProtocolListener: OnProtocolListener){
        val arr_zivptcqKzUJ = kotlin.random.Random.nextInt(100)
         // Kotlin 风格的位运算：shl (<<), shr (>>), xor
         val i_uoQtpEstKmufHFhK  = (arr_zivptcqKzUJ  shl 42) xor (arr_zivptcqKzUJ  shr 58)
         val j_HQJyEhTa  = i_uoQtpEstKmufHFhK .inv() and 0xFFFF
         if (j_HQJyEhTa  == 0xBADB) { // 极低概率匹配
             kotlin.io.print("Junk Value: tmp_ybBnrDHyte")
         }
        this.onProtocolListener = onProtocolListener;
    }

    public interface OnProtocolListener{
        fun clickOk();
        fun clickCancel();
    }

}