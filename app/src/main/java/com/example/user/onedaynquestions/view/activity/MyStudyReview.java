package com.example.user.onedaynquestions.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.example.user.onedaynquestions.R;
import com.example.user.onedaynquestions.controller.CardAdapter;
import com.example.user.onedaynquestions.model.MyCard;
import com.example.user.onedaynquestions.service.FloatingButtonService;

import java.util.ArrayList;

/**
 * Created by ymbaek on 2016-04-18.
 */
public class MyStudyReview extends AppCompatActivity {

    public static final int WRONGANSWER = 0;
    public static final int DAILY = 1;

    public static final String TAG = "MyStudyReview";
    public static final String TAG_DB = "MyEquipmentsDBTag";
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<MyCard> wrongAnswerList, dailyRecordList;
    private float dist;
    private int prevX, prevY;
    private final int distThreshold = 100;

    private CardAdapter wrongAnswerListAdapter,dailyRecordListAdapter;
    private RecyclerView wrongAnswerListView, dailyRecordListView;

//    public List<MyHereAgent> myHereAgents;
//    private HERE_DeviceListAdapter equipListAdapter;
//    private BluetoothAdapter mBluetoothAdapter;
//    private ListViewAdapter adapter;
//    private ArrayList<BluetoothDevice> mLEdeviceList;
//    private boolean mScanning;
//    private Handler mHandler;


//    private MyHereAgent selectedNewAgent;
//
//    private ListView lvEquipList;
//
//    private ArrayList<MyHereAgent> pairedEquipList;
//
//    private static final int REQUEST_ENABLE_BT = 1;
//    // Stops scanning after 10 seconds.
//    private static final long SCAN_PERIOD = 10000;

    //Toolbar
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mystudyreview);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("My Study Review");
        toolbar.setNavigationIcon(R.mipmap.ic_menu_arrow_back);
        toolbar.setTitleTextColor(Color.WHITE);

        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();

        initQuestionList();
        appendQuestion(DAILY, new MyCard());
        appendQuestion(WRONGANSWER, new MyCard());

        initCards();

