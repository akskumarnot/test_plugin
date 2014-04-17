package org.aksnot.calendar;
 

import android.util.Log;
import java.util.Set;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import android.app.Activity;
import android.content.Intent;
import android.content.BroadcastReceiver;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

//plugin result back to javascript
import org.apache.cordova.PluginResult;

import android.content.Context;
import android.content.IntentFilter;
import java.util.ArrayList;


public class Calendar extends CordovaPlugin {
    
     //api strings
     public static final String BLUE_ENABLE="enable";
     public static final String BLUE_DISCOVER="discover";
     public static final String BLUE_IS_ENABLED="isEnabled";
     public static final String BLUE_LIST_PAIRED="pairedList";
	
     BluetoothAdapter BA= BluetoothAdapter.getDefaultAdapter();	

	public Calendar(){
		super();
	}

	@Override
	public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
	
 	try {

	if(BLUE_ENABLE.equals(action)){
	 if (!BA.isEnabled()) {
         	BA.enable();
     	}
	JSONObject obj	=	new JSONObject();
	obj.put("haha","man");
	callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, obj));
	return true;	
	}

	if(BLUE_LIST_PAIRED.equals(action)){
	Set<BluetoothDevice> pairedDevices = BA.getBondedDevices();
	// If there are paired devices
	JSONArray	devArr	=	new JSONArray();
	JSONObject sample	=	new JSONObject();
	JSONObject data=null;
	sample.put("name","sample");
	sample.put("addr","sample");
	devArr.put((Object)sample);

	if (pairedDevices.size() > 0) {
   	 // Loop through paired devices
    		for (BluetoothDevice device : pairedDevices) {
        	// Add the name and address to an array adapter to show in a ListView
        	String devName	=	device.getName();
		String devAddr	=	device.getAddress();	
		JSONObject obj	=	new JSONObject();
		Log.w("name", devName);
		obj.put("name",devName);
		obj.put("addr",devAddr);
		devArr.put((Object)obj);
  	    	}
		}
		data	=	new JSONObject();	
		data.put("arr",(Object)devArr);	
		callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, data));	
		return true;
	}

	if(BLUE_DISCOVER.equals(action)){
	
	BroadcastReceiver mReceiver = new BroadcastReceiver() {

	void setFilter(){
	IntentFilter filter = new IntentFilter();	
	     filter.addAction(BluetoothDevice.ACTION_FOUND);
    	     filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
             filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
	     Context context=cordova.getActivity().getApplicationContext();
	     context.registerReceiver(this, filter);
	}
	
	public void  onCreate()
        {
           setFilter();
	   BA.startDiscovery();
        }

	protected void onPause() {
	   Context context=cordova.getActivity().getApplicationContext();
	   context.unregisterReceiver(this);
	}
	 
	protected void onResume() {	
	     setFilter();
	}		

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
	    ArrayList<BluetoothDevice> list	= new ArrayList<BluetoothDevice>();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                list.add(device);
                //  discovery is finished
            }
            else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action))
                if(list.size() == 0)
                {
                    //send back no data
			JSONObject data	=	null;
			data	=	new JSONObject();
			data.put("err","no device discovered");
			callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, data));	
                }
                else
                {
                   //send back the list of devices
			JSONArray	arr	=	new JSONArray();
			for(BluetoothDevice device : list){
				//make a JSON object for each
				JSONObject obj	=	new JSONObject();
				String devName	=	device.getName();
				String devAddr	=	device.getAddress();
				obj.put("name",devName);
				obj.put("addr",devAddr);
				arr.put((Object)obj);	
			}
			//made the json aray ) pack it in a json obj ) send it off
			JSONObject data	=	null;
			data	=	new JSONObject();	
			data.put("arr",(Object)arr);	
			callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, data));		
                }

            }
        };
    }
	}

	catch(Exception e) {
    	System.err.println("Exception: " + e.getMessage());
    	callbackContext.error(e.getMessage());
	    return false;
	}
	
	return true; 
}

}		
