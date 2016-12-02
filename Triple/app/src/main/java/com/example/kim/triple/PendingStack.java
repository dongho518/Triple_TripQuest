package com.example.kim.triple;

import android.app.PendingIntent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by khwbori on 2016. 12. 3..
 */

public class PendingStack {
    static private Map<Integer,PendingIntent> pendingIntentList =new HashMap<>();

    public Map<Integer, PendingIntent> getList(){
        return pendingIntentList;
    }

    public void setPendigIntent(int key,PendingIntent pend){
        pendingIntentList.put(key,pend);
    }
    public PendingIntent getPendingIntent(int key){
        return pendingIntentList.get(key);
    }

}