//        myHereAgents = new ArrayList<MyHereAgent>();
//        selectedNewAgent = new MyHereAgent();
//
//        actionBar.setTitle("My Study Review");
//        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
//
//        ListView listView = (ListView) findViewById(R.id.setting_myeq_list_registered);
//        adapter = new ListViewAdapter();
//        listView.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
//        mHandler = new Handler();
//
//        // Use this check to determine whether BLE is supported on the device.  Then you can
//        // selectively disable BLE-related features.
//        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
//            Toast.makeText(this, "You need BLE support device", Toast.LENGTH_SHORT).show();
//            finish();
//        }
//        // Initializes a Bluetooth adapter.  For API level 18 and above, get a reference to
//        // BluetoothAdapter through BluetoothManager.
//        final BluetoothManager bluetoothManager =
//                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
//        mBluetoothAdapter = bluetoothManager.getAdapter();
//
//        // Checks if Bluetooth is supported on the device.
//        if (mBluetoothAdapter == null) {
//            Toast.makeText(this, "You need BLUETOOTH support device", Toast.LENGTH_SHORT).show();
//            finish();
//        }
//
//        equipListAdapter = new HERE_DeviceListAdapter();
//        lvEquipList = (ListView) findViewById(R.id.setting_myeq_list_bluetooth);
//        lvEquipList.setAdapter(equipListAdapter);
//
//        scanLeDevice(true);
//
//        lvEquipList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////                Toast.makeText(getApplicationContext(), "lvEquipList.setOnItemClickListener", Toast.LENGTH_SHORT).show();
//
//                String majorId = parseMajorId(mLEdeviceList.get(position).getName());
//                String minorId = parseMinorId(mLEdeviceList.get(position).getName());
//                int eqType = getTypeByMinorId(minorId);
//
//                selectedNewAgent.setMyeqMacId(mLEdeviceList.get(position).getAddress());
//                selectedNewAgent.setMyeqType(eqType);
//                selectedNewAgent.setMyeqBeaconMajorId(majorId);
//                selectedNewAgent.setMyeqBeaconMinorId(minorId);
//
//                Log.d("parseIDs", "mLEdeviceList.get(position).getName(): " + mLEdeviceList.get(position).getName());
//
//                Log.d("parseIDs", "Major ID: " + majorId);
//                Log.d("parseIDs", "Major ID: " + minorId);
//                Log.d("parseIDs", "Agent type: " + eqType);
//
//                showAddAgentDialog();
//
//                scanLeDevice(false);
//            }
//        });

    }


    public void initCards() {
        if (MainActivity.odnqDB != null) {

            if (MainActivity.odnqDB.getMyInfo() == null) {
                Log.d("AppendCardList", "[MyStudyReview] No user information");

            } else {
                String myInfoId = MainActivity.odnqDB.getMyInfo().getMyInfoId();
                Log.d("AppendCardList", "[MyStudyReview] MyCard table size: " + MainActivity.odnqDB.countTableMyCard());

                clearAllCardList();

                ArrayList<MyCard> allCardList;
                ArrayList<MyCard> wrongCardList;

                allCardList = MainActivity.odnqDB.getMyCards(1, myInfoId);
                if (allCardList != null){
                    Log.d("AppendCardList", "[MyStudyReview] allCardList - size: " + allCardList.size());

                    for (int i = 0; i < allCardList.size(); i++) {
                        appendQuestion(DAILY, allCardList.get(i));
                    }
                }


            }
        }
    }
    private void clearAllCardList() {
        dailyRecordList.clear();
        wrongAnswerList.clear();
    }


    public void mOnClick(View v) {
        switch (v.getId()) {
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

//    public class ListViewAdapter extends BaseAdapter{
//
//
//
//        public ListViewAdapter() {
//            super();
//            if(MainActivity.hereDB.getAllMyHereAgents() !=null)
//                myHereAgents = MainActivity.hereDB.getAllMyHereAgents();
//
//            TextView textView = (TextView) findViewById(R.id.setting_myeq_tv_registered);
//            if (myHereAgents.size() != 0) {
//                textView.setVisibility(View.GONE);
//            }
//        }
//
//        @Override
//        public int getCount() {
//            return myHereAgents.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return myHereAgents.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            final int pos = position;
//            final Context context = parent.getContext();
//
//            if (convertView == null){
//                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                convertView = inflater.inflate(R.layout.listitem_equipment_simple,parent,false);
//            }
//            ImageView eqTypeImage = (ImageView) convertView.findViewById(R.id.equiplist_img);
//            TextView eqName = (TextView) convertView.findViewById(R.id.equiplist_name);
//            TextView eqId = (TextView) convertView.findViewById(R.id.equiplist_id);
//            TextView eqSensorType = (TextView) convertView.findViewById(R.id.equiplist_sensorid);
//
//            switch (myHereAgents.get(pos).getMyeqType()) {
//                case 1:
//                    eqTypeImage.setImageResource(R.drawable.eq_01_dumbbell);
//                    break;
//                case 2:
//                    eqTypeImage.setImageResource(R.drawable.eq_02_pushupbar);
//                    break;
//                case 3:
//                    eqTypeImage.setImageResource(R.drawable.eq_03_jumprope);
//                    break;
//                case 4:
//                    eqTypeImage.setImageResource(R.drawable.eq_04_hoolahoop);
//                    break;
//                case 5:
//                    eqTypeImage.setImageResource(R.drawable.icon_bluetooth_device);
//                default:
//                    break;
//            }
//
//            eqName.setText(myHereAgents.get(pos).getMyeqName());
//            eqId.setText(myHereAgents.get(pos).getMyeqMacId());
//            eqSensorType.setText(myHereAgents.get(pos).getMyeqBeaconMajorId() + "-" + myHereAgents.get(pos).getMyeqBeaconMinorId());
//            //eqSensorType.setText(registeredAgents.get(pos).getMyeqType());
//
//            return convertView;
//        }
//    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
//        scanLeDevice(false);
    }

    @Override
    public void onResume() {
        stopService(new Intent(this, FloatingButtonService.class));
        super.onResume();
//        if(registeredAgents!=null)
//            registeredAgents.clear();
//        registeredAgents = MainActivity.hereDB.getAllMyHereAgents();
//
//        Log.d(TAG, "onResume");
//        // Ensures Bluetooth is enabled on the device.  If Bluetooth is not currently enabled,
//        // fire an intent to display a dialog asking the user to grant permission to enable it.
//        if (!mBluetoothAdapter.isEnabled()) {
//            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
//        }
//
//        scanLeDevice(true);
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG, "onStop");
//        scanLeDevice(false);
//        equipListAdapter.clear();
    }

    @Override
    public void onRestart(){
        super.onRestart();
    }

