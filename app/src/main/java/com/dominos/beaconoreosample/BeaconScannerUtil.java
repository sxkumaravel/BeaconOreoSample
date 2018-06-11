package com.dominos.beaconoreosample;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanSettings;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created on 6/11/18.
 *
 * @author kumars
 */
public class BeaconScannerUtil {

    public static final String BEACON_UUID = "00000000-0000-0000-0000-000000000000";
    private static final int MANUFACTURE_ID = 0x004c;

    /**
     * Get default adapter may be null if the device doesn't have bluetooth option.
     * @return true if the bluetooth is enabled.
     */
    public static boolean isBluetoothEnabled() {
        return BluetoothAdapter.getDefaultAdapter() != null && BluetoothAdapter.getDefaultAdapter().isEnabled();
    }

    public static ScanSettings getScanSettings() {
        ScanSettings.Builder builder = new ScanSettings.Builder();
        builder.setScanMode(ScanSettings.SCAN_MODE_BALANCED);
        return builder.build();
    }

    public static List<ScanFilter> getScanFilters(String uuid) {
        List<ScanFilter> arrayList = new ArrayList<>();
        ScanFilter.Builder builder = new ScanFilter.Builder();

        // the manufacturer data byte is the filter!
        final byte[] manufacturerData = new byte[]
                {
                        0, 0,

                        // uuid
                        0, 0, 0, 0,
                        0, 0,
                        0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0,

                        // major
                        0, 0,

                        // minor
                        0, 0,

                        0
                };

        // the mask tells what bytes in the filter need to match, 1 if it has to match, 0 if not
        final byte[] manufacturerDataMask = new byte[]
                {
                        0, 0,

                        // uuid
                        1, 1, 1, 1,
                        1, 1,
                        1, 1,
                        1, 1, 1, 1, 1, 1, 1, 1,

                        // major
                        0, 0,

                        // minor
                        0, 0,

                        0
                };

        // copy UUID (with no dashes) into data array
        System.arraycopy(UuidToByteArray(UUID.fromString(uuid)), 0, manufacturerData, 2, 16);

        builder.setManufacturerData(MANUFACTURE_ID, manufacturerData, manufacturerDataMask);
        arrayList.add(builder.build());

        return arrayList;
    }

    /**
     * Converts a {@link UUID} to a byte[]. This is used to create a {@link android.bluetooth.le.ScanFilter}.
     * From http://stackoverflow.com/questions/29664316/bluetooth-le-scan-filter-not-working.
     *
     * @param uuid UUID to convert to a byte[]
     * @return byte[]
     */
    private static byte[] UuidToByteArray(@NonNull final UUID uuid) {
        String hex = uuid.toString().replace("-", "");
        int length = hex.length();
        byte[] result = new byte[length / 2];

        for (int i = 0; i < length; i += 2) {
            result[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4) + Character.digit(hex.charAt(i + 1), 16));
        }

        return result;
    }
}
