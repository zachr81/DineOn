package uw.cse.dineon.user.bill.test;

import com.parse.Parse;
import com.parse.ParseException;

import uw.cse.dineon.library.util.TestUtility;
import uw.cse.dineon.user.DineOnUserApplication;
import uw.cse.dineon.user.R;
import uw.cse.dineon.user.bill.CurrentOrderActivity;
import uw.cse.dineon.user.bill.CurrentOrderFragment;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.View;
import android.widget.Button;

public class CurrentOrderActivityTest extends
		ActivityInstrumentationTestCase2<CurrentOrderActivity> {

	private CurrentOrderActivity mActivity;
	
	int orderNum = 100;
	Integer testsRun = 0;
	final int NUM_TESTS = 3;
	
	public CurrentOrderActivityTest() {
		super(CurrentOrderActivity.class);
	}

	public synchronized int increment() {
		return testsRun++;
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		Parse.initialize(null, "RUWTM02tSuenJPcHGyZ0foyemuL6fjyiIwlMO0Ul", "wvhUoFw5IudTuKIjpfqQoj8dADTT1vJcJHVFKWtK");
		
		if(testsRun == 0) {
			try {
				TestUtility.testSetUp(true);
			} catch (Exception e) {
				try {
					TestUtility.testTearDown();
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
		}
		
		DineOnUserApplication.cachedUser = TestUtility.getDineOnUser();
		mActivity = getActivity();
		Intent addEvent = new Intent(mActivity, CurrentOrderActivity.class);
		mActivity.startActivity(addEvent);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		if(increment() == NUM_TESTS) {
			try {
				TestUtility.testTearDown();
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		}
	}

	@SmallTest
	public void testOnClickRequestButton() {
		setActivityInitialTouchMode(false);
		
		Button b = (Button) mActivity.findViewById(R.id.button_request);
		CurrentOrderFragment cof = (CurrentOrderFragment) mActivity.getSupportFragmentManager()
					.findFragmentById(R.id.fragment_current_order);
		
		cof.onClick(b);
		
		final View alertView = cof.getLayoutInflater(cof.getArguments()).inflate(
				R.layout.alert_build_request, null);
		cof.getDescription(alertView);
		mActivity.finish();
	}
	
	public void testOnPlaceOrder() {
		mActivity.onPlaceOrder("rs");
		mActivity.finish();
	}

	public void testOnRequestMade() {
		mActivity.onRequestMade("MORE WATER!!!!");
		mActivity.finish();
	}
}
