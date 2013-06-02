package uw.cse.dineon.restaurant.test;

import com.jayway.android.robotium.solo.Solo;
import com.parse.ParseException;

import uw.cse.dineon.library.Restaurant;
import uw.cse.dineon.library.util.TestUtility;
import uw.cse.dineon.restaurant.DineOnRestaurantApplication;
import uw.cse.dineon.restaurant.profile.ProfileActivity;
import android.support.v4.app.FragmentActivity;
import android.test.ActivityInstrumentationTestCase2;

/**
 * White box testing to test manipulation of restaurant info.
 * @author mhotan
 *
 */
public class RobotiumProfileInfoTest extends
		ActivityInstrumentationTestCase2<ProfileActivity> {

	private Solo solo;
	private FragmentActivity mActivity;

	public RobotiumProfileInfoTest() {
		super(ProfileActivity.class);
	}
	
	@Override
	public void setUp() throws ParseException {
		Restaurant rest = TestUtility.createFakeRestaurant();
		DineOnRestaurantApplication.logIn(rest);
		
		mActivity = getActivity();
		solo = new Solo(getInstrumentation(), mActivity);
	}
	
	@Override
	public void tearDown() throws Exception {
		solo.finishOpenedActivities();
		DineOnRestaurantApplication.logOut(null);
	}
	
	public void testAssertTrivial() {
		assertTrue(true);
	}
}
