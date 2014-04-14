package org.aksnot.calendar;
 
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
import org.apache.cordova.api.Plugin;
import org.apache.cordova.api.PluginResult;


public class Calendar extends CordovaPlugin {
     public static final String ACTION_ADD_CALENDAR_ENTRY = "addCalendarEntry";

     //api strings
     public static final String BLUE_ENABLE="enable";
     public static final String BLUE_IS_ENABLED="isEnabled";
     public static final String BLUE_LIST_PAIRED="pairedList"
	
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
	 callbackContext.success();
	return true;
     		 }	
	}

	if(BLUE_LIST_PAIRED.equals(action)){
	Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
	// If there are paired devices
	if (pairedDevices.size() > 0) {
   	 // Loop through paired devices
		JSONArray	devArr	=	null;
    		for (BluetoothDevice device : pairedDevices) {
        	// Add the name and address to an array adapter to show in a ListView
        	String devName	=	device.getName();
		String devAddr	=	device.getAddress();	
		JSONObject obj	=	null;
		obj.put("name":devName);
		obj.put("addr":devAddr);
		devArr.push(obj.valueToString());
  	    }
	 	JSONObject data	=	null;	
		data.put("arr",devArr);	
		callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, data));
		//callbackContext.success();
	}	
	return true;
	}
  	  callbackContext.error("Invalid action");
   	 return false;
	} catch(Exception e) {
    	System.err.println("Exception: " + e.getMessage());
    	callbackContext.error(e.getMessage());
	    return false;
	} 
}		
}
