package com.example.user.onedaynquestions.deviceLE;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;

import com.example.user.onedaynquestions.R;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.Collection;

public class BeaconRangingActivity extends Activity implements BeaconConsumer {
    protected static final String TAG = "RangingActivity";
    private BeaconManager bManager;
    public static final int MESSAGE_FIND = 1;


    int[] maxRSSIs;
    int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_ranging);
        bManager = BeaconManager.getInstanceForApplication(this);
        bManager.bind(this);
        maxRSSIs = new int[3];

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        bManager.unbind(this);
    }
    @Override
    public void onBeaconServiceConnect() {
        bManager.setRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                if (beacons.size() > 0) {
                    Log.i(TAG, "The first beacon I see is about "+beacons.iterator().next().getDistance()+" meters away.");
                }
            }
        });

        try {
            bManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
        } catch (RemoteException e) {    }
    }


    public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {

        if (beacons.size() > 0) {
            for (Beacon beacon: beacons) {
            	beacon.getBluetoothAddress();
            	
            	Log.d("d","Beacon "+beacon.toString()+" is about "+beacon.getDistance()+" meters away, with Rssi: "+beacon.getRssi());            	
            }
        }
    }

    // The Handler that gets information back from the BluetoothChatService
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_FIND:

                    break;
            }
        }
    };
}
