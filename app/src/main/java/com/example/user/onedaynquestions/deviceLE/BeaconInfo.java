package com.example.user.onedaynquestions.deviceLE;
import org.altbeacon.beacon.Beacon;

public class BeaconInfo {
    public int height;
    public int x, y;
    public String mac;
    public String identifier;
    public String ID2;
    public String ID3;
    public int distance;
    public int rssi;
    public int macHash;

    public boolean isSame(Beacon beacon){
		/*
		if(major == beacon.getId1().toInt() && minor == beacon.getId2().toInt())
			return true;
		else return false;	
		*/
        if(beacon.getBluetoothAddress().hashCode() == macHash) return true;
        else return false;
		
		/*
		if(beacon.getBluetoothAddress().compareTo(mac) == 0) return true;
		else return false;*/
    }
}
