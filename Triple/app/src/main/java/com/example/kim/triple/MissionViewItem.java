package com.example.kim.triple;

import android.graphics.Bitmap;

public class MissionViewItem {
    private Bitmap MiconDrawable;
    private String MtitleStr;
    private String MdescStr;
    private String MdescStr2;

    public void setIcon(Bitmap icon) {
        MiconDrawable = icon;
    }
    public void setTitle(String title) {
        MtitleStr = title;
    }
    public void setDesc(String desc) {
        MdescStr = desc;
    }
    public void setDesc2(String desc2) {
        MdescStr2 = desc2;
    }

    
    public Bitmap getIcon() {
        return this.MiconDrawable;
    }
    public String getTitle() {
        return this.MtitleStr;
    }
    public String getDesc() {
        return this.MdescStr;
    }
    public String getDesc2() {
        return this.MdescStr2;
    }
}