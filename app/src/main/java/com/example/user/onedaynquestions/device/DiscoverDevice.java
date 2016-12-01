package com.example.user.onedaynquestions.device;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.user.onedaynquestions.R;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by user on 2016-04-18.
 */
public class DiscoverDevice extends Activity {
    public static String EXTRA_DEVICE_ADDRESS = "device_address";

    private BluetoothAdapter mBtAdapter;
    private ArrayAdapter<String> mPairedDevicesArrayAdapter;
    private ArrayAdapter<String> mNewDevicesArrayAdapter;
    private ArrayList<String> list_pair;
    private ArrayList<String> list_new;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_discover_device);

        setResult(Activity.RESULT_CANCELED);

        Button scanButton = (Button) findViewById(R.id.button_scan);
        scanButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                doDiscovery();
            }
        });

        list_pair = new ArrayList<String>();
        list_new = new ArrayList<String>();
        mPairedDevicesArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, list_pair);
        mNewDevicesArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, list_new);

        // Find and set up the ListView for paired devices
        final ListView pairedListView = (ListView) findViewById(R.id.paired_devices);
        pairedListView.setAdapter(mPairedDevicesArrayAdapter);

        // Find and set up the ListView for newly discovered devices
        final ListView newDevicesListView = (ListView) findViewById(R.id.new_devices);
        newDevicesListView.setAdapter(mNewDevicesArrayAdapter);

        Button connectButton = (Button) findViewById(R.id.connect_device);
        connectButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mBtAdapter.cancelDiscovery();

                SparseBooleanArray sba_pair = pairedListView.getCheckedItemPositions();
                SparseBooleanArray sba_new = newDevicesListView.getCheckedItemPositions();

                ArrayList<String> arrlist = new ArrayList<String>();

                if(sba_pair.size() !=0) {
                    for (int i = pairedListView.getCount() - 1; i >= 0; i--) {
                        if (sba_pair.get(i)) {
                            String info = list_pair.get(i);
                            String address = info.substring(info.length() - 17);
                            arrlist.add(address);
                        }
                    }
                }
                if(sba_new.size() !=0) {
                    for (int i = newDevicesListView.getCount() - 1; i >= 0; i--) {
                        if (sba_new.get(i)) {
                            String info = list_new.get(i);
                            String address = info.substring(info.length() - 17);
                            arrlist.add(address);
                        }
                    }
                }
                if(arrlist.size() != 0) {
                    CharSequence[] cs = arrlist.toArray(new CharSequence[arrlist.size()]);

                    Intent intent = new Intent();
                    intent.putExtra(EXTRA_DEVICE_ADDRESS, cs);

                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            }
        });

        // Register for broadcasts when a device is discovered
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(mReceiver, filter);

        // Register for broadcasts when discovery has finished
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(mReceiver, filter);


        // Get the local Bluetooth adapter
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();

        // Get a set of currently paired devices
        Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();

        // If there are paired devices, add each one to the ArrayAdapter
        //check device signal strength: RSSI

        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                mPairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        } else {
            String noDevices = getResources().getText(R.string.none_paired).toString();
            mPairedDevicesArrayAdapter.add(noDevices);
        }
        //ListView
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Make sure we're not doing discovery anymore
        if (mBtAdapter != null) {
            mBtAdapter.cancelDiscovery();
        }

        // Unregister broadcast listeners
        this.unregisterReceiver(mReceiver);
    }

    private void doDiscovery() {

        // Indicate scanning in the title
        setProgressBarIndeterminateVisibility(true);
        setTitle(R.string.scanning);

        for(int i = 0 ; i <list_new.size(); i++)
            list_new.remove(i);

        mNewDevicesArrayAdapter.notifyDataSetChanged();

        // If we're already discovering, stop it
        if (mBtAdapter.isDiscovering()) {
            mBtAdapter.cancelDiscovery();
        }

        // Request discover from BluetoothAdapter
        mBtAdapter.startDiscovery();
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // If it's already paired, skip it, because it's been listed already


                //check device signal strength: RSSI
                short rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE);
                //if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                if (rssi < 70){
                    list_new.add(device.getName() + ", RSSI: " + rssi + "dBm" + "\n" + device.getAddress());
                    mNewDevicesArrayAdapter.notifyDataSetChanged();
                }
                //}
                // When discovery is finished, change the Activity title
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                setProgressBarIndeterminateVisibility(false);
                setTitle(R.string.select_device);
                if (mNewDevicesArrayAdapter.getCount() == 0) {
                    String noDevices = getResources().getText(R.string.none_found).toString();
                    mNewDevicesArrayAdapter.add(noDevices);
                }

            }
        }
    };
}
