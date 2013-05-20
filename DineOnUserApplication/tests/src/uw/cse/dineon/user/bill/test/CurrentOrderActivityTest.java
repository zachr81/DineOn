package uw.cse.dineon.user.bill.test;

import com.parse.Parse;
import com.parse.ParseUser;

import uw.cse.dineon.library.DineOnUser;
import uw.cse.dineon.library.Order;
import uw.cse.dineon.library.util.TestUtility;
import uw.cse.dineon.user.R;
import uw.cse.dineon.user.bill.CurrentOrderActivity;
import uw.cse.dineon.user.bill.CurrentOrderFragment;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.Button;

public class CurrentOrderActivityTest extends
		ActivityInstrumentationTestCase2<CurrentOrderActivity> {

	private CurrentOrderActivity mActivity;
	private DineOnUser dineOnUser;

	int orderNum = 100;

	public CurrentOrderActivityTest() {
		super(CurrentOrderActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	@SmallTest
	public void testOnClickRequestButton() {
//		Button b = (Button) mActivity.findViewById(R.id.button_request);
//		CurrentOrderFragment cof = (CurrentOrderFragment) mActivity.getSupportFragmentManager()
//					.findFragmentById(R.id.fragment_current_order);
//
//		cof.onClick(b);
//		getInstrumentation().waitForIdleSync();
//
//		this.sendKeys("T E S T SPACE R E Q U E S T");
//
//
//		cof.getLayoutInflater(cof.getArguments()).inflate(
//				R.layout.alert_build_request, null);
	}

	public void testOnPlaceOrder() {
//		Order o = new Order(1, dineOnUser.getUserInfo(), TestUtility.getFakeMenuItems());
//		mActivity.onPlaceOrder(o);
	}

	public void testOnRequestMade() {
//		mActivity.onRequestMade("MORE WATER!!!!");
	}

}
