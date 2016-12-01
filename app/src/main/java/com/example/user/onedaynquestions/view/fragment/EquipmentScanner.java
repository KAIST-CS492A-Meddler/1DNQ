/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.user.onedaynquestions.view.fragment;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.onedaynquestions.R;
import com.example.user.onedaynquestions.deviceLE.BluetoothLeService;
import com.example.user.onedaynquestions.deviceLE.HERE_GattAttributes;
import com.example.user.onedaynquestions.model.MyHereAgent;
import com.example.user.onedaynquestions.view.activity.DoingExerciseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Activity for scanning and displaying available Bluetooth LE devices.
 */
public class EquipmentScanner extends Fragment {
    private final String TAG = "EquipmentScanner";

    public static final String EXTRAS_DEVICE_NAME = "DEVICE_NAME";
    public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";
    private HERE_DeviceListAdapter equipListAdapter;
    private BluetoothAdapter mBluetoothAdapter;
    private ArrayList<BluetoothDevice> mLEdeviceList;
    private boolean mScanning;
    private Handler mHandler;
    //Bluetooth components
    public String mDeviceAddress;

    private BluetoothLeService mBluetoothLeService;
    private BluetoothGattCharacteristic characteristicTX;
    private BluetoothGattCharacteristic characteristicRX;

    private final String LIST_NAME = "NAME";
    private final String LIST_UUID = "UUID";

//    private TextView tv_numagents;

    public final static UUID HM_RX_TX =
            UUID.fromString(HERE_GattAttributes.HM_RX_TX);

    private ListView lvEquipList;

    private ArrayList<MyHereAgent> pairedEquipList;

    private byte count = 0;
    private int found = 0;

    private static final int REQUEST_ENABLE_BT = 1;
    // Stops scanning after 10 seconds.
    private static final long SCAN_PERIOD = 10000;

    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
                Toast.makeText(getActivity(), "Unable to initialize Bluetooth", Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
            // Automatically connects to the device upon successful start-up initialization.
            mBluetoothLeService.connect(mDeviceAddress);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
        }
    };

    // Handles various events fired by the Service.
    // ACTION_GATT_CONNECTED: connected to a GATT server.
    // ACTION_GATT_DISCONNECTED: disconnected from a GATT server.
    // ACTION_GATT_SERVICES_DISCOVERED: discovered GATT services.
    // ACTION_DATA_AVAILABLE: received data from the device.  This can be a result of read
    //                        or notification operations.
    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                count = 0;
                Log.d("BR", "connect");
                //mConnected = true;
            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
                //mConnected = false;
                Log.d("BR", "disconnect");
                Intent gattServiceIntent = new Intent(getActivity(), BluetoothLeService.class);
                getActivity().bindService(gattServiceIntent, mServiceConnection, getActivity().BIND_AUTO_CREATE);
            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                // Show all the supported services and characteristics on the user interface.
                Log.d("BR", "discover");
                displayGattServices(mBluetoothLeService.getSupportedGattServices());
            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
                if(mBluetoothLeService != null) {
                    Log.d("BR", "data");
                    count++;
                    if(count == 1){
                        setCommandToHERE_agent((byte) 95);
                    }else if (count < 13) {
                        if (setCommandToHERE_agent(count)) {
                            count--;
                        }
                    }
                    else if (count == 18 && pairedEquipList.size() > 1) {
                        found ++;
                        if(found > pairedEquipList.size()-1) {
                            count = 33;
                        }else {
                            mDeviceAddress = pairedEquipList.get(found).getMyeqMacId();
                            if(mBluetoothLeService != null) {
                                mBluetoothLeService.disconnect();
                                mBluetoothLeService.initialize();
                            }
                        }
                    }
                }
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler();

        // Use this check to determine whether BLE is supported on the device.  Then you can
        // selectively disable BLE-related features.
        if (!getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(getActivity(), "You need BLE support device", Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }
        // Initializes a Bluetooth adapter.  For API level 18 and above, get a reference to
        // BluetoothAdapter through BluetoothManager.
        final BluetoothManager bluetoothManager =
                (BluetoothManager) getActivity().getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        // Checks if Bluetooth is supported on the device.
        if (mBluetoothAdapter == null) {
            Toast.makeText(getActivity(), "You need BLUETOOTH support device", Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }
        Intent gattServiceIntent = new Intent(getActivity(), BluetoothLeService.class);
        getActivity().registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
        getActivity().bindService(gattServiceIntent, mServiceConnection, getActivity().BIND_AUTO_CREATE);

        scanLeDevice(true);

        equipListAdapter = new HERE_DeviceListAdapter();
        View view = inflater.inflate(R.layout.fragment_devicelist, container, false);
        lvEquipList = (ListView) view.findViewById(R.id.fragment1_equiplist);
        lvEquipList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                scanLeDevice(false);

                final MyHereAgent device = pairedEquipList.get(position);
                if (device == null) return;
                final Intent intent = new Intent(getActivity(), DoingExerciseActivity.class);
                intent.putExtra(DoingExerciseActivity.EXTRAS_DEVICE_NAME, device.getMyeqName());
                intent.putExtra(DoingExerciseActivity.EXTRAS_DEVICE_ADDRESS, device.getMyeqMacId());
                if (mScanning) {
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                    mScanning = false;
                }
                if(mBluetoothLeService != null){
                    mBluetoothLeService.disconnect();
                    mBluetoothLeService.close();
                    mBluetoothLeService = null;
                }
                startActivity(intent);
            }
        });
        lvEquipList.setAdapter(equipListAdapter);

