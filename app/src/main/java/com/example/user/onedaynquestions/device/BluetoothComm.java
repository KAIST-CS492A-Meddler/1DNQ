package com.example.user.onedaynquestions.device;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.onedaynquestions.R;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.Region;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

/**
 * Created by user on 2016-04-18.
 */
public class BluetoothComm extends AppCompatActivity implements BeaconConsumer {

    public static final String TAG = "BluetoothComm";
    //Message from CommService
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_DEVICE_NAME = 3;
    public static final int MESSAGE_TOAST = 4;

    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;

    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";

    // Name of the connected device
    private String mConnectedDeviceName = null;
    // Array adapter for the conversation thread
    private ArrayAdapter<String> mConversationArrayAdapter;
    private BluetoothAdapter mBluetoothAdapter = null;
    private CommService commService = null;

    private TextView state;
    private ListView mConversationView;


    private BeaconManager bManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /*-- Basic setting for navigating view start--*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_bluetooth_comm);

        //state = (TextView)findViewById(R.id.state);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        Button start = (Button)findViewById(R.id.start);
        start.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent discoveringIntent = new Intent(BluetoothComm.this, DiscoverDevice.class);
                startActivityForResult(discoveringIntent, REQUEST_CONNECT_DEVICE);
            }
        });


        bManager = BeaconManager.getInstanceForApplication(this);
        bManager.bind(this);

        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

    }

    @Override
    public void onStart() {
        super.onStart();

        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            // Otherwise, setup the chat session
        } else {
            if (commService == null) try {
                setupService();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public synchronized void onResume() {
        super.onResume();
        //if(D) Log.e(TAG, "+ ON RESUME +");

        // Performing this check in onResume() covers the case in which BT was
        // not enabled during onStart(), so we were paused to enable it...
        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
        if (commService != null) {
            // Only if the state is STATE_NONE, do we know that we haven't started already
            if (commService.getState() == CommService.STATE_NONE) {
                // Start the Bluetooth chat services
                commService.start();
            }
        }
    }

    private void setupService() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        mConversationArrayAdapter = new ArrayAdapter<String>(this, R.layout.textform_message);
        mConversationView = (ListView) findViewById(R.id.in);
        mConversationView.setAdapter(mConversationArrayAdapter);
        commService = new CommService(this, mHandler);
    }

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg){
            switch (msg.what) {
                case MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case CommService.STATE_CONNECTED:
                            //mConversationArrayAdapter.clear();
                            mConversationArrayAdapter.add(mConnectedDeviceName + " is connected" );
                            break;
                        case CommService.STATE_CONNECTING:
                        case CommService.STATE_LISTEN:
                        case CommService.STATE_NONE:
                            break;
                    }
                    break;
                case MESSAGE_READ:
                    byte[] buf = (byte[]) msg.obj;
                    //mConversationArrayAdapter.add(mConnectedDeviceName+":   " + new String(buf).trim());
                    mConversationArrayAdapter.add(new String(buf).trim());
                    break;
                case MESSAGE_DEVICE_NAME:
                    mConnectedDeviceName = msg.getData().getString(DEVICE_NAME); // save the connected device's name
                    Toast.makeText(getApplicationContext(), "Connected to " + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                    break;
                case MESSAGE_TOAST:
                    Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    // Get the device MAC address
                    CharSequence[] address = data.getExtras().getCharSequenceArray(DiscoverDevice.EXTRA_DEVICE_ADDRESS);

                    int selectedPosition=0;
                    // Get the BLuetoothDevice object
                    for (CharSequence cs : address){
                        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(cs.toString());
                        commService.connect(device, selectedPosition);
                        selectedPosition++;
                    }
                }
                break;

            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // Bluetooth is now enabled, so set up a chat session
                    try {
                        setupService();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                } else {
                    // User did not enable Bluetooth or an error occured
                    Toast.makeText(this, R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }

    @Override
    public void onBeaconServiceConnect() {
        bManager.setMonitorNotifier(new MonitorNotifier() {
            @Override
            public void didEnterRegion(Region region) {
                Log.i(TAG, "I just saw an beacon for the first time!");
            }

            @Override
            public void didExitRegion(Region region) {
                Log.i(TAG, "I no longer see an beacon");
            }

            @Override
            public void didDetermineStateForRegion(int state, Region region) {
                Log.i(TAG, "I have just switched from seeing/not seeing beacons: " + state);
            }
        });

        try {
            bManager.startMonitoringBeaconsInRegion(new Region("myMonitoringUniqueId", null, null, null));
        } catch (RemoteException e) {    }

    }



    private void verifyBluetooth() {

        try {
            if (!BeaconManager.getInstanceForApplication(this).checkAvailability()) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Bluetooth not enabled");
                builder.setMessage("Please enable bluetooth in settings and restart this application.");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        finish();
                        System.exit(0);
                    }
                });
                builder.show();
            }
        }
        catch (RuntimeException e) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Bluetooth LE not available");
            builder.setMessage("Sorry, this device does not support Bluetooth LE.");
            builder.setPositiveButton(android.R.string.ok, null);
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                @Override
                public void onDismiss(DialogInterface dialog) {
                    finish();
                    System.exit(0);
                }

            });
            builder.show();

        }

    }

    public void didEnterRegion(Region region) {
        Log.d(TAG, "I just saw a beacon named " + region.getUniqueId() + " for the first time!");
    }

    public void didExitRegion(Region region) {
        Log.d(TAG,"I no longer see a beacon named "+ region.getUniqueId());
    }

    public void didDetermineStateForRegion(int state, Region region) {
        Log.d(TAG,"I have just switched from seeing/not seeing beacons: "+state);
    }

    public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {


    }

}
