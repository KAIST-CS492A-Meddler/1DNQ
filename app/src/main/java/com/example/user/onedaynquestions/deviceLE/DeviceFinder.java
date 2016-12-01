package com.example.user.onedaynquestions.deviceLE;

import android.app.Application;

import com.example.user.onedaynquestions.device.BluetoothComm;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.powersave.BackgroundPowerSaver;
import org.altbeacon.beacon.startup.BootstrapNotifier;
import org.altbeacon.beacon.startup.RegionBootstrap;

import java.util.Collection;

public class DeviceFinder extends Application implements BootstrapNotifier,
        RangeNotifier {
    private static final String TAG = "Device Finder";
    private BeaconManager mBeaconManager;
    private Region mAllBeaconsRegion;
    private Region mYellowBeaconsRegion;
    private Region mPinkBeaconsRegion;
    //private BluetoothComm mMonitoringActivity;
    //private BeaconRangingActivity mRangingActivity;
    private BackgroundPowerSaver mBackgroundPowerSaver;
    @SuppressWarnings("unused")
    private RegionBootstrap mRegionBootstrap;

    private boolean[] isFind;
    private int count;

    public static String packet;
    public static boolean updated;
    public static boolean updating;

    public static final int period = 100;
    public static final int periodCount = 5;

    public static BeaconInfo[] beacons = new BeaconInfo[3];

    @Override
    public void onCreate() {
        super.onCreate();

        packet = new String();
        updated = false;
        updating = false;

        isFind = new boolean[3];
        for (int i = 0; i < 3; i++) {
            beacons[i] = new BeaconInfo();
            isFind[i] = false;
        }

        System.currentTimeMillis();
        beacons[0].mac = new String("20:16:01:18:94:43"); // Green
		/*
		 * beacons[0].height = 178; beacons[0].x = 267; beacons[0].y = 264;
		 */
        beacons[0].height = 46;
        beacons[0].x = 160;
        beacons[0].y = 0;
        beacons[0].macHash = 1838408631;
        beacons[0].identifier = "24ddf411-8cf1-440c-87cd-e368daf9c93e";
        beacons[0].ID2 = "17987";
        beacons[0].ID3 = "18762";

        beacons[1].mac = new String("E0:FE:4B:CD:D2:15"); // yellow
		/*
		 * beacons[1].height = 122; beacons[1].x = 385; beacons[1].y = 426;
		 */
        beacons[1].height = 48;
        beacons[1].x = 0;
        beacons[1].y = -76;
        beacons[1].macHash = -1546132393;
        beacons[1].identifier = "24ddf411-8cf1-440c-87cd-c368daf9c93e";
        beacons[1].ID2 = "18497";
        beacons[1].ID3 = "17732";

        beacons[2].mac = new String("DE:2C:73:EF:FD:7C"); // pink
		/*
		 * beacons[2].height = 196; beacons[2].x = 301; beacons[2].y = 426;
		 */
        beacons[2].height = 48;
        beacons[2].x = 0;
        beacons[2].y = 0;
        beacons[2].macHash = -102821283;
        beacons[2].identifier = "24ddf411-8cf1-440c-87cd-c368daf9c93e";
        beacons[2].ID2 = "18500";
        beacons[2].ID3 = "18757";

        mAllBeaconsRegion = new Region("OnMyDesk",
                Identifier.parse(beacons[0].identifier), null, null);
		/*
		 * mYellowBeaconsRegion = new Region("OnMyDesk",
		 * Identifier.parse(beacons[1].identifier),
		 * Identifier.parse(beacons[1].ID2), Identifier.parse(beacons[1].ID3));
		 * mPinkBeaconsRegion = new Region("OnMyDesk",
		 * Identifier.parse(beacons[2].identifier),
		 * Identifier.parse(beacons[2].ID2), Identifier.parse(beacons[2].ID3));
		 */

        mBeaconManager = BeaconManager.getInstanceForApplication(this);
        mBeaconManager.setForegroundScanPeriod(period * periodCount);
        mBackgroundPowerSaver = new BackgroundPowerSaver(this);
        mRegionBootstrap = new RegionBootstrap(this, mAllBeaconsRegion);

        isFind = new boolean[3];

        mBeaconManager
                .getBeaconParsers()
                .add(new BeaconParser()
                        .setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));

        // By default the AndroidBeaconLibrary will only find AltBeacons. If you
        // wish to make it
        // find a different type of beacon, you must specify the byte layout for
        // that beacon's
        // advertisement with a line like below. The example shows how to find a
        // beacon with the
        // same byte layout as AltBeacon but with a beaconTypeCode of 0xaabb
        //
        // beaconManager.getBeaconParsers().add(new BeaconParser().
        // setBeaconLayout("m:2-3=aabb,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));
        //
        // In order to find out the proper BeaconLayout definition for other
        // kinds of beacons, do
        // a Google search for "setBeaconLayout" (including the quotes in your
        // search.)
    }

    @Override
    public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region arg1) {
		/*
		 * if (mRangingActivity != null) {
		 * mRangingActivity.didRangeBeaconsInRegion(arg0, arg1); }
		 */
        boolean end = false;
        int count = 0;

        for(int i = 0 ; i < 3; i++) isFind[i] = false;
        if (beacons.size() > 0) {
            for (Beacon beacon : beacons) {
                for (int i = 0; i < 3; i++) {
                    if (!isFind[i] && DeviceFinder.beacons[i].isSame(beacon)) {
                        count++;
                        isFind[i] = true;
                        DeviceFinder.beacons[i].distance = (int) (beacon.getDistance() * 1000);
                        DeviceFinder.beacons[i].rssi = beacon.getRssi();

                    }
                }
                if (count == 3) {
                    break;
                }
            }
        }
    }

    @Override
    public void didDetermineStateForRegion(int arg0, Region arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void didEnterRegion(Region arg0) {
        /*
        if (mMonitoringActivity != null) {
            mMonitoringActivity.didEnterRegion(arg0);
        }
        try {
            Log.d(TAG, "entered region.  starting ranging");
            mBeaconManager.startRangingBeaconsInRegion(mAllBeaconsRegion);
            mBeaconManager.setRangeNotifier(this);
        } catch (RemoteException e) {
            Log.e(TAG, "Cannot start ranging");
        }
        */
    }

    @Override
    public void didExitRegion(Region arg0) {
        /*
        if (mMonitoringActivity != null) {
            mMonitoringActivity.didExitRegion(arg0);
        }*/
    }

    public void setMonitoringActivity(BluetoothComm activity) {
       // mMonitoringActivity = activity;
    }
/*
	public void setRangingActivity(BeaconRangingActivity activity) {
		mRangingActivity = activity;
	}*/
}
