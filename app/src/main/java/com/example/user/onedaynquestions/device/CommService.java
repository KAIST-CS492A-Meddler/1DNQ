package com.example.user.onedaynquestions.device;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by user on 2016-04-18.
 */
public class CommService {

    private static final String TAG = "BluetoothChatService";
    private static final boolean D = true;

    // Name for the SDP record when creating server socket
    private static final String NAME = "BluetoothChat";

    // Unique UUID for this application
    private ArrayList<UUID> mUuids;
    private static UUID MY_UUID;

    // Member fields
    private final BluetoothAdapter mAdapter;
    private final Handler mHandler;
    private AcceptThread mAcceptThread;
    private ConnectThread mConnectThread;
    private ConnectedThread mConnectedThread;
    private int mState;

    private ArrayList<String> mDeviceAddresses;
    private ArrayList<String> mDeviceNames;
    private ArrayList<ConnectedThread> mConnThreads;
    private ArrayList<BluetoothSocket> mSockets;

    public static final int STATE_NONE = 0;       // we're doing nothing. only valid during setup/shutdown
    public static final int STATE_LISTEN = 1;     // now listening for incoming connections
    public static final int STATE_CONNECTING = 2; // now initiating an outgoing connection
    public static final int STATE_CONNECTED = 3;  // now connected to a remote device

    public CommService(Context context, Handler handler) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        mAdapter = BluetoothAdapter.getDefaultAdapter();
        mState = STATE_NONE;
        mHandler = handler;

