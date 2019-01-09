package com.example.eyads.myappli;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.IBinder;
import android.app.NotificationChannel;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.app.NotificationManager;
import android.support.v4.app.NotificationCompat;
import android.os.BatteryManager;
import java.util.Objects;

import static android.app.NotificationManager.IMPORTANCE_HIGH;
import static android.os.Build.*;

public class MyService extends Service {
    NotificationCompat.Builder notification;
    private static final int uID= 6785;
    private static final String TAG ="active_message";
    int num = 2;
    boolean flag=false;


    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (VERSION.SDK_INT >= VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel);
            String description = getString(R.string.channel_description);
            //int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("channel",name, IMPORTANCE_HIGH);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this


            NotificationManager NM = getSystemService(NotificationManager.class);
            Objects.requireNonNull(NM).createNotificationChannel(channel);

            num = 1;
        }
        else
            num = 0;



        if (num==0)

            notification = new NotificationCompat.Builder(this);

        else
            notification = new NotificationCompat.Builder(this,"channel");
    }

    public MyService() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG,"Method onStartCommand has been called");
        final int bl = intent.getIntExtra("bl", 1);
        Runnable r = new Runnable() {
            @RequiresApi(api = VERSION_CODES.LOLLIPOP)
            @Override
            public void run() {
                while(true)
                {
                    //get battery level
                    BatteryManager bm = (BatteryManager) getBaseContext().getSystemService(BATTERY_SERVICE);
                    int batLevel = 0;

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        batLevel = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
                    }

                    if (batLevel==bl)
                    {
                        createNotificationChannel();
                        notification.setSmallIcon(R.mipmap.ic_launcher);//this tells the notification what it's Icon is
                        notification.setContentTitle("Battery notification"); //this tells the notification what to say
                        notification.setWhen(System.currentTimeMillis());//gives the current time with notification
                        notification.setContentText("You have "+ bl + "% battery remaining");
                            if(flag==false) {
                                ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
                                toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 500);
                            }
                            flag = true;
                        //below sends the notification
                        NotificationManager NM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                        if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
                            Objects.requireNonNull(NM).notify(uID,notification.build());
                        }
                    }
                }
            }
        };
    Thread bThread = new Thread(r);
    bThread.start();
    return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.i(TAG,"Method onDestory has been called");
    }

//below is not needed
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
