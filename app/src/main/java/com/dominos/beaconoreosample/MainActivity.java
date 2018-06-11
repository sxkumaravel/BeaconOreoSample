package com.dominos.beaconoreosample;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    private EditText mEditText;
    private LinearLayout mLinearLayout;
    private ScrollView mScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditText = findViewById(R.id.main_edit_text);
        mEditText.setText(BeaconScannerUtil.BEACON_UUID);
        mLinearLayout = findViewById(R.id.main_ll_scroll);
        mScrollView = findViewById(R.id.main_scroll);

        setUpButton();
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(receiver, new IntentFilter(BeaconReceiver.SERVICE_MESSENGER));
    }

    @Override
    protected void onStop() {
        unregisterReceiver(receiver);
        super.onStop();
    }

    private void setUpButton() {
        Button button = findViewById(R.id.main_button_scan);

        if (BeaconReceiver.isReceiverAlreadySetUp(this)) {
            button.setText(R.string.stop_scanning);
            button.setTag(true);
        } else {
            button.setText(R.string.start_scanning);
            button.setTag(false);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkForPermission()) {
                    return;
                }

                if (!BeaconScannerUtil.isBluetoothEnabled()) {
                    Toast.makeText(v.getContext(), "Turn On Bluetooth", Toast.LENGTH_LONG).show();
                    return;
                }

                if ((boolean) v.getTag()) {
                    BeaconRegisterApi.cancelBeaconScan(v.getContext());
                } else {
                    BeaconRegisterApi.registerForBeaconScan(v.getContext(), mEditText.getText().toString().trim());
                }

                setUpButton();
            }
        });
    }

    private boolean checkForPermission() {
        int location = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        if (location != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }

        return true;
    }

    private void addTextToScroll(String text) {
        CustomView customView = new CustomView(this, text, mLinearLayout.getChildCount());
        mLinearLayout.addView(customView);
        mScrollView.fullScroll(View.FOCUS_DOWN);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            addTextToScroll(intent.getStringExtra("extra"));
        }
    };
}
