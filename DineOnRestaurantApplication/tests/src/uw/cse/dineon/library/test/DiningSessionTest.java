package uw.cse.dineon.library.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import uw.cse.dineon.library.CurrentOrderItem;
import uw.cse.dineon.library.DiningSession;
import uw.cse.dineon.library.MenuItem;
import uw.cse.dineon.library.Order;
import uw.cse.dineon.library.RestaurantInfo;
import uw.cse.dineon.library.UserInfo;
import uw.cse.dineon.library.util.FakeRestaurantInformation;
import android.app.Activity;
import android.content.Context;
import android.test.AndroidTestCase;

import com.parse.Parse;
import com.parse.ParseUser;

/**
 * 
 * @author zach
 *
 */
public class DiningSessionTest extends AndroidTestCase {

	Activity activity;
	Context mContext = null;

	DiningSession testSession;
	ParseUser mUser;
	UserInfo testUInfo;
	List<CurrentOrderItem> testItems;
	MenuItem testItem;
	List<Order> orders;
	RestaurantInfo testRInfo;

//
//	/**
//	 * Default constructor. 
//	 */
//	public DiningSessionTest() {
//		super();
//	}

	@Override
	protected void setUp() throws Exception { 
		mUser = new ParseUser();
		mUser.setEmail("dst@a.com");
		mUser.setObjectId("245");
		
		FakeRestaurantInformation f = new FakeRestaurantInformation(mUser);

		testUInfo = new UserInfo(mUser);
		testUInfo.setObjId("tui");

		testRInfo = new RestaurantInfo(mUser);
		testSession = new DiningSession(32, new Date(3254645), testUInfo, testRInfo);

		testItems = new ArrayList<CurrentOrderItem>();
		List<MenuItem> menuItems = f.getFakeMenuItems();
		for (MenuItem m : menuItems) {
			testItems.add(new CurrentOrderItem(m));
		}
		orders = f.getFakeOrders();

	}
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Asserts that the session has the correct list of orders
	 * 
	 * White box
	 */
	public void testGetOrdersInitial() {
		assertEquals(new ArrayList<Order>(), testSession.getOrders());
	}
	
	public void testGetOrdersAfterAdd() {
		List<Order> tempList = new ArrayList<Order>();
		tempList.add(orders.get(1));
		testSession.addPendingOrder(orders.get(1));
		assertEquals(tempList, testSession.getOrders());
	}

	/**
	 * Asserts that the session has the correct list of users stored
	 * 
	 * White box
	 */
	public void testGetUsersInitial() {
		List<UserInfo> tempList = new ArrayList<UserInfo>();
		tempList.add(testUInfo);
		assertEquals(tempList, testSession.getUsers());
	}
	
	/**
	 * Asserts that the correct user is added
	 * 
	 * White box
	 */
	public void testAddUser() {
		testSession.addUser(testUInfo);
		List<UserInfo> tempList = new ArrayList<UserInfo>();
		tempList.add(testUInfo);
		tempList.add(testUInfo);
		assertEquals(tempList, testSession.getUsers());
	}
	
	/**
	 * Asserts that the session has the correct table id stored
	 * 
	 * White box
	 */
	public void testGetTableID() {
		assertEquals(32, testSession.getTableID());
	}

	/**
	 * Asserts that the session has the correct changed table id stored
	 * 
	 * White box
	 */
	public void testResetTableID() {
		testSession.resetTableID(42);
		assertEquals(42, testSession.getTableID());
	}



}