//        tv_numagents = (TextView) view.findViewById(R.id.equiplist_numagents);
//        tv_numagents.setText(String.format("%d", equipListAdapter.getCount()));


        //getActionBar().setTitle(mDeviceName);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        return view;
    }

    // Demonstrates how to iterate through the supported GATT Services/Characteristics.
    // In this sample, we populate the data structure that is bound to the ExpandableListView
    // on the UI.
    private void displayGattServices(List<BluetoothGattService> gattServices) {
        if (gattServices == null) return;
        String uuid = null;
        ArrayList<HashMap<String, String>> gattServiceData = new ArrayList<HashMap<String, String>>();


        // Loops through available GATT Services.
        for (BluetoothGattService gattService : gattServices) {
            HashMap<String, String> currentServiceData = new HashMap<String, String>();
            uuid = gattService.getUuid().toString();
            currentServiceData.put(
                    LIST_NAME, HERE_GattAttributes.lookup(uuid, "unknown"));

            // If the service exists for HM 10 Serial, say so.
            if(HERE_GattAttributes.lookup(uuid, "unknown") == "HM 10 Serial") {
            } else {
            }
            currentServiceData.put(LIST_UUID, uuid);
            gattServiceData.add(currentServiceData);

            // get characteristic when UUID matches RX/TX UUID
            characteristicTX = gattService.getCharacteristic(BluetoothLeService.UUID_HM_RX_TX);
            characteristicRX = gattService.getCharacteristic(BluetoothLeService.UUID_HM_RX_TX);

            if(characteristicTX != null && mBluetoothLeService != null) {
                characteristicRX.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                mBluetoothLeService.writeCharacteristic(characteristicTX);
                mBluetoothLeService.setCharacteristicNotification(characteristicRX,true);
            }
        }

    }

    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }

    public boolean setCommandToHERE_agent(byte command){
        byte[] val = new byte[1];
        val[0] = command;
        if(characteristicTX == null){
            return false;
        }else {
            characteristicTX.setValue(val);
            if (mBluetoothLeService != null) {
                boolean status = mBluetoothLeService.writeCharacteristic(characteristicTX);
                return status;
            }
            return false;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // User chose not to enable Bluetooth.
        if (requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_CANCELED) {
            getActivity().finish();
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        scanLeDevice(false);
        if(mBluetoothLeService != null) {
            mBluetoothLeService.disconnect();
            mBluetoothLeService.close();
        }
        try {
            getActivity().unregisterReceiver(mGattUpdateReceiver);
            getActivity().unbindService(mServiceConnection);
        }catch (Exception e){

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mBluetoothLeService != null) {
            mBluetoothLeService.disconnect();
            mBluetoothLeService.close();
        }
        try {
            getActivity().unregisterReceiver(mGattUpdateReceiver);
            getActivity().unbindService(mServiceConnection);
        }catch (Exception e){

        }
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d("aaaa", "onResume");
        //equipListAdapter.clear();
        pairedEquipList.clear();
        pairedEquipList = new ArrayList<MyHereAgent>();
//        tv_numagents = (TextView) view.findViewById(R.id.equiplist_numagents);
//        tv_numagents.setText(String.format("%d", equipListAdapter.getCount()));
        equipListAdapter.notifyDataSetChanged();
        Log.d(TAG, "onResume");
        // Ensures Bluetooth is enabled on the device.  If Bluetooth is not currently enabled,
        // fire an intent to display a dialog asking the user to grant permission to enable it.
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        Intent gattServiceIntent = new Intent(getActivity(), BluetoothLeService.class);
        getActivity().registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
        getActivity().bindService(gattServiceIntent, mServiceConnection, getActivity().BIND_AUTO_CREATE);

        scanLeDevice(true);

    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG, "onStop");
        scanLeDevice(false);
        equipListAdapter.clear();
    }


    private void scanLeDevice(final boolean enable) {
        if (enable) {
            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                }
            }, SCAN_PERIOD);

            mScanning = true;
            mBluetoothAdapter.startLeScan(mLeScanCallback);
        } else {
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }
    }

    // Adapter for holding devices found through scanning.
    private class HERE_DeviceListAdapter extends BaseAdapter {
        private LayoutInflater mInflator;

        public HERE_DeviceListAdapter() {
            super();
            pairedEquipList = new ArrayList<MyHereAgent>();
            mLEdeviceList = new ArrayList<BluetoothDevice>();
            mInflator = getActivity().getLayoutInflater();
        }

        public void addDevice(BluetoothDevice device) {
            if(!mLEdeviceList.contains(device)) {
                mLEdeviceList.add(device);
                if(!pairedEquipList.contains(device.getAddress())){
                    if(device.getName().contains("HERE")) {
                        if(pairedEquipList.size() == 0){
                            Log.d("addDevice", "device");
                            scanLeDevice(false);
                            mDeviceAddress = device.getAddress();
                            Intent gattServiceIntent = new Intent(getActivity(), BluetoothLeService.class);
                            getActivity().bindService(gattServiceIntent, mServiceConnection, getActivity().BIND_AUTO_CREATE);


                        }
//                        int connectedAgentType;
//                        if (device.getName().contains("DB")) {
//                            connectedAgentType = MyHereAgent.TYPE_DUMBEL;
//                        } else if (device.getName().contains("PU")) {
//                            connectedAgentType = MyHereAgent.TYPE_PUSH_UP;
//                        } else if (device.getName().contains("HH")) {
//                            connectedAgentType = MyHereAgent.TYPE_HOOLA_HOOP;
//                        } else if (device.getName().contains("JR")) {
//                            connectedAgentType = MyHereAgent.TYPE_JUMP_ROPE;
//                        } else {
//                            connectedAgentType = MyHereAgent.TYPE_OTHERS;
//                        }

                        String deviceAddress = device.getAddress();
                        String deviceName = device.getName();
                        String deviceMajorId = parseMajorId(device.getName());
                        String deviceMinorId = parseMinorId(device.getName());
                        int deviceType = getTypeByMinorId(deviceMinorId);

                        pairedEquipList.add(new MyHereAgent(deviceAddress, deviceName, deviceType, deviceMajorId, deviceMinorId));
                    }
                }
            }
        }

        public BluetoothDevice getDevice(int position) {
            return mLEdeviceList.get(position);
        }

        public void clear() {
            pairedEquipList.clear();
        }

        @Override
        public int getCount() {
            return pairedEquipList.size();
        }

        @Override
        public Object getItem(int i) {
            return pairedEquipList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            // General ListView optimization code.
            if (view == null) {
                int res = 0;
                res = R.layout.listitem_equipment_simple;
                view = mInflator.inflate(res, viewGroup, false);

            }

            ImageView eqTypeImage = (ImageView)view.findViewById(R.id.equiplist_img);
            TextView eqName = (TextView)view.findViewById(R.id.equiplist_name);
            TextView eqId = (TextView)view.findViewById(R.id.equiplist_id);
            TextView eqSensorId = (TextView)view.findViewById(R.id.equiplist_sensorid);

            switch (pairedEquipList.get(i).getMyeqType()) {
                case MyHereAgent.TYPE_DUMBEL:
                    eqTypeImage.setImageResource(R.drawable.eq_01_dumbbell);
                    break;
                case MyHereAgent.TYPE_PUSH_UP:
                    eqTypeImage.setImageResource(R.drawable.eq_02_pushupbar);
                    break;
                case MyHereAgent.TYPE_JUMP_ROPE:
                    eqTypeImage.setImageResource(R.drawable.eq_03_jumprope);
                    break;

                case MyHereAgent.TYPE_HOOLA_HOOP:
                    eqTypeImage.setImageResource(R.drawable.eq_04_hoolahoop);
                    break;
                default:
                    eqTypeImage.setImageResource(R.drawable.eq_01_dumbbell);
                    break;
            }

            eqName.setText(pairedEquipList.get(i).getMyeqName());
            eqId.setText(pairedEquipList.get(i).getMyeqMacId());
            eqSensorId.setText(pairedEquipList.get(i).getMyeqBeaconMajorId() + "-" + pairedEquipList.get(i).getMyeqBeaconMinorId());



            return view;
        }
    }

    // Device scan callback.
    private BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback() {

        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
            try {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        equipListAdapter.addDevice(device);
//                        tv_numagents = (TextView) view.findViewById(R.id.equiplist_numagents);
//                        tv_numagents.setText(String.format("%d", equipListAdapter.getCount()));
                        equipListAdapter.notifyDataSetChanged();
                    }
                });
            }catch (Exception e){};
        }
    };

    private String parseMajorId(String deviceName) {
        if (deviceName.contains("-")) {
            int separatorLoc = deviceName.indexOf("-");
            return deviceName.substring(0, separatorLoc);
        } else {
            return "";
        }
    }

    private String parseMinorId(String deviceName) {
        if (deviceName.contains("-")) {
            int separatorLoc = deviceName.indexOf("-");
            return deviceName.substring(separatorLoc + 1, deviceName.length());
        } else {
            return "";
        }
    }

    private int getTypeByMinorId(String minorId) {
        if (minorId.contains("DB") || minorId.contains("Dumbbell") || minorId.contains("Dumbbel") || minorId.contains("Dumbel")) {
            return 1;
        }else if (minorId.contains("PU") || minorId.contains("Pushupbar")) {
            return 2;
        }else if (minorId.contains("JR") || minorId.contains("Jumprope")) {
            return 3;
        }else if (minorId.contains("HH") || minorId.contains("Hoolahoop")) {
            return 4;
        } else {
            return 0;
        }
    }

}