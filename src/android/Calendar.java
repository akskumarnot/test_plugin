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
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

//plugin result back to javascript
import org.apache.cordova.PluginResult;


public class Calendar extends CordovaPlugin {
    
     //api strings
     public static final String BLUE_ENABLE="enable";
     public static final String BLUE_IS_ENABLED="isEnabled";
     public static final String BLUE_LIST_PAIRED="pairedList";
	
     BluetoothAdapter BA= BluetoothAdapter.getDefaultAdapter();	

	public Calendar(){
		super();
	}

	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
 	try {

	if(BLUE_ENABLE.equals(action)){
	 if (!BA.isEnabled()) {
         	BA.enable();
     	}
	callbackContext.success();
	return true;	
	}

	if(BLUE_LIST_PAIRED.equals(action)){
	Set<BluetoothDevice> pairedDevices = BA.getBondedDevices();
	// If there are paired devices

	JSONArray	devArr	=	null;
	
	JSONObject sample	=	null;
	sample.put("name","sample");
	sample.put("addr","sample");
	devArr.put((Object)sample);

	if (pairedDevices.size() > 0) {
   	 // Loop through paired devices
    		for (BluetoothDevice device : pairedDevices) {
        	// Add the name and address to an array adapter to show in a ListView
        	String devName	=	device.getName();
		String devAddr	=	device.getAddress();	
		JSONObject obj	=	null;
		Log.w("name", devName);
		obj.put("name",devName);
		obj.put("addr",devAddr);
		devArr.put((Object)obj);
  	    }
	 	JSONObject data	=	null;	
		data.put("arr",(Object)devArr);	
	}
	System.ot.println("Sent something\n");
	callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, new JSONArray("test")));	
		return true;
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
