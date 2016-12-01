package com.example.user.onedaynquestions.view.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.onedaynquestions.R;
import com.example.user.onedaynquestions.deviceLE.BluetoothLeService;
import com.example.user.onedaynquestions.deviceLE.HERE_GattAttributes;
import com.example.user.onedaynquestions.model.MyHereAgent;
import com.example.user.onedaynquestions.model.MyRoutine;
import com.example.user.onedaynquestions.model.RecordAgent;
import com.example.user.onedaynquestions.utility.PeakDetector;
import com.example.user.onedaynquestions.utility.SinusoidalDetector;
import com.example.user.onedaynquestions.utility.TaskScheduler;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.UUID;

/**
 * Created by user on 2016-06-07.
 */
public class DoingExerciseActivity extends AppCompatActivity {
    public static final String TAG = "DoingExerciseActivity";
    private static final int GOAL_SET = 51;
    private static final int GOAL_COUNT = 52;
    private static final int GOAL_TIME = 53;

    private static final int RAND_MIN = 0;
    private static final int RAND_MAX = 3;

    public static Activity thisActivity;
    MyRoutine myRoutine;

    LinearLayout layout_whole;

    TextView tv_timer;

    LinearLayout layout_title;
    TextView exercisePhase;
    ImageView iv_current_eq;
    TextView tv_eq_order;
    TextView tv_eq_name;
    TextView tv_eq_goal;
    TextView tv_eq_count;
    TextView tv_eq_samplingRate;
    private GraphView gv;

    ArrayList<RecordAgent> agentRecords;
    RecordAgent currentAgent = new RecordAgent();
    int numAgents;
    int currentOrder;


    int currentRecordCount;
    int currentRecordTime;
    private Runnable increaseTimer;

    TaskScheduler timer;
    boolean isTimerRunning;

    Vibrator vib;
    private Runnable invalidator;
    private final Handler mHandler = new Handler();

    //device components
    private int connectedAgentType;

    //data components
    private char startBit;

    public static final char INDEX_ACC_X = 'a';
    public static final char INDEX_GYRO_X = 'g';
    public static final char INDEX_MAG_X = 'm';
    public static final char INDEX_ACC_Y= 'a';
    public static final char INDEX_GYRO_Y = 'g';
    public static final char INDEX_MAG_Y = 'm';
    public static final char INDEX_ACC_Z = 'a';
    public static final char INDEX_GYRO_Z = 'g';
    public static final char INDEX_MAG_Z = 'm';
    public static final char INDEX_FORCE = 'f';
    public static final String END_BIT = "@"; // do not use
    //number of measurements typs
    /*
    3 Axis Accelerometer
    3 Axis Gyroscope
    3 Axis Magnetometer
    1 Force Sensor - Load cell
     */
    public PeakDetector dumbel;
    public PeakDetector pushup;
    public SinusoidalDetector hoop;
    private final int numberOfMeasures = 10;
    private long startTime;
    private float[] values;
    private int usingValue;

    float[] offsets;

    boolean isSetOffset;
    CountDownTimer cdt, srChecker;
    float[] roughLPF;
    float[][] LPFbuffer;
    final int LPFwindow = 20;
    int[] latestIdx;
    private final float radToDeg = (float)(180/ Math.PI);
    private int countGyro = 0;
    private int countAcc = 0;

    private int samplingCount;
    private float samplingRate;
    private float[][] longtermAverageBuffer;
    private float[] longtermAverage;
    private int[] longTermIndex;
    private final int longterm = 3*100; //approx. 3s

    private int countCurrentEq = 0;
    private boolean isRisingPeak, isFallingPeak;

    private int prevForce = 0;
    private int pushUpThreshold = 2800;


    //Bluetooth components
    public static final String EXTRAS_DEVICE_NAME = "DEVICE_NAME";
    public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";
    private String mDeviceName;
    private String mDeviceAddress;

    private BluetoothLeService mBluetoothLeService;
    private boolean mConnected = false;
    private BluetoothGattCharacteristic characteristicTX;
    private BluetoothGattCharacteristic characteristicRX;

    private final String LIST_NAME = "NAME";
    private final String LIST_UUID = "UUID";
    LineGraphSeries<DataPoint> series;

    public final static UUID HM_RX_TX =
            UUID.fromString(HERE_GattAttributes.HM_RX_TX);