        initializeArrayLists();
//        mDeviceAddresses = new ArrayList<String>();
//        mConnThreads = new ArrayList<ConnectedThread>();
//        mSockets = new ArrayList<BluetoothSocket>();
//        mUuids = new ArrayList<UUID>();
//        // 7 randomly-generated UUIDs. These must match on both server and client.
//        mUuids.add(UUID.fromString("00001101-0000-1000-8000-00805f9b34fc"));
//        mUuids.add(UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"));
//        mUuids.add(UUID.fromString("00001112-0000-1000-8000-00805f9b34fb"));
//        mUuids.add(UUID.fromString("00001112-0000-1000-8000-00805f9b34fb"));
//        mUuids.add(UUID.fromString("00001112-0000-1000-8000-00805f9b34fb"));
//        mUuids.add(UUID.fromString("00001112-0000-1000-8000-00805f9b34fb"));
//        mUuids.add(UUID.fromString("00001112-0000-1000-8000-00805f9b34fb"));
    }
    public static UUID getMY_UUID() {
        return MY_UUID;
    }

    public static void setMY_UUID(UUID mY_UUID) {
        MY_UUID = mY_UUID;
    }

    public boolean isDeviceConnectedAtPosition(int position) {
        if (mConnThreads.get(position) == null) {
            return false;
        }
        return true;
    }

    private void initializeArrayLists() {
        mDeviceAddresses = new ArrayList<String>(5);
        mDeviceNames = new ArrayList<String>(5);
        mConnThreads = new ArrayList<ConnectedThread>(5);
        mSockets = new ArrayList<BluetoothSocket>(5);
        mUuids = new ArrayList<UUID>(5);

        for (int i = 0; i < 5; i++) {
            mDeviceAddresses.add(null);
            mDeviceNames.add(null);
            mConnThreads.add(null);
            mSockets.add(null);
            mUuids.add(null);
        }

        Log.i(TAG, "mConnThreads.size() in Service Constructor--"
                + mConnThreads.size());
    }

    public ArrayList<String> getmDeviceNames() {
        return this.mDeviceNames;
    }

    public void setmDeviceNames(ArrayList<String> mDeviceNames) {
        this.mDeviceNames = mDeviceNames;
    }

    public ArrayList<String> getmDeviceAddresses() {
        return mDeviceAddresses;
    }

    public void setmDeviceAddresses(ArrayList<String> mDeviceAddresses) {
        this.mDeviceAddresses = mDeviceAddresses;
    }

    private synchronized void setState(int state) {
        mState = state;

        // Give the new state to the Handler so the UI Activity can update
        mHandler.obtainMessage(BluetoothComm.MESSAGE_STATE_CHANGE, state, -1).sendToTarget();
    }

    /**
     * Return the current connection state. */
    public synchronized int getState() {
        return mState;
    }

    /**
     * Start the chat service. Specifically start AcceptThread to begin a
     * session in listening (server) mode. Called by the Activity onResume() */
    public synchronized void start() {
        if (D) Log.d(TAG, "start");

        // Cancel any thread attempting to make a connection
        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }

        // Cancel any thread currently running a connection
        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }

        // Start the thread to listen on a BluetoothServerSocket
        if (mAcceptThread == null) {
            mAcceptThread = new AcceptThread();
            mAcceptThread.start();
        }
        setState(STATE_LISTEN);
    }

    public synchronized void connect(BluetoothDevice device, int selectedPosition) {

        if (getPositionIndexOfDevice(device) == -1) {
            if (D)
                Log.d(TAG, "connect to: " + device);

        if (mState == STATE_CONNECTING) {
            if (mConnectThread != null) {
                mConnectThread.cancel();
                mConnectThread = null;
            }
        }

            // Cancel any thread currently running a connection
            if (mConnThreads.get(selectedPosition) != null) {
                mConnThreads.get(selectedPosition).cancel();
                // mConnectedThread = null;
                mConnThreads.set(selectedPosition, null);
            }


//            for (int i = 0; i < 1; i++) {
//            try {
//                mConnectThread = new ConnectThread(device, mUuids.get(i),i);
//                mConnectThread.start();
//
//                setState(STATE_CONNECTING);
//            } catch (Exception e) {
//            }
//        }
            // Create a new thread and attempt to connect to each UUID
            // one-by-one.
            try {
                // String
                // s="00001101-0000-1000-8000"+device.getAddress().split(":");

                //00001101-0000-1000-8000-00805F9B34FB
                ConnectThread mConnectThread = new ConnectThread(device,
                        UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"),
                        selectedPosition);
                Log.i(TAG, "uuid-string at server side"
                        + ("00001101-0000-1000-8000" + device.getAddress()
                        .replace(":", "")));
                mConnectThread.start();
                setState(STATE_CONNECTING);
            } catch (Exception e) {
            }
        } else {
            Message msg = mHandler
                    .obtainMessage(BluetoothComm.MESSAGE_TOAST);
            Bundle bundle = new Bundle();
            bundle.putString(BluetoothComm.TOAST,
                    "This device " + device.getName() + " Already Connected");
            msg.setData(bundle);
            mHandler.sendMessage(msg);
        }
    }

    /**
     * Start the ConnectedThread to begin managing a Bluetooth connection
     * @param socket  The BluetoothSocket on which the connection was made
     * @param device  The BluetoothDevice that has been connected
     */
    public synchronized void connected(BluetoothSocket socket, BluetoothDevice device, int selectedPosition) {
        if (D) Log.d(TAG, "connected");

        // Start the thread to manage the connection and perform transmissions
        mConnectedThread = new ConnectedThread(socket);
        mConnectedThread.start();
        mConnThreads.set(selectedPosition, mConnectedThread);

        String message = device.getAddress();
        byte[] send = message.getBytes();


        // Send the name of the connected device back to the UI Activity
        Message msg = mHandler.obtainMessage(BluetoothComm.MESSAGE_DEVICE_NAME);
        Bundle bundle = new Bundle();
        bundle.putString(BluetoothComm.DEVICE_NAME, device.getName());
        msg.setData(bundle);
        mHandler.sendMessage(msg);

        setState(STATE_CONNECTED);

        write(send);
    }

    public synchronized void stop() {
        if (D) Log.d(TAG, "stop");
        if (mConnectThread != null) {mConnectThread.cancel(); mConnectThread = null;}
        if (mConnectedThread != null) {mConnectedThread.cancel(); mConnectedThread = null;}
        if (mAcceptThread != null) {mAcceptThread.cancel(); mAcceptThread = null;}

        for (int i = 0; i < 1; i++) {
            mDeviceNames.set(i, null);
            mDeviceAddresses.set(i, null);
            mSockets.set(i, null);
            if (mConnThreads.get(i) != null) {
                mConnThreads.get(i).cancel();
                mConnThreads.set(i, null);
            }
        }

        setState(STATE_NONE);
    }

    /**
     * Write to the ConnectedThread in an unsynchronized manner
     * @param out The bytes to write
     * @see ConnectedThread#write(byte[])
     */
    public void write(byte[] out) {
        // When writing, try to write out to all connected threads
        for (int i = 0; i < mConnThreads.size(); i++) {
            try {
                // Create temporary object
                ConnectedThread r;
                // Synchronize a copy of the ConnectedThread
                synchronized (this) {
                    if (mState != STATE_CONNECTED) return;
                    r = mConnThreads.get(i);
                }
                // Perform the write unsynchronized
                r.write(out);
            } catch (Exception e) {
            }
        }
    }

    private void connectionFailed() {
        setState(STATE_LISTEN);

        // Send a failure message back to the Activity
        Message msg = mHandler.obtainMessage(BluetoothComm.MESSAGE_TOAST);
        Bundle bundle = new Bundle();
        bundle.putString(BluetoothComm.TOAST, "Unable to connect device");
        msg.setData(bundle);
        mHandler.sendMessage(msg);
    }

    /**
     * Indicate that the connection was lost and notify the UI Activity.
     */
    private void connectionLost(BluetoothDevice device) {
        //setState(STATE_LISTEN);
        int positionIndex = getPositionIndexOfDevice(device);
        if (positionIndex != -1) {
            Log.i(TAG, "getPositionIndexOfDevice(device) ==="
                    + mDeviceAddresses.get(getPositionIndexOfDevice(device)));
            mDeviceAddresses.set(positionIndex, null);
            mDeviceNames.set(positionIndex, null);
            mConnThreads.set(positionIndex, null);

            Message msg = mHandler
                    .obtainMessage(BluetoothComm.MESSAGE_TOAST);
            Bundle bundle = new Bundle();
            bundle.putString(BluetoothComm.TOAST,
                    "Device connection was lost from " + device.getName());
            msg.setData(bundle);
            mHandler.sendMessage(msg);
        }
//        // Send a failure message back to the Activity
//        Message msg = mHandler.obtainMessage(BluetoothComm.MESSAGE_TOAST);
//        Bundle bundle = new Bundle();
//        bundle.putString(BluetoothComm.TOAST, "Device connection was lost");
//        msg.setData(bundle);
//        mHandler.sendMessage(msg);
    }

    private class AcceptThread extends Thread {
        // The local server socket
        private final BluetoothServerSocket mmServerSocket;

        public AcceptThread() {
            BluetoothServerSocket tmp = null;
            // Create a new listening server socket
            try {

                if (mAdapter.isEnabled()) {
                    CommService.setMY_UUID(UUID
                            .fromString("00001101-0000-1000-8000-"
                                    + mAdapter.getAddress().replace(":", "")));
                }
                Log.i(TAG, "MY_UUID.toString()=="
                        + CommService.getMY_UUID().toString());

                tmp = mAdapter.listenUsingRfcommWithServiceRecord(NAME,
                        CommService.getMY_UUID());
            } catch (IOException e) {
                Log.e(TAG, "listen() failed", e);
            }
            mmServerSocket = tmp;
        }

        public void run() {
            if (D) Log.d(TAG, "BEGIN mAcceptThread" + this);
            setName("AcceptThread");
            BluetoothSocket socket = null;
            Log.i(TAG, "mState in acceptThread==" + mState);
            // Listen to the server socket if we're not connected
            while (mState != STATE_CONNECTED) {
                try {
                    // This is a blocking call and will only return on a
                    // successful connection or an exception
                    socket = mmServerSocket.accept();
                } catch (IOException e) {
                    Log.e(TAG, "accept() failed", e);
                    break;
                }

                // If a connection was accepted
                if (socket != null) {
                    synchronized (CommService.this) {
                        switch (mState) {
                            case STATE_LISTEN:
                            case STATE_CONNECTING:
                                // Situation normal. Start the connected thread.
                                connected(socket, socket.getRemoteDevice(),getAvailablePositionIndexForNewConnection(socket.getRemoteDevice()));
                                break;
                            case STATE_NONE:
                            case STATE_CONNECTED:
                                // Either not ready or already connected. Terminate
                                // new socket.
                                try {
                                    socket.close();
                                } catch (IOException e) {
                                    Log.e(TAG, "Could not close unwanted socket", e);
                                }
                                break;
                        }
                    }
                }
            }
            if (D)
                Log.i(TAG, "END mAcceptThread");
//            try {
//                // Listen for all 7 UUIDs
//                for (int i = 0; i < 1; i++) {
//                    mmServerSocket = mAdapter.listenUsingRfcommWithServiceRecord(NAME, mUuids.get(i));
//                    socket = mmServerSocket.accept();
//                    if (socket != null) {
//                        String address = socket.getRemoteDevice().getAddress();
//                        mSockets.add(socket);
//                        mDeviceAddresses.add(address);
//                        connected(socket, socket.getRemoteDevice());
//                    }
//                }
//            } catch (IOException e) {
//                Log.e(TAG, "accept() failed", e);
//            }
//            if (D) Log.i(TAG, "END mAcceptThread");
        }

        public void cancel() {
            if (D) Log.d(TAG, "cancel " + this);
            try {
                mmServerSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "close() of server failed", e);
            }
        }
    }

    /**
     * This thread runs while attempting to make an outgoing connection
     * with a device. It runs straight through; the connection either
     * succeeds or fails.
     */
    private class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;
        private UUID tempUuid;
        private int selectedPosition;

        public ConnectThread(BluetoothDevice device, UUID uuid, int selectedPosition) {
            mmDevice = device;
            BluetoothSocket tmp = null;
            tempUuid = uuid;

            this.selectedPosition = selectedPosition;
            // Get a BluetoothSocket for a connection with the
            // given BluetoothDevice
            try {
                tmp = device.createRfcommSocketToServiceRecord(uuid);
            } catch (IOException e) {
                Log.e(TAG, "create() failed", e);
            }
            mmSocket = tmp;
//            // Get a BluetoothSocket for a connection with the
//            // given BluetoothDevice
//            try {
//                tmp = device.createRfcommSocketToServiceRecord(uuid);
//            } catch (IOException e) {
//                Log.e(TAG, "create() failed", e);
//            }
//            mmSocket = tmp;

        }

        public void run() {
            Log.i(TAG, "BEGIN mConnectThread");
            setName("ConnectThread");

            // Always cancel discovery because it will slow down a connection
            mAdapter.cancelDiscovery();
            // Make a connection to the BluetoothSocket
            try {
                // This is a blocking call and will only return on a
                // successful connection or an exception
                mmSocket.connect();
            } catch (IOException e) {
                //if (tempUuid.toString().contentEquals(mUuids.get(6).toString())) {
                    connectionFailed();//thread);
                //}
                // Close the socket
                try {
                    mmSocket.close();
                } catch (IOException e2) {
                    Log.e(TAG, "unable to close() socket during connection failure", e2);
                }
                // Start the service over to restart listening mode
                CommService.this.start();
                return;
            }

            // Reset the ConnectThread because we're done
            synchronized (CommService.this) {
                mConnectThread = null;
            }
            mDeviceAddresses.set(selectedPosition, mmDevice.getAddress());
            mDeviceNames.set(selectedPosition, mmDevice.getName());
            // Start the connected thread
            connected(mmSocket, mmDevice, selectedPosition);
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "close() of connect socket failed", e);
            }
        }
    }

    /**
     * This thread runs during a connection with a remote device.
     * It handles all incoming and outgoing transmissions.
     */
    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            Log.d(TAG, "create ConnectedThread");
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the BluetoothSocket input and output streams
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                Log.e(TAG, "temp sockets not created", e);
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            Log.i(TAG, "BEGIN mConnectedThread");
            byte[] buffer = new byte[1024];
            int bytes;

            // Keep listening to the InputStream while connected
            while (true) {
                try {
                    // Read from the InputStream
                    bytes = mmInStream.read(buffer);
                    // Send the obtained bytes to the UI Activity
                    mHandler.obtainMessage(BluetoothComm.MESSAGE_READ, bytes,getPositionIndexOfDevice(mmSocket.getRemoteDevice()),buffer).sendToTarget();

                } catch (IOException e) {
                    Log.e(TAG, "disconnected", e);
                    connectionLost(mmSocket.getRemoteDevice());
                    break;
                }
            }
        }

        public void write(byte[] buffer) {


            //for(int i = 0; i < 300 ;i++){
                String tempMsg = String.valueOf(buffer) + ": " + Math.random()*100;
                byte[] tempByte = tempMsg.getBytes();

                try {
                    mmOutStream.write(tempByte);
                    System.out.println("send it!!!");
                    //sleep(200);

                } catch (IOException e) {
                    Log.e(TAG, "Exception during write", e);
                }
           // }
        }

        public void cancel() {

            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "close() of connect socket failed", e);
            }
        }
    }
    public int getPositionIndexOfDevice(BluetoothDevice device) {
        for (int i = 0; i < mDeviceAddresses.size(); i++) {
            if (mDeviceAddresses.get(i) != null
                    && mDeviceAddresses.get(i).equalsIgnoreCase(
                    device.getAddress()))
                return i;
        }
        return -1;
    }

    public int getAvailablePositionIndexForNewConnection(BluetoothDevice device) {
        if (getPositionIndexOfDevice(device) == -1) {
            for (int i = 0; i < mDeviceAddresses.size(); i++) {
                if (mDeviceAddresses.get(i) == null) {
                    return i;
                }
            }
        }
        return -1;
    }
}