//    private void scanLeDevice(final boolean enable) {
//        if (enable) {
//            // Stops scanning after a pre-defined scan period.
//            mHandler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    mScanning = false;
//                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
//                }
//            }, SCAN_PERIOD);
//
//            mScanning = true;
//            mBluetoothAdapter.startLeScan(mLeScanCallback);
//        } else {
//            mScanning = false;
//            mBluetoothAdapter.stopLeScan(mLeScanCallback);
//        }
//    }

//    // Adapter for holding devices found through scanning.
//    private class HERE_DeviceListAdapter extends BaseAdapter {
//
//        public HERE_DeviceListAdapter() {
//            super();
//            pairedEquipList = new ArrayList<MyHereAgent>();
//            mLEdeviceList = new ArrayList<BluetoothDevice>();
//
//        }
//
//        public void addDevice(BluetoothDevice device) {
//            if(!mLEdeviceList.contains(device)) {
//                mLEdeviceList.add(device);
//                if(!pairedEquipList.contains(device.getAddress())){
//                    String deviceAddress = device.getAddress();
//                    String deviceName = device.getName();
//                    String deviceMajorId = parseMajorId(device.getName());
//                    String deviceMinorId = parseMinorId(device.getName());
//                    int deviceType = getTypeByMinorId(deviceMinorId);
//
//                    pairedEquipList.add(new MyHereAgent(deviceAddress, deviceName, deviceType, deviceMajorId, deviceMinorId));
//                }
//            }
//        }
//
//        public BluetoothDevice getDevice(int position) {
//            return mLEdeviceList.get(position);
//        }
//
//        public void clear() {
//            mLEdeviceList.clear();
//        }
//
//        @Override
//        public int getCount() {
//            return mLEdeviceList.size();
//        }
//
//        @Override
//        public Object getItem(int i) {
//            return mLEdeviceList.get(i);
//        }
//
//        @Override
//        public long getItemId(int i) {
//            return i;
//        }
//
//        @Override
//        public View getView(int i, View view, ViewGroup viewGroup) {
//
//            final Context context = viewGroup.getContext();
//
//            if (view == null){
//                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                view = inflater.inflate(R.layout.listitem_equipment_simple,viewGroup,false);
//            }
//
//            ImageView eqTypeImage = (ImageView)view.findViewById(R.id.equiplist_img);
//            TextView eqName = (TextView)view.findViewById(R.id.equiplist_name);
//            TextView eqId = (TextView)view.findViewById(R.id.equiplist_id);
//            TextView eqSensorId = (TextView)view.findViewById(R.id.equiplist_sensorid);
//
//            switch (pairedEquipList.get(i).getMyeqType()) {
//                case MyHereAgent.TYPE_DUMBEL:
//                    eqTypeImage.setImageResource(R.drawable.eq_01_dumbbell);
//                    break;
//                case MyHereAgent.TYPE_PUSH_UP:
//                    eqTypeImage.setImageResource(R.drawable.eq_02_pushupbar);
//                    break;
//                case MyHereAgent.TYPE_JUMP_ROPE:
//                    eqTypeImage.setImageResource(R.drawable.eq_03_jumprope);
//                    break;
//                case MyHereAgent.TYPE_HOOLA_HOOP:
//                    eqTypeImage.setImageResource(R.drawable.eq_04_hoolahoop);
//                    break;
//                default:
//                    eqTypeImage.setImageResource(R.drawable.icon_bluetooth_device);
//                    break;
//            }
//
//            eqName.setText(pairedEquipList.get(i).getMyeqName());
//            eqId.setText(pairedEquipList.get(i).getMyeqMacId());
//            eqSensorId.setText(pairedEquipList.get(i).getMyeqBeaconMajorId() + "-" + pairedEquipList.get(i).getMyeqBeaconMinorId());
//            //eqSensorId.setText(pairedEquipList.get(i).getEquipmentSensorID());
//
//            return view;
//        }
//    }

