package com.lx.lxtoolsproject;


import android.app.Activity;
import android.content.Context;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.bytedance.sdk.openadsdk.TTFullScreenVideoAd;
import com.bytedance.sdk.openadsdk.mediation.ad.MediationAdSlot;
import com.bytedance.sdk.openadsdk.mediation.manager.MediationAdEcpmInfo;
import com.bytedance.sdk.openadsdk.mediation.manager.MediationFullScreenManager;


public class GMFullUtils{

    public static GMFullUtils instance;
    private TTFullScreenVideoAd mTTFullAd;

    public  static GMFullUtils  getGMFullUtilsInstance(){
        if (instance == null){
            synchronized (GMFullUtils.class){
                if (instance == null){
                    instance = new GMFullUtils();
                }
            }
        }
        return instance;
    }


    public void loadAd(String ggId, Activity context) {
        AdSlot adSlot =  new AdSlot.Builder()
                .setCodeId(ggId)
                .setOrientation(TTAdConstant.ORIENTATION_VERTICAL)
                .setMediationAdSlot(new MediationAdSlot.Builder()
                        .setMuted(false)
                        .setVolume(0.6f)
                        .setBidNotify(true).build()).build();

        TTAdNative adNative = TTAdSdk.getAdManager().createAdNative(context);

        adNative.loadFullScreenVideoAd(adSlot,new TTAdNative.FullScreenVideoAdListener(){

            @Override
            public void onError(int i, String s) {
            }

            @Override
            public void onFullScreenVideoAdLoad(TTFullScreenVideoAd ttFullScreenVideoAd) {
                mTTFullAd = ttFullScreenVideoAd;
                showAd(context);

            }
            @Override
            public void onFullScreenVideoCached() {
            }
            @Override
            public void onFullScreenVideoCached(TTFullScreenVideoAd ttFullScreenVideoAd) {
                mTTFullAd = ttFullScreenVideoAd;
            }
        });
    }


    public void showAd(Activity context ) {


            if (mTTFullAd == null) {
                return;
            }

            mTTFullAd.setFullScreenVideoAdInteractionListener(new TTFullScreenVideoAd.FullScreenVideoAdInteractionListener() {
                @Override
                public void onAdShow() {
                    if (mTTFullAd !=null){
                        MediationFullScreenManager mediationManager = mTTFullAd.getMediationManager();
                        if (mediationManager!=null){
                            MediationAdEcpmInfo showEcpm = mediationManager.getShowEcpm();
//                            createNATAdInfo(showEcpm,getAdInfo());
                        }
                    }
                }

                @Override
                public void onAdVideoBarClick() {
                    if (mTTFullAd!=null){
                        MediationFullScreenManager mediationManager = mTTFullAd.getMediationManager();
                        if (mediationManager!=null){
                            MediationAdEcpmInfo showEcpm = mediationManager.getShowEcpm();
                        }
                    }
                }

                @Override
                public void onAdClose() {
                    context.finish();
                }

                @Override
                public void onVideoComplete() {
                }

                @Override
                public void onSkippedVideo() {
                }
            });
            mTTFullAd.showFullScreenVideoAd(context);


    }


}
