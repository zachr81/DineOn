package uw.cse.dineon.user.checkin;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.util.Log;

public class QRCheckin {
	
	/**
	 * Processes the information gathered from a QR scan and sends the checkin
	 * request to the restaurant expecting a DiningSession as a result
	 * 
	 * @param requestCode QR format information
	 * @param resultCode -1 if successful and failed otherwise
	 * @param intent Information returned from the QR scan activity
	 */
	public static void QRResult(int requestCode, int resultCode, Intent intent) {
		IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		  if (scanResult != null) {
			  // handle scan result
			  String contents = scanResult.getContents();
			  // TODO Handle Correct log in
			  try {
				  JSONObject data = new JSONObject(contents);
				  Log.d("ZXing", data.toString());
				  
				  
			  } catch (JSONException e) {
				  Log.d("ZXing", "Error: " + e.getMessage());
			  }
			  
		  } else {
			  // else continue with any other code you need in the method
			  Log.d("ZXing", "Error getting the result");
		  }
	}
}
