package broadcast;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.util.Log;

import com.example.kim.triple.MainActivity;
import com.example.kim.triple.MissionPlay;
import com.example.kim.triple.PendingStack;
import com.example.kim.triple.R;

public class ProximityIntentReceiver extends BroadcastReceiver {
    public ProximityIntentReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Log.e("CHECK", "BroadCast");
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        int missionId = intent.getIntExtra("mission_id",1);
        Log.i("BroadCast Id",""+missionId);
        Intent intent1 = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent1,PendingIntent.FLAG_UPDATE_CURRENT);
        PendingStack pendingStack = new PendingStack();

        try {
            PendingIntent pendingIntent1 = pendingStack.getPendingIntent(missionId);
            if(pendingIntent1 != null) {
                locationManager.removeProximityAlert(pendingIntent1);
                pendingStack.getList().remove(missionId);
            }
        }catch (SecurityException e){
            e.printStackTrace();
        }


        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification =new android.support.v7.app.NotificationCompat.Builder(context)
                .setContentTitle("Mission")
                .setContentText("you can accept mission")
                .setSmallIcon(R.drawable.notification_icon)
                .setContentIntent(pendingIntent)
                .build();

        //소리추가
        notification.defaults = Notification.DEFAULT_SOUND;

        //알림 소리를 한번만 내도록
        notification.flags = Notification.FLAG_ONLY_ALERT_ONCE;

        //확인하면 자동으로 알림이 제거 되도록
        notification.flags = Notification.FLAG_AUTO_CANCEL;

        mNotificationManager.notify( 777 , notification);
    }
}
