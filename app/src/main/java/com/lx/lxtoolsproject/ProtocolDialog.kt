package com.lx.lxtoolsproject

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
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView(){

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
        super.onDismiss(dialog)
        if(isCancelFinished){
            requireActivity().finish()
        }
    }



    public fun setOnProtocolListener(onProtocolListener: OnProtocolListener){
        this.onProtocolListener = onProtocolListener;
    }

    public interface OnProtocolListener{
        fun clickOk();
        fun clickCancel();
    }

}