package com.lx.lxtoolsproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ep.custom_honor_library.R;
import com.ep.custom_honor_library.adlp.AdController;
import com.ep.custom_honor_library.bean.ControlAdBean;
import com.lx.c_interface_library.CommonAPI;
import com.ep.custom_honor_library.utils.CustomLogUtils;
import com.lx.c_interface_library.OnMiddleInterface;

import java.lang.ref.WeakReference;

public class MiddleAdActivity extends AppCompatActivity implements OnMiddleInterface {



    private LinearLayout adLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.middle_ad_activity);
        adLayout = findViewById(R.id.middle_ad_layout);
        initAdView(getIntent());
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        initAdView(intent);
    }


    private void initAdView(Intent intent){
        String adScreen = intent.getStringExtra(CommonAPI.INTENT_MIDDLE_FLAG);
        int adIndex = intent.getIntExtra(CommonAPI.INTENT_MIDDLE_INDEX,0);
        CustomLogUtils.i("获取到的KEY == "+adScreen);
        ControlAdBean controlAdBean = new ControlAdBean();
        controlAdBean.setWrContext(new WeakReference<>(this));
        controlAdBean.setWrViewGroup(new WeakReference<>(adLayout));
        controlAdBean.setAdIndex(adIndex);
        controlAdBean.setAdSCreen(adScreen);
        AdController.getAdControllerInstance().intentAd(controlAdBean);

    }

}
