package uw.cse.dineon.user.restaurantselection.test;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import uw.cse.dineon.library.DineOnUser;
import uw.cse.dineon.library.RestaurantInfo;
import uw.cse.dineon.user.DineOnUserApplication;
import uw.cse.dineon.user.restaurantselection.RestaurantSelectionActivity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

public class RestaurantSelectionActivityTest extends
		ActivityInstrumentationTestCase2<RestaurantSelectionActivity> {

	private ParseUser testUser;
	private DineOnUser dineOnUser;
	private RestaurantSelectionActivity mActivity;
	private RestaurantInfo testRInfo;

	public RestaurantSelectionActivityTest() {
		super(RestaurantSelectionActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
//		Parse.initialize(null, "RUWTM02tSuenJPcHGyZ0foyemuL6fjyiIwlMO0Ul", "wvhUoFw5IudTuKIjpfqQoj8dADTT1vJcJHVFKWtK");
		setActivityInitialTouchMode(false);
		testUser = new ParseUser();
		testUser.setUsername("rest");
		dineOnUser = new DineOnUser(testUser);
		DineOnUserApplication.setDineOnUser(dineOnUser);
		testRInfo = new RestaurantInfo(testUser);
	    Intent addEvent = new Intent();
	    setActivityIntent(addEvent);

	    
		mActivity = getActivity();
//		
//		ParseQuery inner = new ParseQuery(RestaurantInfo.class.getSimpleName());
//		inner.whereEqualTo(RestaurantInfo.PARSEUSER, ParseUser.logIn("r", "r"));
//		ParseObject tempObj = inner.getFirst();
//		testRInfo = new RestaurantInfo(tempObj);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testAddRestaurantInfo() throws ParseException {
//		List<RestaurantInfo> tempList = new ArrayList<RestaurantInfo>();
//		tempList.add(testRInfo);
//		mActivity.addRestaurantInfos(tempList);
	}

	public void testOnRestaurantFocusedOn() {
		mActivity.onRestaurantFocusedOn(testRInfo);
	}

	
	public void testGetRestaurants() {
//		assertNotNull(mActivity.getRestaurants());
	}
	
	public void testShowNoRestaurantsDialog() {
//		mActivity.showNoRestaurantsDialog("test for failure");
//		mActivity.finish();
	}
	
	public void testDeleteResume() {
		getInstrumentation().waitForIdleSync();
		mActivity.finish();

		mActivity = getActivity();
	}

}