    // Code to manage Service lifecycle.
    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
                Log.e(TAG, "Unable to initialize Bluetooth");
                finish();
            }
            // Automatically connects to the device upon successful start-up initialization.
            mBluetoothLeService.connect(mDeviceAddress);
            final Runnable connection = new Runnable() {
                @Override
                public void run() {
                    final boolean state = mBluetoothLeService.connect(mDeviceAddress);
                    if(!state){
                        Log.d("", "connect fail");
                        try {
                            wait(100);
                        }catch (Exception e){}
                        run();
                    }else{
                    }
                }
            };
            connection.run();
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
                mConnected = true;
                invalidateOptionsMenu();
            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
                mConnected = false;
                invalidateOptionsMenu();
            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                // Show all the supported services and characteristics on the user interface.
                displayGattServices(mBluetoothLeService.getSupportedGattServices());
            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {

                appendData(intent.getStringExtra(mBluetoothLeService.EXTRA_DATA));
            }
        }
    };
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

    SoundPool soundPool;
    int soundId1;
    boolean isSoundFirst =true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doingexercise);


        myRoutine = MainActivity.mySelectedRoutine;

        if (myRoutine == null) {

        } else {
//            Log.d("mySelectedRoutine", "myRoutine id" + myRoutine.getRoutineId());
//            Log.d("mySelectedRoutine", "myRoutine name" + myRoutine.getRoutineName());
//            Log.d("mySelectedRoutine", "myRoutine eq1-id" + myRoutine.getRoutineEq1Id());
//            Log.d("mySelectedRoutine", "myRoutine eq1-goal" + myRoutine.getRoutineEq1Goal());
//            Log.d("mySelectedRoutine", "myRoutine eq2-id" + myRoutine.getRoutineEq2Id());
//            Log.d("mySelectedRoutine", "myRoutine eq2-goal" + myRoutine.getRoutineEq2Goal());
//            Log.d("mySelectedRoutine", "myRoutine eq3-id" + myRoutine.getRoutineEq3Id());
//            Log.d("mySelectedRoutine", "myRoutine eq3-goal" + myRoutine.getRoutineEq3Goal());
//            Log.d("mySelectedRoutine", "myRoutine eq4" + myRoutine.getRoutineEq4Id());
//            Log.d("mySelectedRoutine", "myRoutine eq4" + myRoutine.getRoutineEq4Goal());
//            Log.d("mySelectedRoutine", "myRoutine eq5" + myRoutine.getRoutineEq5Id());
//            Log.d("mySelectedRoutine", "myRoutine eq5" + myRoutine.getRoutineEq5Goal());


            //Arraylist to save the user's records
            agentRecords = new ArrayList<>();
            numAgents = 0;
            currentOrder = 0;
            dumbel = new PeakDetector(0);
            dumbel.setDelta(60);
            hoop = new SinusoidalDetector(51*25.4, 386*8 );
            vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

            initWidgets();
            initAgentValues();
            initAgentRecords();

            initSamplingRateUnit();
            initPreprocessingUnits();

            soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
            soundId1 = soundPool.load(this, R.raw.bell, 1);

            increaseTimer = new Runnable() {
                @Override
                public void run() {

                    //Obtain a current RecordAgent for recording
                    if (numAgents > 0) {

                        currentAgent = agentRecords.get(currentOrder);

                    }



                    Random rand = new Random();
                    int randNum;

                    //TODO: tv_eq_count와 currentRecordCount는 성근이형 코드로 대체
                    if (isTimerRunning) {
                        currentRecordTime++;
                        randNum = rand.nextInt((RAND_MAX - RAND_MIN) + 1) + RAND_MIN;
                        //currentRecordCount += randNum;
                    }
                    tv_timer.setText(secondToTimerString(currentRecordTime));
                    tv_eq_count.setText(String.format("%d", currentRecordCount));
                    if(currentAgent != null) {
                        if (currentAgent.getGoalCount() != -1 && currentRecordCount == currentAgent.getGoalCount() * currentAgent.getGoalSet()) {
                            if (isSoundFirst) {
                                soundPool.play(soundId1, 1, 1, 0, 0, 1);
                                isSoundFirst = false;
                            }
                        }
                        if (currentAgent.getGoalTime() != -1 && currentRecordTime == currentAgent.getGoalTime() * currentAgent.getGoalSet()) {
                            if (isSoundFirst) {
                                soundPool.play(soundId1, 1, 1, 0, 0, 1);
                                isSoundFirst = false;
                            }
                        }
                    }
                }
            };
            timer = new TaskScheduler();
            timer.scheduleAtFixedRate(increaseTimer, 1000);

            invalidator = new Runnable() {
                @Override
                public void run() {
                    double x = (double) (System.currentTimeMillis() - startTime)/1000;
                    //series[vf.getDisplayedChild()].appendData(new DataPoint((double) (System.currentTimeMillis() - startTime) / 1000, (double) values[vf.getDisplayedChild()]), true, 1000);
                    series.appendData(new DataPoint(x, (double) values[connectedAgentType]), true, 300);
                }
            };
            //mHandler.postDelayed(invalidator, 1500);
            isRisingPeak = false;
            isFallingPeak = false;
            startTime = System.currentTimeMillis();

        }

    }
    public int findImage (int type){
        switch (type) {
            case MyHereAgent.TYPE_DUMBEL:
                return R.drawable.eq_01_dumbbell;
            case MyHereAgent.TYPE_PUSH_UP:
                return R.drawable.eq_02_pushupbar;
            case MyHereAgent.TYPE_JUMP_ROPE:
                return R.drawable.eq_03_jumprope;
            case MyHereAgent.TYPE_HOOLA_HOOP:
                return R.drawable.eq_04_hoolahoop;
            case MyHereAgent.TYPE_OTHERS:
                return R.mipmap.ic_setting_user_information;
        }
        return 0;
    }
    public int findImage (){
        switch (connectedAgentType) {
            case MyHereAgent.TYPE_DUMBEL:
                return R.drawable.eq_01_dumbbell;
            case MyHereAgent.TYPE_PUSH_UP:
                return R.drawable.eq_02_pushupbar;
            case MyHereAgent.TYPE_JUMP_ROPE:
                return R.drawable.eq_03_jumprope;
            case MyHereAgent.TYPE_HOOLA_HOOP:
                return R.drawable.eq_04_hoolahoop;
            case MyHereAgent.TYPE_OTHERS:
                return R.mipmap.ic_setting_user_information;
        }
        return 0;
    }

    private void initWidgets() {
        gv = (GraphView) findViewById(R.id.graphX);
        series = new LineGraphSeries();

        series= new LineGraphSeries<DataPoint>();
        series.setColor(Color.RED);

        gv.addSeries(series);
        gv.getViewport().setScrollable(true);

        gv.getViewport().setXAxisBoundsManual(true);
        gv.getViewport().setMinX(0);
        gv.getViewport().setMaxX(5); //second
        gv.getViewport().setBackgroundColor(Color.WHITE);
        gv.getGridLabelRenderer().setGridColor(Color.GRAY);
        gv.getGridLabelRenderer().reloadStyles();
        gv.getViewport().setYAxisBoundsManual(true);

        isTimerRunning = true;

        layout_whole = (LinearLayout) findViewById(R.id.doingexercise_layout_whole);
        layout_title = (LinearLayout) findViewById(R.id.doingexercise_layout_title);
        exercisePhase = (TextView) findViewById(R.id.state);

        tv_timer = (TextView) findViewById(R.id.doingexercise_tv_timer);

        iv_current_eq = (ImageView) findViewById(R.id.doingexercise_iv_current_eq);
        tv_eq_order = (TextView) findViewById(R.id.doingexercise_tv_eq_order);
        tv_eq_name = (TextView) findViewById(R.id.doingexercise_tv_eq_name);
        tv_eq_goal = (TextView) findViewById(R.id.doingexercise_tv_eq_goal);
        tv_eq_count = (TextView) findViewById(R.id.doingexercise_tv_eq_count);
        tv_eq_samplingRate = (TextView) findViewById(R.id.sampling_rate);

        initMeasureValues();

    }

    private void initSamplingRateUnit(){

        samplingCount = 0;
        samplingRate = 0;
        srChecker = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                samplingRate = (float)samplingCount;
                tv_eq_samplingRate.setText("");
                samplingCount = 0;

            }

            @Override
            public void onFinish() {
                isFallingPeak = false;
                isRisingPeak = false;
            }
        };
        srChecker.start();
    }


    private void initPreprocessingUnits(){

        longtermAverageBuffer = new float[numberOfMeasures][longterm];
        longtermAverage = new float[longterm];
        longTermIndex= new int[numberOfMeasures];
        for(int i = 0; i < numberOfMeasures; i++){
            longTermIndex[i] = 0;
            longtermAverage[i] = 0;
            for(int k = 0; k <longterm; k++){
                longtermAverageBuffer[i][k] = 0;
            }
        }


        roughLPF = new float[numberOfMeasures];
        LPFbuffer= new float[numberOfMeasures][LPFwindow];
        latestIdx = new int[numberOfMeasures];
        for(int i = 0; i < numberOfMeasures; i++){
            latestIdx[i] = 0;
            roughLPF[i] = 0;
            for(int k = 0; k < LPFwindow; k++){
                LPFbuffer[i][k] = 0;
            }
        }

        values = new float[numberOfMeasures];
        offsets = new float[numberOfMeasures];
        isSetOffset = false;
        cdt = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                isSetOffset = false;
                for(int i = 0; i < 3; i++){
                    offsets[i] = offsets[i]/countAcc;
                }
                for(int i = 3; i < 6; i++){
                    offsets[i] = offsets[i]/countGyro;
                }
            }
        };
    }


    private void initMeasureValues() {
        currentRecordTime = 0;
        currentRecordCount = 0;

        countCurrentEq = 0;

        tv_timer.setText(secondToTimerString(currentRecordTime));
        tv_eq_count.setText(String.format("%d", currentRecordCount));

        isRisingPeak = false;
        isFallingPeak = false;
        //startTime = System.currentTimeMillis();
    }

    private void initAgentValues() {
        if(myRoutine == null){

            Log.d("mySelectedRoutine", "myRoutine is null");


            final Intent intent = getIntent();
            mDeviceName = intent.getStringExtra(EXTRAS_DEVICE_NAME);
            mDeviceAddress = intent.getStringExtra(EXTRAS_DEVICE_ADDRESS);

            if (mDeviceName.contains("DB")) {
                connectedAgentType = MyHereAgent.TYPE_DUMBEL;
            } else if (mDeviceName.contains("PU")) {
                connectedAgentType = MyHereAgent.TYPE_PUSH_UP;
            } else if (mDeviceName.contains("HH")) {
                connectedAgentType = MyHereAgent.TYPE_HOOLA_HOOP;
            } else if (mDeviceName.contains("JR")) {
                connectedAgentType = MyHereAgent.TYPE_JUMP_ROPE;
            } else {
                connectedAgentType = MyHereAgent.TYPE_OTHERS;
                Toast.makeText(this, "Incompatible Equipment", Toast.LENGTH_SHORT).show();
            }
            tv_eq_name.setText(mDeviceName);
            tv_eq_goal.setText("000");

        } else {

            Log.d("mySelectedRoutine", "myRoutine is not null");

            String currentAgentMacId;
            String currentAgentGoal;

            if (currentOrder == 0) {
                tv_eq_order.setText(String.format("%dst", currentOrder + 1) + " Exercise");
                currentAgentMacId = myRoutine.getRoutineEq1Id();
                currentAgentGoal = myRoutine.getRoutineEq1Goal();
            } else if (currentOrder == 1) {
                tv_eq_order.setText(String.format("%dnd", currentOrder + 1) + " Exercise");
                currentAgentMacId = myRoutine.getRoutineEq2Id();
                currentAgentGoal = myRoutine.getRoutineEq2Goal();
            } else if (currentOrder == 2) {
                tv_eq_order.setText(String.format("%drd", currentOrder + 1) + " Exercise");
                currentAgentMacId = myRoutine.getRoutineEq3Id();
                currentAgentGoal = myRoutine.getRoutineEq3Goal();
            } else if (currentOrder == 3) {
                tv_eq_order.setText(String.format("%dth", currentOrder + 1) + " Exercise");
                currentAgentMacId = myRoutine.getRoutineEq4Id();
                currentAgentGoal = myRoutine.getRoutineEq4Goal();
            } else {
                tv_eq_order.setText(String.format("%dth", currentOrder + 1) + " Exercise");
                currentAgentMacId = myRoutine.getRoutineEq5Id();
                currentAgentGoal = myRoutine.getRoutineEq5Goal();
            }

            MyHereAgent currentAgent = MainActivity.hereDB.getMyHereAgent(currentAgentMacId);



            try {
                iv_current_eq.setImageResource(findImage(currentAgent.getMyeqType()));
                tv_eq_name.setText(currentAgent.getMyeqName());
                tv_eq_goal.setText(makeGoalString(currentAgentGoal));

                connectedAgentType =  currentAgent.getMyeqType();

                Log.d("mySelectedRoutine", "currentAgent-Name: " + currentAgent.getMyeqName());
                Log.d("mySelectedRoutine", "currentAgent-Type: " + currentAgent.getMyeqType());
                Log.d("mySelectedRoutine", "currentAgent-MacId: " + currentAgent.getMyeqMacId());
                Log.d("mySelectedRoutine", "currentAgent-Goal: " + makeGoalString(currentAgentGoal));


                mDeviceName = "HERE-";

                switch (connectedAgentType){
                    case MyHereAgent.TYPE_DUMBEL:
                        mDeviceName += "DB";
                        break;
                    case MyHereAgent.TYPE_PUSH_UP:
                        mDeviceName += "PU";
                        break;
                    case MyHereAgent.TYPE_HOOLA_HOOP:
                        mDeviceName += "HH";
                        break;
                    case MyHereAgent.TYPE_JUMP_ROPE:
                        mDeviceName += "JR";
                        break;
                }

                if(mDeviceAddress != currentAgent.getMyeqMacId()){
                    mDeviceAddress = currentAgent.getMyeqMacId();
                    Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
                    bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
                    mBluetoothLeService.disconnect();
                    mBluetoothLeService.initialize();
                    mBluetoothLeService.connect(mDeviceAddress);

                }
            } catch (Exception e) {
            }
        }

        switch (connectedAgentType){
            case MyHereAgent.TYPE_DUMBEL:
                startBit = INDEX_GYRO_Y;
                gv.getViewport().setMinY(-250);
                gv.getViewport().setMaxY(250);
                break;
            case MyHereAgent.TYPE_PUSH_UP:
                startBit = INDEX_FORCE;
                gv.getViewport().setMinY(-1000);
                gv.getViewport().setMaxY(5000);
                break;
            case MyHereAgent.TYPE_HOOLA_HOOP:
                startBit = INDEX_GYRO_Y;
                gv.getViewport().setMinY(-250);
                gv.getViewport().setMaxY(250);
                break;
            case MyHereAgent.TYPE_JUMP_ROPE:
                startBit = INDEX_GYRO_X;
                gv.getViewport().setMinY(-250);
                gv.getViewport().setMaxY(250);
                break;
        }
    }


    private void initAgentRecords() {

        agentRecords.clear();
        numAgents = 0;
        currentRecordCount = 0;

        boolean isMoreAgent = true;

        if(myRoutine == null) {
            numAgents++;
            RecordAgent tmpAgentRecord1 = new RecordAgent();
            tmpAgentRecord1.setAgentMacId(mDeviceAddress);
            tmpAgentRecord1.setAgentName(mDeviceName);
            tmpAgentRecord1.setAgentType(MyHereAgent.TYPE_DUMBEL);
            tmpAgentRecord1.setGoal(1, 10, -1);
            tmpAgentRecord1.setRecordCount(10);
            agentRecords.add(tmpAgentRecord1);
            return;
        }
        /* EQ1 */
        if ((myRoutine.getRoutineEq1Id().equals("-1") ||
                myRoutine.getRoutineEq1Id().equals("") ||
                myRoutine.getRoutineEq1Id() == null)) {
            isMoreAgent = false;
            return;
        } else {
            numAgents ++;
            RecordAgent tmpAgentRecord1 = new RecordAgent();
            String agent1MacId = myRoutine.getRoutineEq1Id();
            tmpAgentRecord1.setAgentMacId(agent1MacId);
            tmpAgentRecord1.setAgentName(MainActivity.hereDB.getMyHereAgent(agent1MacId).getMyeqName());
            tmpAgentRecord1.setAgentType(MainActivity.hereDB.getMyHereAgent(agent1MacId).getMyeqType());

            tmpAgentRecord1.setGoalSet(getIntFromGoal(GOAL_SET, myRoutine.getRoutineEq1Goal()));
            tmpAgentRecord1.setGoalCount(getIntFromGoal(GOAL_COUNT, myRoutine.getRoutineEq1Goal()));
            tmpAgentRecord1.setGoalTime(getIntFromGoal(GOAL_TIME, myRoutine.getRoutineEq1Goal()));

            tmpAgentRecord1.setRecordCount(0);
            tmpAgentRecord1.setRecordTime(0);

            agentRecords.add(tmpAgentRecord1);
            Log.d("InitializeAgentRecords", "A record is added: "
                    + tmpAgentRecord1.getAgentName() + " ["
                    + tmpAgentRecord1.getGoalSet() + "|"
                    + tmpAgentRecord1.getGoalCount() + "|"
                    + tmpAgentRecord1.getGoalTime() + "]");
            Log.d("InitializeAgentRecords", "Agent num: " + numAgents);

        }

        if(!isMoreAgent)    return;

        /* EQ2 */
        if ((myRoutine.getRoutineEq2Id().equals("-1") ||
                myRoutine.getRoutineEq2Id().equals("") ||
                myRoutine.getRoutineEq2Id() == null)) {
            isMoreAgent = false;
            return;
        } else {
            numAgents ++;
            RecordAgent tmpAgentRecord2 = new RecordAgent();
            String agent2MacId = myRoutine.getRoutineEq2Id();
            tmpAgentRecord2.setAgentMacId(agent2MacId);
            tmpAgentRecord2.setAgentName(MainActivity.hereDB.getMyHereAgent(agent2MacId).getMyeqName());
            tmpAgentRecord2.setAgentType(MainActivity.hereDB.getMyHereAgent(agent2MacId).getMyeqType());

            tmpAgentRecord2.setGoalSet(getIntFromGoal(GOAL_SET, myRoutine.getRoutineEq2Goal()));
            tmpAgentRecord2.setGoalCount(getIntFromGoal(GOAL_COUNT, myRoutine.getRoutineEq2Goal()));
            tmpAgentRecord2.setGoalTime(getIntFromGoal(GOAL_TIME, myRoutine.getRoutineEq2Goal()));

            tmpAgentRecord2.setRecordCount(0);
            tmpAgentRecord2.setRecordTime(0);

            agentRecords.add(tmpAgentRecord2);
            Log.d("InitializeAgentRecords", "A record is added: "
                    + tmpAgentRecord2.getAgentName() + " ["
                    + tmpAgentRecord2.getGoalSet() + "|"
                    + tmpAgentRecord2.getGoalCount() + "|"
                    + tmpAgentRecord2.getGoalTime() + "]");
            Log.d("InitializeAgentRecords", "Agent num: " + numAgents);

        }

        if(!isMoreAgent)    return;

        /* EQ3 */
        if ((myRoutine.getRoutineEq3Id().equals("-1") ||
                myRoutine.getRoutineEq3Id().equals("") ||
                myRoutine.getRoutineEq3Id() == null)) {
            isMoreAgent = false;
            return;
        } else {
            numAgents ++;
            RecordAgent tmpAgentRecord3 = new RecordAgent();
            String agent3MacId = myRoutine.getRoutineEq3Id();
            tmpAgentRecord3.setAgentMacId(agent3MacId);
            tmpAgentRecord3.setAgentName(MainActivity.hereDB.getMyHereAgent(agent3MacId).getMyeqName());
            tmpAgentRecord3.setAgentType(MainActivity.hereDB.getMyHereAgent(agent3MacId).getMyeqType());

            tmpAgentRecord3.setGoalSet(getIntFromGoal(GOAL_SET, myRoutine.getRoutineEq3Goal()));
            tmpAgentRecord3.setGoalCount(getIntFromGoal(GOAL_COUNT, myRoutine.getRoutineEq3Goal()));
            tmpAgentRecord3.setGoalTime(getIntFromGoal(GOAL_TIME, myRoutine.getRoutineEq3Goal()));

            tmpAgentRecord3.setRecordCount(0);
            tmpAgentRecord3.setRecordTime(0);

            agentRecords.add(tmpAgentRecord3);
            Log.d("InitializeAgentRecords", "A record is added: "
                    + tmpAgentRecord3.getAgentName() + " ["
                    + tmpAgentRecord3.getGoalSet() + "|"
                    + tmpAgentRecord3.getGoalCount() + "|"
                    + tmpAgentRecord3.getGoalTime() + "]");
            Log.d("InitializeAgentRecords", "Agent num: " + numAgents);

        }

        if(!isMoreAgent)    return;

        /* EQ4 */
        if ((myRoutine.getRoutineEq4Id().equals("-1") ||
                myRoutine.getRoutineEq4Id().equals("") ||
                myRoutine.getRoutineEq4Id() == null)) {
            isMoreAgent = false;
            return;
        } else {
            numAgents ++;
            RecordAgent tmpAgentRecord4 = new RecordAgent();
            String agent4MacId = myRoutine.getRoutineEq4Id();
            tmpAgentRecord4.setAgentMacId(agent4MacId);
            tmpAgentRecord4.setAgentName(MainActivity.hereDB.getMyHereAgent(agent4MacId).getMyeqName());
            tmpAgentRecord4.setAgentType(MainActivity.hereDB.getMyHereAgent(agent4MacId).getMyeqType());

            tmpAgentRecord4.setGoalSet(getIntFromGoal(GOAL_SET, myRoutine.getRoutineEq4Goal()));
            tmpAgentRecord4.setGoalCount(getIntFromGoal(GOAL_COUNT, myRoutine.getRoutineEq4Goal()));
            tmpAgentRecord4.setGoalTime(getIntFromGoal(GOAL_TIME, myRoutine.getRoutineEq4Goal()));

            tmpAgentRecord4.setRecordCount(0);
            tmpAgentRecord4.setRecordTime(0);

            agentRecords.add(tmpAgentRecord4);
            Log.d("InitializeAgentRecords", "A record is added: "
                    + tmpAgentRecord4.getAgentName() + " ["
                    + tmpAgentRecord4.getGoalSet() + "|"
                    + tmpAgentRecord4.getGoalCount() + "|"
                    + tmpAgentRecord4.getGoalTime() + "]");
            Log.d("InitializeAgentRecords", "Agent num: " + numAgents);

        }

        if(!isMoreAgent)    return;

        /* EQ5 */
        if ((myRoutine.getRoutineEq5Id().equals("-1") ||
                myRoutine.getRoutineEq5Id().equals("") ||
                myRoutine.getRoutineEq5Id() == null)) {
            isMoreAgent = false;
            return;
        } else {
            numAgents ++;
            RecordAgent tmpAgentRecord5 = new RecordAgent();
            String agent5MacId = myRoutine.getRoutineEq5Id();
            tmpAgentRecord5.setAgentMacId(agent5MacId);
            tmpAgentRecord5.setAgentName(MainActivity.hereDB.getMyHereAgent(agent5MacId).getMyeqName());
            tmpAgentRecord5.setAgentType(MainActivity.hereDB.getMyHereAgent(agent5MacId).getMyeqType());

            tmpAgentRecord5.setGoalSet(getIntFromGoal(GOAL_SET, myRoutine.getRoutineEq5Goal()));
            tmpAgentRecord5.setGoalCount(getIntFromGoal(GOAL_COUNT, myRoutine.getRoutineEq5Goal()));
            tmpAgentRecord5.setGoalTime(getIntFromGoal(GOAL_TIME, myRoutine.getRoutineEq5Goal()));

            tmpAgentRecord5.setRecordCount(0);
            tmpAgentRecord5.setRecordTime(0);

            agentRecords.add(tmpAgentRecord5);
            Log.d("InitializeAgentRecords", "A record is added: "
                    + tmpAgentRecord5.getAgentName() + " ["
                    + tmpAgentRecord5.getGoalSet() + "|"
                    + tmpAgentRecord5.getGoalCount() + "|"
                    + tmpAgentRecord5.getGoalTime() + "]");
            Log.d("InitializeAgentRecords", "Agent num: " + numAgents);

        }

        Log.d("InitializeAgentRecords", "Agent num: " + numAgents);

    }

    private void setOffset(){
        isSetOffset = true;
        countAcc = 0;
        countGyro = 0;
        for(int i = 0; i < 6; i++){
            offsets[i] = 0;
        }
        cdt.start();
    }
    private float LPF(float raw, int what){
        latestIdx[what] = (latestIdx[what]+1) % LPFwindow;
        roughLPF[what] -= LPFbuffer[what][latestIdx[what]];
        LPFbuffer[what][latestIdx[what]] = raw;
        roughLPF[what] += raw;
        return roughLPF[what]/LPFwindow;
    }
    private float longTermAvg(float raw, int what){
        longTermIndex[what] = (longTermIndex[what]+1) % longterm;
        longtermAverage[what] -= longtermAverageBuffer[what][longTermIndex[what]];
        longtermAverageBuffer[what][longTermIndex[what]] = raw;
        longtermAverage[what] += raw;
        return longtermAverage[what]/longterm;
    }
    private int getIntFromGoal(int target, String rawGoal) {

        //Type 1. 3|15|-1
        //Type 2. 2|-1|60

        int goalType = 0;   //1: count times, 2: count secs, 3: count times & secs

        String set = "";    //sets
        String count = "";  //times
        String time = "";   //secs

        StringTokenizer tokens = new StringTokenizer(rawGoal, "|");
        set = tokens.nextToken();
        count = tokens.nextToken();
        time = tokens.nextToken();

        //Single or multiple
        int intSet = Integer.parseInt(set);
        int intCount = Integer.parseInt(count);
        int intTime = Integer.parseInt(time);

        switch (target) {
            case 51:
                return intSet;
            case 52:
                return intCount;
            case 53:
                return intTime;
            default:
                return -1;
        }
    }

    private String secondToTimerString(int seconds) {

        int mins = seconds / 60;
        int secs = seconds % 60;

        String timerString = String.format("%02d:%02d", mins, secs);

        return timerString;
    }





    private void initializeRecord() {
        countCurrentEq = 0;
        currentRecordTime = 0;

        //Restart exercising -> Run timer
        isTimerRunning = true;

    }

    public void mOnClick(View v) {
        switch (v.getId()) {
            case R.id.doingexercise_btn_back:
                AlertDialog quitExercisingAlert = askQuitExercising();
                quitExercisingAlert.show();
                break;
            case R.id.doingexercise_btn_skipgonext:
                if (isTimerRunning) {
                    isTimerRunning = false;
                }
                //timer.stop(increaseTimer);

                Log.d("DoingExerciseOrder", "currentOrder: " + currentOrder + ", agentRecords.size(): " + agentRecords.size());

                //If there is any remaining equipment (or now doing the last one)
                if (currentOrder + 1 < numAgents) {
                    AlertDialog goToNextExerciseAlert = askGoToNextExercise();
                    goToNextExerciseAlert.show();
                }
                //If there is no remaining equipment to do
                else {
                    AlertDialog finishExerciseAlert = askFinishExercise();
                    finishExerciseAlert.show();
                }



                //Toast.makeText(getApplicationContext(), "Skip this equipment\nor Go to next step", Toast.LENGTH_SHORT).show();
                break;
        }
    }



    private AlertDialog askQuitExercising() {
        AlertDialog myDeleteDialogBox = new AlertDialog.Builder(this)
                .setTitle("Quit Exercising")
                .setMessage("Are you sure you want to stop exercising and go to the main page?")
                .setPositiveButton("Give up", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Exercising is stopped.", Toast.LENGTH_SHORT).show();
                        finish();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();

        myDeleteDialogBox.setCanceledOnTouchOutside(false);

        return myDeleteDialogBox;
    }

    private AlertDialog askGoToNextExercise() {
        AlertDialog myGoNextDialogBox = new AlertDialog.Builder(this)
                .setTitle("Go to next step")
                .setMessage("Are you sure you want to finish current exercise?")
                .setPositiveButton("Go next!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(getApplicationContext(), "Exercising is stopped.", Toast.LENGTH_SHORT).show();

                        //TODO: Store previous exercise record to agentRecords
                        agentRecords.get(currentOrder).setRecordCount(currentRecordCount);
                        agentRecords.get(currentOrder).setRecordTime(currentRecordTime);

                        //if currentOrder = 2, numAgent = 4
                        currentOrder++;
                        isSoundFirst = true;

                        //TODO: Initialize DoingExerciseActivity
                        //TODO: Restart timer & measuring sensor
                        isTimerRunning = true;
                        initMeasureValues();
                        initAgentValues();

                        Log.d("DoingExerciseOrder", "currentOrder: " + currentOrder + ", agentRecords.size(): " + agentRecords.size());


                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!isTimerRunning) {
                            isTimerRunning = true;
                        }
                        dialog.dismiss();
                    }
                })
                .create();

        myGoNextDialogBox.setCanceledOnTouchOutside(false);

        return myGoNextDialogBox;
    }

    private AlertDialog askFinishExercise() {
        AlertDialog myFinishDialogBox = new AlertDialog.Builder(this)
                .setTitle("Finish exercising")
                .setMessage("This is the last step of your routine.\nDo you want to finish exercising?")
                .setPositiveButton("Finish", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(), "Exercising is stopped.", Toast.LENGTH_SHORT).show();

                                //TODO: Store previous exercise record to agentRecords
                                agentRecords.get(currentOrder).setRecordCount(currentRecordCount);
                                agentRecords.get(currentOrder).setRecordTime(currentRecordTime);

                                Log.d("agentRecords", "Exercise is finished.");
                                for (int i = 0; i < numAgents; i++) {
                                    Log.d("agentRecords", "agentRecords[" + i + "]: " + agentRecords.get(i).getRecordCount() + " / " + agentRecords.get(i).getRecordTime());
                                }


                                //Go to FinishExerciseActivity
                                Intent intent_finishexercise = new Intent(getApplicationContext(), FinishExerciseActivity.class);
                                intent_finishexercise.putExtra("agentRecords", agentRecords);
                                if (myRoutine == null) {
                                    intent_finishexercise.putExtra("routineName", "No routine");

                                } else {
                                    intent_finishexercise.putExtra("routineName", myRoutine.getRoutineName());
                                }
                                startActivity(intent_finishexercise);
                                DoingExerciseActivity.this.finish();

                                dialog.dismiss();
                            }
                        }

                )
                .

                        setNegativeButton("Do more!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (!isTimerRunning) {
                                    isTimerRunning = true;
                                }
                                dialog.dismiss();
                            }
                        })
                .create();

        myFinishDialogBox.setCanceledOnTouchOutside(false);

        return myFinishDialogBox;
    }

    public int pushUpMonitor(float value){
        values[connectedAgentType] =  value ;
        if(value > pushUpThreshold){
            exercisePhase.setText("Falling");
            isRisingPeak = true;
            if (isFallingPeak) {
                vib.vibrate(100);
            }
            isFallingPeak = false;
            return 1;
        }else if(value < pushUpThreshold) {
            isFallingPeak = true;
            exercisePhase.setText("Rising");
            if (isRisingPeak) {
                currentRecordCount++;
                setCommandToHERE_agent((byte) 'a');
                vib.vibrate(100);
            }
            isRisingPeak = false;
            return -1;
        }else{

            exercisePhase.setText("Static");
            return 0;
        }
    }

    public int dumbbellMonitor(float value) {
        values[connectedAgentType] = LPF(value, connectedAgentType) - longTermAvg(value, connectedAgentType);
        if (values[connectedAgentType] > 20) {
            exercisePhase.setText(currentRecordCount / 2 + ":  Rising");
            dumbel.setDetectingMode(1);
        } else if (values[connectedAgentType] < -20) {
            exercisePhase.setText(currentRecordCount / 2 + ":  Falling");
            dumbel.setDetectingMode(-1);
        } else {
            exercisePhase.setText(currentRecordCount / 2 + ":  Static");
            dumbel.setDetectingMode(0);
        }
        switch (dumbel.peakDetection(new DataPoint(0, values[connectedAgentType]))) {
            case 1:
                isRisingPeak = true;
                if (isFallingPeak) {
                    vib.vibrate(100);
                }
                isFallingPeak = false;
                return 1;
            case -1:
                isFallingPeak = true;
                if (isRisingPeak) {
                    currentRecordCount++;
                    setCommandToHERE_agent((byte) 'a');
                    vib.vibrate(100);
                }
                isRisingPeak = false;
                return -1;
            default:
                return 0;
        }
    }

    public int hoopMonitor(float value) {
        values[connectedAgentType] = LPF(value, connectedAgentType) - longTermAvg(value, connectedAgentType);
        if (hoop.periodMonitor(new DataPoint((double) (System.currentTimeMillis() - startTime), value))) {
            exercisePhase.setText("Hooping");
            currentRecordCount = hoop.getCount();
            return  1;
        } else {
            exercisePhase.setText("is not Periodic");
            return -1;
        }
    }

    private void appendData(String raw) {
        float value;
        int what = -1;
        if (raw != null) {
            String[] data = raw.split(" ");
            try {
                for (int i = 0; i < data.length; i++) {
                    switch (connectedAgentType){
                        case MyHereAgent.TYPE_DUMBEL:
                            if (data[i].charAt(0) == startBit) {
                                i++;
                                value = (float) Integer.parseInt(data[++i]) - offsets[connectedAgentType];
                                dumbbellMonitor(value);
                                samplingCount++;
                            }

                            break;
                        case MyHereAgent.TYPE_PUSH_UP:
                            if (data[i].charAt(0) == startBit) {
                                value = (float) Integer.parseInt(data[++i]);
                                pushUpMonitor(value);
                                samplingCount++;
                            }
                            break;
                        case MyHereAgent.TYPE_HOOLA_HOOP:
                            if (data[i].charAt(0) == startBit) {
                                i++;
                                value = (float) Integer.parseInt(data[++i]) - offsets[connectedAgentType];
                                hoopMonitor(value);
                                samplingCount++;
                            }
                            break;
                        default:
                            break;

                    }
                }
            }catch(NumberFormatException e){
                Log.e(TAG, e.getMessage());
            };
            invalidator.run();
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
        characteristicTX.setValue(val);
        boolean status =  mBluetoothLeService.writeCharacteristic(characteristicTX);
        return status;
    }


    @Override
    public void onBackPressed() {
        AlertDialog quitExercisingAlert = askQuitExercising();
        quitExercisingAlert.show();
        //super.onBackPressed();
    }


    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
        if (mBluetoothLeService != null) {
            final boolean result = mBluetoothLeService.connect(mDeviceAddress);
            Log.d(TAG, "Connect request result=" + result);

            if(mConnected) {
                mBluetoothLeService.writeCharacteristic(characteristicTX);
                mBluetoothLeService.setCharacteristicNotification(characteristicRX,true);
            }
        }
        //startTime = System.currentTimeMillis();
    }


    @Override
    protected void onPause() {
        super.onPause();
        try {
            unbindService(mServiceConnection);
            mBluetoothLeService = null;
            mHandler.removeCallbacks(invalidator);
        }catch(Exception e){};
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            unbindService(mServiceConnection);
            mBluetoothLeService = null;
            mHandler.removeCallbacks(invalidator);
        }catch(Exception e){};
    }


    /**
     * @return              Parsed/Refined String
     */
    public String makeGoalString(String rawGoal) {


        String goalSentence = "";

        int goalType = 0;   //1: count times, 2: count secs, 3: count times & secs

        String set = "";    //sets
        String count = "";  //times
        String time = "";   //secs

        StringTokenizer tokens = new StringTokenizer(rawGoal, "|");
        set = tokens.nextToken();
        count = tokens.nextToken();
        time = tokens.nextToken();

        Log.d("TokenizerExercise", "set[" + set + "], count[" + count + "], time[" + "]");

        //Single or multiple
        int intSet = Integer.parseInt(set);
        int intCount = Integer.parseInt(count);
        int intTime = Integer.parseInt(time);

        set += " SET";
        count += " TIME";
        time += " SEC";

        //Check goal type
        if (intCount == -1) {
            goalType = 2;
        }
        if (intTime == -1) {
            goalType = 1;
        }
        if (intTime != -1 && intCount != -1) {
            goalType = 3;
        }

        if (intSet > 1) {
            set += "S";
        }
        if (intCount > 1) {
            count += "S";
        }
        if (intTime > 1) {
            time += "S";
        }

        switch (goalType) {
            //Count times (회수)
            case 1:
                goalSentence = count + " X " + set;
                break;
            //Count secs (시간)
            case 2:
                goalSentence = time + " X " + set;
                break;
            // Count both times & secs
            case 3:
                goalSentence = count + " X " + set + " (" + time + ")";
                break;
        }

        return goalSentence;
    }

}
