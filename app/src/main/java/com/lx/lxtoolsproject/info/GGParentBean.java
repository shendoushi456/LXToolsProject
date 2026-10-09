package com.lx.lxtoolsproject.info;

import java.io.Serializable;
import java.util.ArrayList;

public class GGParentBean implements Serializable {

    public String ggKey;
    public ArrayList<GGChildBean> ggChild;

    @Override
    public String toString() {
        return "GGParentBean{" +
                "ggKey='" + ggKey + '\'' +
                ", ggChild=" + ggChild +
                '}';
    }

    public String getGgKey() {
        return ggKey;
    }

    public void setGgKey(String ggKey) {
        this.ggKey = ggKey;
    }

    public ArrayList<GGChildBean> getGgChild() {
        return ggChild;
    }

    public void setGgChild(ArrayList<GGChildBean> ggChild) {
        this.ggChild = ggChild;
    }

    public static class GGChildBean implements Serializable{
        public String ggChildID;
        public String getggType;
        public String ggChildKey;

        @Override
        public String toString() {
            return "GGChildBean{" +
                    "ggChildID='" + ggChildID + '\'' +
                    ", getggType='" + getggType + '\'' +
                    ", ggChildKey='" + ggChildKey + '\'' +
                    '}';
        }

        public String getGetggType() {
            return getggType;
        }

        public void setGetggType(String getggType) {
            this.getggType = getggType;
        }

        public String getGgChildID() {
            return ggChildID;
        }

        public void setGgChildID(String ggChildID) {
            this.ggChildID = ggChildID;
        }


        public String getGgChildKey() {
            return ggChildKey;
        }

        public void setGgChildKey(String ggChildKey) {
            this.ggChildKey = ggChildKey;
        }
    }
}
