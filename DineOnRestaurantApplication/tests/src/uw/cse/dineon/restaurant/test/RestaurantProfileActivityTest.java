package uw.cse.dineon.restaurant.test;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;

import uw.cse.dineon.library.CustomerRequest;
import uw.cse.dineon.library.DiningSession;
import uw.cse.dineon.library.MenuItem;
import uw.cse.dineon.library.Order;
import uw.cse.dineon.library.Restaurant;
import uw.cse.dineon.library.RestaurantInfo;
import uw.cse.dineon.library.UserInfo;
import uw.cse.dineon.library.util.DineOnConstants;
import uw.cse.dineon.restaurant.profile.*;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.TextView;
import android.app.AlertDialog;

public class RestaurantProfileActivityTest extends
		ActivityInstrumentationTestCase2<ProfileActivity> {

	private ProfileActivity mActivity;
	private Restaurant r;

	private ParseUser mUser;

	private UserInfo mUI;

	private RestaurantInfo mRI;

	View mView;

	int orderNum = 100;

	private static String menuItemText = "Fake Menu Item 1";
	private static String menuItemDescription = "Fake Menu Item 1 Description";

	public RestaurantProfileActivityTest() {
		super(ProfileActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		Parse.initialize(getInstrumentation().getTargetContext(),
				"RUWTM02tSuenJPcHGyZ0foyemuL6fjyiIwlMO0Ul",
				"wvhUoFw5IudTuKIjpfqQoj8dADTT1vJcJHVFKWtK");
		mUser = ParseUser.logIn("glee23", "p");

		r = createFakeRestaurant(mUser);
		r.getInfo().setAddr("5513");
		r.getInfo().setPhone("112233");

		Intent intent = new Intent(getInstrumentation().getTargetContext(), ProfileActivity.class);
		intent.putExtra(DineOnConstants.KEY_RESTAURANT, r);
		setActivityIntent(intent);

		setActivityInitialTouchMode(false);

		mActivity = getActivity();
	}

	protected void tearDown() throws Exception {
		mUser = null;
		mUI = null;
		mActivity.finish();
		super.tearDown();
	}

	public void testActionBarTabs() {
		mActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				mActivity.getActionBar().setSelectedNavigationItem(0);
			}
		});
		getInstrumentation().waitForIdleSync();
		Fragment frag = mActivity.getSupportFragmentManager().findFragmentByTag(
				ProfileActivity.LAST_FRAG_TAG);
		assertEquals(RestaurantInfoFragment.class, frag.getClass());
		mActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				mActivity.getActionBar().setSelectedNavigationItem(1);
			}
		});
		getInstrumentation().waitForIdleSync();
		frag = mActivity.getSupportFragmentManager().findFragmentByTag(
				ProfileActivity.LAST_FRAG_TAG);
		assertEquals(MenuItemsFragment.class, frag.getClass());
	}

	public void testAddMenuItem() {
		mActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				mActivity.getActionBar().setSelectedNavigationItem(1);
			}
		});
		getInstrumentation().waitForIdleSync();// Only way to wait for UI
												// thread;
		boolean result = getInstrumentation().invokeMenuActionSync(mActivity,
				uw.cse.dineon.restaurant.R.id.menu_add_menu_item, 0);

		assertTrue(result); // true if button selected success
		getInstrumentation().waitForIdleSync();
		this.sendKeys("T E S T SPACE I T E M");
		MenuItemsFragment frag = (MenuItemsFragment) mActivity.getSupportFragmentManager()
				.findFragmentByTag(ProfileActivity.LAST_FRAG_TAG);
		View input = frag.newItemAlert
				.findViewById(uw.cse.dineon.restaurant.R.id.input_menuitem_title);
		assertNotNull(input);
		String text = ((TextView) input).getText().toString().toLowerCase();
		assertEquals("test item", text);

	}

	// CP'd from RestaurantMainActivityTest
	private Restaurant createFakeRestaurant(ParseUser user) throws ParseException {
		Restaurant r = new Restaurant(user);
		r.addOrder(createFakeOrder(user));
		r.addCustomerRequest(createFakeRequest(user));
		r.addDiningSession(createFakeDiningSession(user));
		return r;
	}

	private Order createFakeOrder(ParseUser user) {
		return new Order(orderNum++, mUI, createFakeMenuItems());
	}

	private List<MenuItem> createFakeMenuItems() {
		MenuItem m = new MenuItem(1, 3.99, menuItemText, menuItemDescription);
		List<MenuItem> items = new ArrayList<MenuItem>();
		items.add(m);
		return items;
	}

	private CustomerRequest createFakeRequest(ParseUser user) {
		return new CustomerRequest("I want my food now!", mUI, new GregorianCalendar().getTime());
	}

	private DiningSession createFakeDiningSession(ParseUser user) throws ParseException {
		return new DiningSession(1, new GregorianCalendar().getTime(), mUI, mRI);
	}
}
