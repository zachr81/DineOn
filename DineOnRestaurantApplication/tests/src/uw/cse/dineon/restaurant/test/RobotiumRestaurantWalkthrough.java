package uw.cse.dineon.restaurant.test;

import uw.cse.dineon.restaurant.R;
import uw.cse.dineon.restaurant.active.RestauarantMainActivity;
import uw.cse.dineon.restaurant.login.RestaurantLoginActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.EditText;

import com.jayway.android.robotium.solo.Solo;

public class RobotiumRestaurantWalkthrough extends
		ActivityInstrumentationTestCase2<RestaurantLoginActivity> {
	
	public static final String LOGIN_NAME = "test34";
	public static final String PASSWORD = "test34";

	private Solo solo;

	public RobotiumRestaurantWalkthrough() {
		super(RestaurantLoginActivity.class);
	}

	@Override
	public void setUp() {
		solo = new Solo(getInstrumentation(), getActivity());

	}

	@Override
	public void tearDown() throws Exception {
		solo.finishOpenedActivities();
	} 
	
	public void testLogIn(){
		solo.waitForActivity(RestauarantMainActivity.class, 3000);//wait for auto-login
		if(solo.getCurrentActivity().getClass() != RestauarantMainActivity.class){
			Log.v("ROBO", solo.getCurrentActivity().getClass().getName());
			tryLogIn(); //Try to log in or make account
		}
		
		solo.waitForActivity(RestauarantMainActivity.class, 500);
		//we should not be in the main activity. if not, we have failed.
		solo.assertCurrentActivity("Error, not in Restaurant Main Activity" , RestauarantMainActivity.class);
		
		//Now we're in the restaurant activity. Change what's past here to do different stuff
		
	}

	private void tryLogIn() {
		EditText et = solo.getEditText("Your Restaurant Name");
		assertNotNull(et);
		solo.typeText(et, LOGIN_NAME);
		
		EditText et2 = solo.getEditText("Password");
		assertNotNull(et2);
		solo.typeText(et2, PASSWORD);
		
		solo.clickOnButton("Log in");
		solo.waitForDialogToClose(1000); //wait for login validation
		
		if(solo.searchText("Invalid Credentials")){
			makeAccount();
		}
		
	}

	/**
	 * Private method to make an account if one does not exist under the specified credentials
	 */
	private void makeAccount() {
		solo.clickOnButton("Dismiss");
		solo.waitForDialogToClose(500);
		solo.clickOnActionBarItem(R.id.option_create_new_account);
		//Type in new account info
		EditText et = solo.getEditText("Your Restaurant Name");
		assertNotNull(et);
		solo.typeText(et, LOGIN_NAME);
		
		et = solo.getEditText("Email Address");
		assertNotNull(et);
		solo.typeText(et, "test@example.com");
		
		et = solo.getEditText("Password");
		assertNotNull(et);
		solo.typeText(et, PASSWORD);
		
		et = solo.getEditText("Repeat Password");
		assertNotNull(et);
		solo.typeText(et, PASSWORD);
		
		solo.clickOnButton("Create New Account");
	}

}
