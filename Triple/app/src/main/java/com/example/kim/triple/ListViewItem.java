package com.example.kim.triple;

import android.graphics.drawable.Drawable;

/**
 * Created by Kim on 2016-11-29.
 */
    public class ListViewItem {
    private Drawable iconDrawable ;
    private String titleStr ;
    private Drawable descStr ;

    public void setIcon(Drawable icon) {
        iconDrawable = icon ;
    }
    public void setTitle(String title) {
        titleStr = title ;
    }
    public void setDesc(Drawable desc) {
        descStr = desc ;
    }

    public Drawable getIcon() {
        return this.iconDrawable ;
    }
    public String getTitle() {
        return this.titleStr ;
    }
    public Drawable getDesc() {
        return this.descStr ;
    }
}

