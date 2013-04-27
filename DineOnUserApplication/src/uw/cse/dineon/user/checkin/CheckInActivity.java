package uw.cse.dineon.user.checkin;

import uw.cse.dineon.user.DineOnUserActivity;
import uw.cse.dineon.user.R;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * TODO
 * @author mhotan
 */
public class CheckInActivity extends DineOnUserActivity 
implements CheckInFragment.CheckInListener{
	
	public static final String EXTRA_CHECKEDIN = "is checked in?";
	
	private boolean mCheckedIn;

	private final static String TAG = CheckInActivity.class.getSimpleName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checkin);
		mCheckedIn = false;
	}

	@Override
	public void onCheckInSuccess() {
		// TODO Auto-generated method stub
		mCheckedIn = true;
		finish();
	}

	@Override
	public void onCheckInFail() {
		// TODO Auto-generated method stub
		mCheckedIn = false;
	}
	
	@Override
	public void finish(){
		Intent retIntent = new Intent();
		retIntent.putExtra(EXTRA_CHECKEDIN, mCheckedIn);
		super.finish();
		// Send the results back with an intent
	}
	
	@Override
	public void onBackPressed() {
		Log.d(TAG, "Check In Activity back pressed");
		super.onBackPressed();
	}
}
