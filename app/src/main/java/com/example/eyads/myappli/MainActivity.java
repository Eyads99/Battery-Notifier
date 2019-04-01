package com.example.eyads.myappli;

import android.app.Notification;
import android.app.NotificationChannel;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.graphics.Color;
import android.content.res.Resources;
import android.util.TypedValue;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.os.BatteryManager;
import android.media.AudioManager;
import android.media.ToneGenerator;

import java.util.Objects;

import static android.app.NotificationManager.IMPORTANCE_HIGH;
import static android.os.Build.*;
import static java.lang.System.currentTimeMillis;


public class MainActivity extends AppCompatActivity {

    NotificationCompat.Builder notification;
    private static final int uID= 6785;
    private static final String TAG ="active_message";
    int num = 2;

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

       // createNotificationChannel();

        if (num!=0)

            notification = new NotificationCompat.Builder(this,"channel");

        else

            notification = new NotificationCompat.Builder(this);
    }
//start of on click
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Intent i = new Intent(this,MyService.class);
        //startService(i);

        createNotificationChannel();

        if (num==0)

            notification = new NotificationCompat.Builder(this);

        else
            notification = new NotificationCompat.Builder(this,"channel");


        final EditText editText = findViewById(R.id.editText);//refrence to the input field called editText

        //final int bl = 23;
        Button Ebutton = findViewById(R.id.Ebutton);// refrence to the button, named "Ebutton"
        Ebutton.setOnClickListener( //this is wait for Ebutton to be clicked
                new Button.OnClickListener(){ //this is need to contextulise the method

                    public void onClick(View v){//this is the method that tell the button what to do
                        TextView cText = findViewById(R.id.textView2);//refrence to the text on the screen
                        cText.setText("The notification has been activated");//changes the refrenced text to "Button has been clicked"
                        notification.setSmallIcon(R.mipmap.ic_launcher);//this tells the notification what it's Icon is
                        notification.setContentTitle("Battery Notification"); //this tells the notification what to say
                        notification.setWhen(currentTimeMillis());//gives the current time with notification
                        notification.setContentText("The notification has been activated");
                        //////////////////
                        final int bl;
                        bl = Integer.parseInt(editText.getText().toString());
                        /////////////////
                        i.putExtra("bl",bl);//puts the requied number in the service bl = batteryLevel
                        startService(i);
                        //below sends the notification
                        NotificationManager NM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                        if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
                            Objects.requireNonNull(NM).notify(uID,notification.build());
                        }

                    }
                }

        );
//end of on click


        Log.i(TAG,"onCreate");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Made by Eyad Yasser Shaban © 2018", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}