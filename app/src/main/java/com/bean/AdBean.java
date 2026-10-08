package com.bean;

import android.app.Activity;
import android.view.ViewGroup;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class AdBean implements Serializable {

   public String scene_key;
   public boolean isCanEnable;
   public ArrayList<AdChildBean> ad_list_beans;

    @Override
    public String toString() {
        return "AdBean{" +
                "scene_key='" + scene_key + '\'' +
                ", isCanEnable=" + isCanEnable +
                ", ad_list_beans=" + ad_list_beans +
                '}';
    }

    public String getScene_key() {
        return scene_key;
    }

    public void setScene_key(String scene_key) {
        this.scene_key = scene_key;
    }

    public boolean isCanEnable() {
        return isCanEnable;
    }

    public void setCanEnable(boolean canEnable) {
        isCanEnable = canEnable;
    }

    public ArrayList<AdChildBean> getAd_list_beans() {
        return ad_list_beans;
    }

    public void setAd_list_beans(ArrayList<AdChildBean> ad_list_beans) {
        this.ad_list_beans = ad_list_beans;
    }

    public class AdChildBean implements Serializable{
        @Override
        public String toString() {
            return "AdChildBean{" +
                    "gm_id='" + gm_id + '\'' +
                    ", taku_id='" + taku_id + '\'' +
                    ", placement_id='" + placement_id + '\'' +
                    ", type='" + type + '\'' +
                    ", key='" + key + '\'' +
                    ", allName='" + allName + '\'' +
                    '}';
        }

        public String gm_id;
        public String taku_id;
        public String placement_id;
        public String type;
        public String key;
        public String allName;

        public String getGm_id() {
            return gm_id;
        }

        public void setGm_id(String gm_id) {
            this.gm_id = gm_id;
        }

        public String getTaku_id() {
            return taku_id;
        }

        public void setTaku_id(String taku_id) {
            this.taku_id = taku_id;
        }

        public String getPlacement_id() {
            return placement_id;
        }

        public void setPlacement_id(String placement_id) {
            this.placement_id = placement_id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getAllName() {
            return allName;
        }

        public void setAllName(String allName) {
            this.allName = allName;
        }
    }
}
