package uw.cse.dineon.restaurant.test;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import uw.cse.dineon.library.CustomerRequest;
import uw.cse.dineon.library.DineOnUser;
import uw.cse.dineon.library.DiningSession;
import uw.cse.dineon.library.MenuItem;
import uw.cse.dineon.library.Order;
import uw.cse.dineon.library.Restaurant;
import uw.cse.dineon.library.RestaurantInfo;
import uw.cse.dineon.library.UserInfo;
import uw.cse.dineon.library.util.DineOnConstants;
import uw.cse.dineon.restaurant.active.RestauarantMainActivity;
import android.app.Activity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.ImageButton;
import android.widget.TextView;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;

public class RestaurantMainActivityTest extends
ActivityInstrumentationTestCase2<RestauarantMainActivity> {

	private Activity mActivity;
	private Restaurant r;

	private ParseUser mUser;

	private UserInfo mUI;

	private static String menuItemText = "Fake Menu Item 1"; 
	private static String menuItemDescription = "Fake Menu Item 1 Description";

	public RestaurantMainActivityTest() throws ParseException {
		super(RestauarantMainActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		Parse.initialize(getInstrumentation().getTargetContext(), 
				"RUWTM02tSuenJPcHGyZ0foyemuL6fjyiIwlMO0Ul", 
				"wvhUoFw5IudTuKIjpfqQoj8dADTT1vJcJHVFKWtK");
		mUser = ParseUser.logIn("glee23", "p");

		mUI = new UserInfo(mUser);

		r = createFakeRestaurant(mUser);

		Intent intent = new Intent(getInstrumentation().getTargetContext(),
				RestauarantMainActivity.class);
		intent.putExtra(DineOnConstants.KEY_USER, new DineOnUser(mUser));
		intent.putExtra(DineOnConstants.KEY_RESTAURANT, r);
		setActivityIntent(intent);

		setActivityInitialTouchMode(false);

		mActivity = getActivity();
	}

	private Restaurant createFakeRestaurant(ParseUser user) throws ParseException{
		Restaurant r = new Restaurant(user);
		r.addOrder(createFakeOrder(user));
		r.addCustomerRequest(createFakeRequest(user));
		r.addDiningSession(createFakeDiningSession(user));
		return r;
	}

	private Order createFakeOrder(ParseUser user){
		return new Order(1, mUI, createFakeMenuItems());
	}

	private List<MenuItem> createFakeMenuItems(){
		MenuItem m = new MenuItem(1, 3.99, menuItemText, menuItemDescription);
		List<MenuItem> items = new ArrayList<MenuItem>();
		items.add(m);
		return items;
	}

	private CustomerRequest createFakeRequest(ParseUser user){
		return new CustomerRequest("I want my food now!", mUI, new GregorianCalendar().getTime());
	}

	private DiningSession createFakeDiningSession(ParseUser user) throws ParseException {
		return new DiningSession(1, 
				new GregorianCalendar().getTime(), 
				mUI, new RestaurantInfo(user));
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testOrderLayoutItemsPopulate() {
		TextView orderTitle = (TextView) mActivity.findViewById(uw.cse.dineon.restaurant.R.id.button_order_title);
		TextView orderTime = (TextView) mActivity.findViewById(uw.cse.dineon.restaurant.R.id.label_order_time);
		ImageButton arrowButton = (ImageButton) mActivity.findViewById(uw.cse.dineon.restaurant.R.id.button_expand_order);
		assertNotNull(orderTitle);
		assertNotNull(orderTime);
		assertNotNull(arrowButton);
	}

	public void testRequestLayoutItemsPopulate() {
		TextView requestTitle = (TextView) mActivity.findViewById(uw.cse.dineon.restaurant.R.id.label_request_title);
		TextView requestTime = (TextView) mActivity.findViewById(uw.cse.dineon.restaurant.R.id.label_request_time);
		ImageButton arrowButton = (ImageButton) mActivity.findViewById(uw.cse.dineon.restaurant.R.id.button_expand_request);
		System.out.println(requestTitle.getText().toString());
		assertNotNull(requestTitle);
		assertNotNull(requestTime);
		assertNotNull(arrowButton);
	}
	
//  Uncomment -- Will pass once onOrderRequestDetail is implemented
//	public void testGoToOrderDetailActivity() {
//		ActivityMonitor monitor = getInstrumentation().addMonitor(OrderDetailActivity.class.getName(), null, false);
//		ImageButton orderArrowButton = (ImageButton) mActivity.findViewById(uw.cse.dineon.restaurant.R.id.button_expand_order);
//		TouchUtils.clickView(this, orderArrowButton);
//		TouchUtils.clickView(this, orderArrowButton);
//		OrderDetailActivity startedActivity = (OrderDetailActivity) monitor.waitForActivityWithTimeout(2000);
//		assertNotNull(startedActivity);
//		this.sendKeys(KeyEvent.KEYCODE_BACK);
//	}

}
