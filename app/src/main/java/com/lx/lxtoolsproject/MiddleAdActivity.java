package com.lx.lxtoolsproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.lx.c_interface_library.OnMiddleInterface;
import com.lx.lxtoolsproject.utils.AdControlCUtils;


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
        AdControlCUtils.initAdShow(intent,this,adLayout);
    }

}