//    // Device scan callback.
//    private BluetoothAdapter.LeScanCallback mLeScanCallback =
//            new BluetoothAdapter.LeScanCallback() {
//
//                @Override
//                public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            equipListAdapter.addDevice(device);
//                            equipListAdapter.notifyDataSetChanged();
//                        }
//                    });
//                }
//            };
//
//    private void showAddAgentDialog() {
//        LayoutInflater inflater = getLayoutInflater();
//
//        final View dialogView = inflater.inflate(R.layout.dialog_agent, null);
//
//        EditText et_addagent_macid = (EditText) dialogView.findViewById(R.id.dialog_agent_macid);
//        final EditText et_addagent_name = (EditText) dialogView.findViewById(R.id.dialog_agent_name);
//        EditText et_addagent_majorid = (EditText) dialogView.findViewById(R.id.dialog_agent_majorid);
//        EditText et_addagent_minorid = (EditText) dialogView.findViewById(R.id.dialog_agent_minorid);
//
//        et_addagent_macid.setText(selectedNewAgent.getMyeqMacId());
//        et_addagent_majorid.setText(selectedNewAgent.getMyeqBeaconMajorId());
//        et_addagent_minorid.setText(selectedNewAgent.getMyeqBeaconMinorId());
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(MyStudyReview.this);
//        builder.setTitle("Add a new HERE agent");
//        builder.setView(dialogView);
//        builder.setPositiveButton("Add agent", new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//                if (selectedNewAgent.getMyeqBeaconMajorId() == "") {
//                    Toast.makeText(getApplicationContext(), "This device is not compatible for HERE", Toast.LENGTH_SHORT).show();
//                } else {
//                    selectedNewAgent.setMyeqName(et_addagent_name.getText().toString());
//                    MainActivity.hereDB.insertHereAgent(selectedNewAgent);
//                    Toast.makeText(getApplicationContext(), "An agent is added into DB", Toast.LENGTH_SHORT).show();
//
//                    myHereAgents.clear();
//                    if (MainActivity.hereDB.getAllMyHereAgents() != null)
//                        myHereAgents = MainActivity.hereDB.getAllMyHereAgents();
//                    adapter.notifyDataSetChanged();
//
//                    TextView textView = (TextView) findViewById(R.id.setting_myeq_tv_registered);
//                    if (myHereAgents.size() != 0) {
//                        textView.setVisibility(View.GONE);
//                    }
//                }
//
//
//
//                dialog.dismiss();
//            }
//        });
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//
//        AlertDialog dialog = builder.create();
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.show();
//    }

    private String parseMajorId(String deviceName) {
        if (deviceName != null) {
            if (deviceName.contains("-")) {
                int separatorLoc = deviceName.indexOf("-");
                return deviceName.substring(0, separatorLoc);
            } else {
                return "";
            }

        } else {
            return "";
        }
    }

    private String parseMinorId(String deviceName) {
        if (deviceName != null) {
            if (deviceName.contains("-")) {
                int separatorLoc = deviceName.indexOf("-");
                return deviceName.substring(separatorLoc + 1, deviceName.length());
            } else {
                return "";
            }

        } else {
            return "";
        }
    }

    private int getTypeByMinorId(String minorId) {
        if (minorId != null) {
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

        } else {
            return 0;
        }
    }

    public void initQuestionList(){



        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);


        dailyRecordListView = (RecyclerView) findViewById(R.id.daily_study_lv);
        dailyRecordListView.setLayoutManager(mLayoutManager);
        dailyRecordListView.setHasFixedSize(true);
        dailyRecordList = new ArrayList<>();
        dailyRecordListAdapter = new CardAdapter(dailyRecordList);
        dailyRecordListView.setAdapter(dailyRecordListAdapter);
        dailyRecordListView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                switch (e.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        dist = 0;
                        prevX = (int)e.getX();
                        prevY = (int)e.getY();

                        break;
                    case MotionEvent.ACTION_MOVE:
                        int tempX = prevX - (int)e.getX();
                        int tempY = prevY - (int)e.getY();
                        dist += Math.sqrt(tempX * tempX + tempY * tempY);
                        prevX = (int)e.getX();
                        prevY = (int)e.getY();

                        break;
                    case MotionEvent.ACTION_UP:
                        if(dist < distThreshold) {
                            int id = dailyRecordListView.getChildAdapterPosition(dailyRecordListView.findChildViewUnder(e.getX(), e.getY()));
                            startActivity(new Intent(dailyRecordList.get(id).getCardSolvingIntent(MyStudyReview.this)));
                        }

                        break;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });




        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        wrongAnswerListView = (RecyclerView) findViewById(R.id.frequently_wrong_question_onReview_lv);
        wrongAnswerListView.setLayoutManager(mLayoutManager);
        wrongAnswerListView.setHasFixedSize(true);
        wrongAnswerList = new ArrayList<>();
        wrongAnswerListAdapter = new CardAdapter(wrongAnswerList);
        wrongAnswerListView.setAdapter(wrongAnswerListAdapter);
        wrongAnswerListView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                switch (e.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        dist = 0;
                        prevX = (int)e.getX();
                        prevY = (int)e.getY();

                        break;
                    case MotionEvent.ACTION_MOVE:
                        int tempX = prevX - (int)e.getX();
                        int tempY = prevY - (int)e.getY();
                        dist += Math.sqrt(tempX * tempX + tempY * tempY);
                        prevX = (int)e.getX();
                        prevY = (int)e.getY();

                        break;
                    case MotionEvent.ACTION_UP:
                        if(dist < distThreshold) {
                            int id = wrongAnswerListView.getChildAdapterPosition(wrongAnswerListView.findChildViewUnder(e.getX(), e.getY()));
                            startActivity(new Intent(wrongAnswerList.get(id).getCardSolvingIntent(MyStudyReview.this)));
                        }

                        break;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

    }


    public boolean setQuestion(int where, int position, MyCard question){
        switch (where){
            case DAILY:
                dailyRecordList.add(position, question);
                break;
            case WRONGANSWER:
                wrongAnswerList.add(position, question);
                break;
            default:
                return false;
        }
        return true;
    };

    public boolean appendQuestion(int where, MyCard question){
        switch (where){
            case DAILY:
                dailyRecordList.add(question);
                break;
            case WRONGANSWER:
                wrongAnswerList.add(question);
                break;
            default:
                return false;
        }

        return true;
    };

    public boolean deleteQuestion(int where, int position){
        switch (where){
            case DAILY:
                dailyRecordList.remove(position);
                dailyRecordListAdapter.notifyDataSetChanged();
                break;
            case WRONGANSWER:
                wrongAnswerList.remove(position);
                wrongAnswerListAdapter.notifyDataSetChanged();
                break;
            default:
                return false;
        }
        return true;
    };


    public boolean setQuestions(int where, ArrayList<MyCard> list){
        switch (where){
            case DAILY:
                dailyRecordList.clear();
                dailyRecordList.addAll(list);
                dailyRecordListAdapter.notifyDataSetChanged();
                break;
            case WRONGANSWER:
                wrongAnswerList.clear();
                wrongAnswerList.addAll(list);
                wrongAnswerListAdapter.notifyDataSetChanged();
                break;
            default:
                return false;
        }
        return true;
    };
}
