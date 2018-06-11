# Android Beacon Oreo Sample Application (iBeacon)

The usual way of scanning beacons on Android is to have an Android Service that uses the Bluetooth API to scan for beacons every n second. From Android Oreo (8.0), this is no longer reliable as the OS has background limitations that kill the Service soon after the app UI has gone from the foreground.

Apps now have to use the BluetoothManager BluetoothLEScanner call that sets up a callbackIntent that fires when beacons are seen.

The scan results will be delivered via the PendingIntent. Use this method of scanning if your process is not always running and it should be started when scan results are available.


Instructions: 
You probably need a Beacon Transmitter if you already have one skip steps 1.

Step 1: (Setting Up Beacon Transmitter App)
Install Beacon Toy App from Play Store (https://play.google.com/store/apps/details?id=com.uriio)
Select iBeacon with any UUID, Major, and Minor ID.

Step 2: (Setting up the sample app)
Run the app from Android Studio and have the same UUID in the EditText and click "Start Scan".

Note: You need to Turn on Bluetooth and give Location Permission in order for Beacon to Work. 


Additional Note: It seems there is an issue with Samsung devices (S8, S9...) running Android Oreo where this mechanism dosen't work but except that it works in all other devices.
https://developer.samsung.com/forum/thread/ble-start-scan-permission-failure-/201/347858?boardName=SDK&startId=00fz9~&startPage=20&curPage=22

Refernce Materials:
1. http://www.davidgyoungtech.com/2017/08/07/beacon-detection-with-android-8
2. https://medium.com/@eliaslecomte/scan-for-i-beacons-on-android-cf97ff532678
3. https://developer.android.com/about/versions/oreo/background
 
