package uw.cse.dineon.user.checkin;

import uw.cse.dineon.user.R;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

/**
 * TODO
 * @author mhotan
 */
public class CheckInActivity extends FragmentActivity 
implements CheckInFragment.CheckInListener{

	private final static String TAG = CheckInActivity.class.getSimpleName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checkin);
	}

	@Override
	public void onCheckInSuccess() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCheckInFail() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void finish(){
		// Send the results back with an intent
	}
	
	@Override
	public void onBackPressed() {
		Log.d(TAG, "Check In Activity backed press");
		super.onBackPressed();
	}
}
