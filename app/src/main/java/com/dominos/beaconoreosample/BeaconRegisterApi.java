package com.dominos.beaconoreosample;

import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;

/**
 * Created on 6/11/18.
 *
 * @author kumars
 */
public class BeaconRegisterApi {

    /**
     * Register for specific beacons through through new bluetooth filter approach.
     * This will invoke {@link BeaconReceiver} once the specific beacons are found.
     * REQ: Bluetooth need to be turned on.
     * Location service need to be turned on.
     *
     * @param context application context to use.
     * @param uuid beacon uuid.
     */
    public static void registerForBeaconScan(Context context, String uuid) {
        BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();
        bluetoothAdapter.getBluetoothLeScanner().startScan(BeaconScannerUtil.getScanFilters(uuid), BeaconScannerUtil.getScanSettings(), BeaconReceiver.getPendingIndent(context));
    }

    /**
     * To cancel the beacon system scanning which is currently happening and to which is registered for to happen.
     *
     * @param context application context to use.
     */
    public static void cancelBeaconScan(Context context) {
        BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();
        PendingIntent pendingIndent = BeaconReceiver.getPendingIndent(context);
        bluetoothAdapter.getBluetoothLeScanner().stopScan(pendingIndent);
        pendingIndent.cancel();
    }
}
