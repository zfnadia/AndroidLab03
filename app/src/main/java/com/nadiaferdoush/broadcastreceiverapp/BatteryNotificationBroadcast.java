package com.nadiaferdoush.broadcastreceiverapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.ProgressBar;
import android.widget.TextView;

public class BatteryNotificationBroadcast extends AppCompatActivity {

    private TextView mBatteryLevelText;
    private ProgressBar mBatteryLevelProgress;
    private TextView mChargingStatus;
    private TextView mChargingMethod;
    private BroadcastReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery_notification_broadcast);

        mBatteryLevelText = (TextView) findViewById(R.id.textView);
        mBatteryLevelProgress = (ProgressBar) findViewById(R.id.progressBar);
        mChargingStatus = (TextView) findViewById(R.id.chargingStatus);
        mChargingMethod = (TextView) findViewById(R.id.chargingMethod);
        mReceiver = new BatteryBroadcastReceiver();
    }


    @Override
    protected void onStart() {
        registerReceiver(mReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        registerReceiver(mReceiver, new IntentFilter(Intent.ACTION_POWER_CONNECTED));
        registerReceiver(mReceiver, new IntentFilter(Intent.ACTION_POWER_DISCONNECTED));

        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(mReceiver);
        super.onStop();
    }

    private class BatteryBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            mBatteryLevelText.setText(getString(R.string.battery_level) + ": " + level + "%");
            mBatteryLevelProgress.setProgress(level);

            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                    status == BatteryManager.BATTERY_STATUS_FULL;

            int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
            boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
            boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;

            mChargingStatus.setText(getString(R.string.charging_status) + ": The device is" + (isCharging ? "" : " not") + " charging.");

            mChargingMethod.setText(getString(R.string.charging_method) + ": " + (usbCharge ? "Connected through USB" : (acCharge ? "Connected to AC source" : "Not Available")));

        }
    }
}
