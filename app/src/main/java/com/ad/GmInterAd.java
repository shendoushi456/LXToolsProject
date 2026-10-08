package com.ad;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.bytedance.sdk.openadsdk.TTFullScreenVideoAd;
import com.bytedance.sdk.openadsdk.mediation.ad.MediationAdSlot;
import com.bytedance.sdk.openadsdk.mediation.manager.MediationAdEcpmInfo;
import com.bytedance.sdk.openadsdk.mediation.manager.MediationFullScreenManager;
import com.lx.lxtoolsproject.ToolsApplication;

public class GmInterAd {

    public boolean isHasReady = false;
    private TTFullScreenVideoAd mTTFullAd;
    public void loadAd(Context context,OnAdStatusListener onloadStatusListener) {

        Log.d("AD_LOG","开始load 插屏");
        AdSlot adSlot =  new AdSlot.Builder()
                .setCodeId("104576790")
                .setOrientation(TTAdConstant.ORIENTATION_VERTICAL)
                .setMediationAdSlot(new MediationAdSlot.Builder()
                        .setMuted(false)
                        .setVolume(0.6f)
                        .setBidNotify(true).build()).build();

        TTAdNative adNative = TTAdSdk.getAdManager().createAdNative(ToolsApplication.Companion.getContentInstance());

        adNative.loadFullScreenVideoAd(adSlot,new TTAdNative.FullScreenVideoAdListener(){

            @Override
            public void onError(int i, String s) {
                isHasReady = false;
                if (onloadStatusListener!=null){
                    onloadStatusListener.loadFail();
                }

            }

            @Override
            public void onFullScreenVideoAdLoad(TTFullScreenVideoAd ttFullScreenVideoAd) {


                mTTFullAd = ttFullScreenVideoAd;
                isHasReady = true;
//                if (onloadStatusListener!=null){
//                    onloadStatusListener.loadSuccess();
//                }
                showAd((Activity)context);
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


    public void showAd(Activity activity) {


        if (isHasReadAd()){
            if (mTTFullAd == null) {
                Log.d("AD_LOG","开始展示插屏广告====mTTFullAd == null");
                return;
            }

            mTTFullAd.setFullScreenVideoAdInteractionListener(new TTFullScreenVideoAd.FullScreenVideoAdInteractionListener() {
                @Override
                public void onAdShow() {

                    if (mTTFullAd !=null){
                        MediationFullScreenManager mediationManager = mTTFullAd.getMediationManager();
                        if (mediationManager!=null){
                            MediationAdEcpmInfo showEcpm = mediationManager.getShowEcpm();

                        }
                    }

                    if (onShowStatusListener!=null){
                        onShowStatusListener.showAdSuccess();
                    }


                }

                @Override
                public void onAdVideoBarClick() {
                    if (mTTFullAd!=null){
                        MediationFullScreenManager mediationManager = mTTFullAd.getMediationManager();
                        if (mediationManager!=null){
                            MediationAdEcpmInfo showEcpm = mediationManager.getShowEcpm();
//                            callAdClicked(CreateAdBean.createNATAdInfo(showEcpm));
                        }
                    }
                }

                @Override
                public void onAdClose() {
                    Log.d("AD_LOG","onAdClose");
                }

                @Override
                public void onVideoComplete() {
                    Log.d("AD_LOG","onVideoComplete");
                }

                @Override
                public void onSkippedVideo() {
                    Log.d("AD_LOG","onSkippedVideo");
                }
            });
            mTTFullAd.showFullScreenVideoAd(activity);
        }else{
            Log.d("AD_LOG","插屏广告未准备好播放");
        }

        isHasReady = false;

    }


    public boolean isHasReadAd() {
        return mTTFullAd!=null && isHasReady;
    }
}
