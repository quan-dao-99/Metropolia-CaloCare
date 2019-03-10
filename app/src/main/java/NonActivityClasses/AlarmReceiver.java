package NonActivityClasses;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.example.calocare.GiaoDienChinh;
import com.example.calocare.R;

public class AlarmReceiver extends BroadcastReceiver {
    private SharedPreferences pref;
    private SharedPreferences.Editor prefEditor;

    @Override
    public void onReceive(Context context, Intent intent) {
        pref = context.getSharedPreferences(AppControl.PREF, Activity.MODE_PRIVATE);
        prefEditor = pref.edit();

        Bundle b = intent.getExtras();
        boolean isMidnight = b.getBoolean("isMidnight");

        if (isMidnight) {
            resetFood(context);
        } else if (!isMidnight) {
            createNoti(context);
        }
    }

    private void createNoti(Context context) {
        Intent intent = new Intent(context, GiaoDienChinh.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"default")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Insufficient")
                .setContentText("You need " + pref.getInt("foodRemain", 0) + " calories to complete today's goal")
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId("com.example.calocare");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "com.example.calocare",
                    "CaloCare",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
        notificationManager.notify(0, builder.build());
    }

    private void resetFood(Context context) {
        prefEditor.putInt("foodAdded", 0);
        prefEditor.putInt("foodRemain", pref.getInt("foodGoal", 0) - 0);
        prefEditor.commit();
    }
}