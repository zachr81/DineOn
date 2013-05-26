package uw.cse.dineon.user.test;

import uw.cse.dineon.library.DineOnUser;
import uw.cse.dineon.user.DineOnUserApplication;
import uw.cse.dineon.user.general.ProfileActivity;
import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.parse.ParseUser;

public class UserProfileActivityTest extends
	ActivityInstrumentationTestCase2<ProfileActivity> {

	private ParseUser testUser;
	private DineOnUser dineOnUser;
	private Activity mActivity;
	private Instrumentation mInster;
	private final String TAG = this.getClass().getSimpleName();
	
	public UserProfileActivityTest(){
		super(ProfileActivity.class);
	}
	
	@Override
	public void setUp(){
		try{
			super.setUp();
			
			testUser = new ParseUser();
			testUser.setUsername("testUser");
			testUser.setPassword("12345");
			testUser.setEmail("testUser@testUser.com");
			
			dineOnUser = new DineOnUser(testUser);
			DineOnUserApplication.setDineOnUser(dineOnUser);
			setActivityInitialTouchMode(false);
		    Intent addEvent = new Intent();
		    setActivityIntent(addEvent);
			mActivity = getActivity();
			mInster = this.getInstrumentation();
			
		}catch(Exception e){
			Log.d(TAG, "Setup failed\n" + e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void tearDown(){
		try {
			super.tearDown();
		} catch (Exception e) {
			Log.d(TAG, "Teardown failed\n" + e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	public void testProfileDisplayViews(){
		
	}
}
