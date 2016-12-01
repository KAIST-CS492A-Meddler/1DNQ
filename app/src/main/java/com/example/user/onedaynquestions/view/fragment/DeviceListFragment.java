package com.example.user.onedaynquestions.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.user.onedaynquestions.R;
import com.example.user.onedaynquestions.controller.EquipmentListAdapter;
import com.example.user.onedaynquestions.model.Equipment;

import java.util.ArrayList;

/**
 * Created by ymbae on 2016-04-18.
 */
public class DeviceListFragment extends Fragment{

    private ListView lvEquipList;

    private ArrayList<Equipment> pairedEquipList;
    private EquipmentListAdapter equipListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        pairedEquipList = new ArrayList<>();
        equipListAdapter = new EquipmentListAdapter(getActivity().getApplicationContext(), pairedEquipList);

        View view = inflater.inflate(R.layout.fragment_devicelist, container, false);
        lvEquipList = (ListView) view.findViewById(R.id.fragment1_equiplist);

        lvEquipList.setAdapter(equipListAdapter);

        Equipment eq1 = new Equipment("EQ01", "DUMBBELL", "Sensor-Q03-87A", "2016-04-18", 2);
        Equipment eq2 = new Equipment("EQ02", "HOOLA-HOOP", "Accelerometer-X-3", "2016-04-18", 1);
        Equipment eq3 = new Equipment("EQ03", "PLANK", "FORCE-ss-2033", "2016-04-15", 1);
        Equipment eq4 = new Equipment("EQ04", "JUMP-ROPE", "ZEROZERO", "2016-04-16", 0);

        pairedEquipList.add(eq1);
        pairedEquipList.add(eq2);
        pairedEquipList.add(eq3);
        pairedEquipList.add(eq4);

        equipListAdapter.notifyDataSetChanged();

        return view;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }
}
