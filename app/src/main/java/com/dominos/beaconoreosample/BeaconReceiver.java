package com.dominos.beaconoreosample;

import android.app.PendingIntent;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanResult;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created on 6/11/18.
 *
 * @author kumars
 */
public class BeaconReceiver extends BroadcastReceiver {

    private static final String TAG = BeaconReceiver.class.getSimpleName();
    public static final String SERVICE_MESSENGER = "com.dominos.beaconoreosample.Service-messenger";

    private static final String EXTRA = "o-scan";
    private static final int REQUEST_CODE = 4759;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "BeaconReceiver - onReceive");
        if (intent == null) {
            return;
        }

        int bleCallbackType = intent.getIntExtra(BluetoothLeScanner.EXTRA_CALLBACK_TYPE, -1);
        if (bleCallbackType == -1) {
            return;
        }

        Log.d(TAG, "Passive background scan callback type: " + bleCallbackType);

        ArrayList<ScanResult> scanResults = intent.getParcelableArrayListExtra(BluetoothLeScanner.EXTRA_LIST_SCAN_RESULT);
        if (scanResults == null) {
            return;
        }

        broadcastProgressUpdateMessage(context, scanResults.toString());
    }

    private void broadcastProgressUpdateMessage(Context context, String extras) {
        Intent brIntent = new Intent(SERVICE_MESSENGER);
        brIntent.putExtra("extra", extras);
        context.sendBroadcast(brIntent);
    }

    /**
     * @return the pending intent for this {@link BeaconReceiver} for the given context.
     */
    public static PendingIntent getPendingIndent(Context context) {
        Intent intent = new Intent(context, BeaconReceiver.class);
        intent.setAction(EXTRA);
        return PendingIntent.getBroadcast(context, REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    /**
     * @return true if the {@link BeaconReceiver} is already set up and is exists within the system memory.
     * Note: once the receiver is set it will stay until the application is rebooted or the application is uninstalled.
     */
    public static boolean isReceiverAlreadySetUp(@NonNull Context context) {
        Intent intent = new Intent(context, BeaconReceiver.class);
        intent.setAction(EXTRA);
        return PendingIntent.getBroadcast(context, REQUEST_CODE, intent, PendingIntent.FLAG_NO_CREATE) != null;
    }
}
