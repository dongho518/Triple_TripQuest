package com.example.kim.triple;

import android.graphics.Bitmap;

/**
 * Created by Kim on 2016-11-29.
 */
    public class ListViewItem {
    private int id;
    private Bitmap iconDrawable ;
    private String titleStr ;
    private String descStr ;
    private String descStr2 ;

    public void setId(int arg){id = arg; }
    public void setIcon(Bitmap icon) {
        iconDrawable = icon ;
    }
    public void setTitle(String title) {
        titleStr = title ;
    }
    public void setDesc(String desc) {
        descStr = desc ;
    }
    public void setDesc2(String desc2) {
        descStr2 = desc2 ;
    }

    public int getId(){return this.id;}
    public Bitmap getIcon() {
        return this.iconDrawable ;
    }
    public String getTitle() {
        return this.titleStr ;
    }
    public String getDesc() {
        return this.descStr ;
    }
    public String getDesc2() {
        return this.descStr2 ;
    }

}

