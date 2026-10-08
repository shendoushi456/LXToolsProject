package com.bean;

import android.app.Activity;
import android.view.ViewGroup;

import java.io.Serializable;
import java.lang.ref.WeakReference;

public class ControlAdBean implements Serializable {

    public String adSCreen;

    public int adIndex;
    public WeakReference<Activity> wrContext;
    public WeakReference<ViewGroup> wrViewGroup;

    public AdBean adBean;

    public WeakReference<Activity> getWrContext() {
        return wrContext;
    }

    public void setWrContext(WeakReference<Activity> wrContext) {
        this.wrContext = wrContext;
    }

    public WeakReference<ViewGroup> getWrViewGroup() {
        return wrViewGroup;
    }

    public void setWrViewGroup(WeakReference<ViewGroup> wrViewGroup) {
        this.wrViewGroup = wrViewGroup;
    }

    public String getAdSCreen() {
        return adSCreen;
    }

    public void setAdSCreen(String adSCreen) {
        this.adSCreen = adSCreen;
    }

    public int getAdIndex() {
        return adIndex;
    }

    public void setAdIndex(int adIndex) {
        this.adIndex = adIndex;
    }

    public AdBean getAdBean() {
        return adBean;
    }

    public void setAdBean(AdBean adBean) {
        this.adBean = adBean;
    }

    @Override
    public String toString() {
        return "ControlAdBean{" +
                "wrContext=" + wrContext +
                ", wrViewGroup=" + wrViewGroup +
                ", adBean=" + adBean +
                '}';
    }
}
