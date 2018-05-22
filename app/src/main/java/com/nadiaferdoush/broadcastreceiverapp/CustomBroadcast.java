package com.nadiaferdoush.broadcastreceiverapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class CustomBroadcast extends AppCompatActivity {

    private BroadcastReceiver broadcastReceiver;
    private Button buttonBroadcastIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_broadcast);

        buttonBroadcastIntent=(Button)findViewById(R.id.buttonBroadcastIntent);
        broadcastReceiver = new MyBroadcastReceiver();

        buttonBroadcastIntent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setAction("my.custom.action.ACTION_TEST");
                sendBroadcast(intent);
            }
        });
    }

    private class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context,"Broadcast Receiver Triggered", Toast.LENGTH_SHORT).show();
        }
    }

    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter("my.custom.action.ACTION_TEST");
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(broadcastReceiver,intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadcastReceiver);
    }


}
