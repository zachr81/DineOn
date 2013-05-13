package uw.cse.dineon.library.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

import uw.cse.dineon.library.DiningSession;
import uw.cse.dineon.library.MenuItem;
import uw.cse.dineon.library.Order;
import uw.cse.dineon.library.UserInfo;
import junit.framework.TestCase;

public class DiningSessionTest extends TestCase {

	DiningSession testSession;
	ParseUser testUser;
	UserInfo testUInfo;
	List<MenuItem> testItems;
	MenuItem testItem;
	Order testOrder;
	List<Order> orders;
	List<UserInfo> testUInfos;
	
	public DiningSessionTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		new ParseObject(UserInfo.class.getSimpleName());
		testSession = new DiningSession(32, new Date(3254645));
		testUser = new ParseUser();
		testUser.setUsername("tester");
		testUser.setPassword("password");
		testUser.setEmail("test@gmail.com");
		testUser.save();
		testUInfo = new UserInfo(testUser);
		testItems = new ArrayList<MenuItem>();
		testItem = new MenuItem(24, 4.5, "Root Beer Float", "Ice cream and root beer");
		testItems.add(testItem);
		testOrder = new Order(32, testUInfo, testItems);
		orders = new ArrayList<Order>();
		orders.add(testOrder);
		
		testUInfos.add(testUInfo);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		testUser.deleteInBackground();
	}

	public void testPackObject() throws ParseException {
		//Default state
		ParseObject testPack1 = testSession.packObject();
		
		DiningSession newSess = new DiningSession(testPack1);
		
		assertEquals(testSession.getTableID(), newSess.getTableID());
		assertEquals(testSession.getUsers(), newSess.getUsers());
		assertEquals(testSession.getPendingOrders(), newSess.getPendingOrders());
		assertEquals(testSession.getCompletedOrders(), newSess.getCompletedOrders());
		assertEquals(testSession.getStartTime(), newSess.getStartTime());
	}

	public void testDiningSessionInt() {
		DiningSession constructSession = new DiningSession(32);
		assertEquals(32, constructSession.getTableID());
	}

	public void testDiningSessionIntDate() {
		assertEquals(32, testSession.getTableID());
		assertEquals(new Date(3254645), testSession.getTableID());
	}

	public void testDiningSessionParseObject() {
		//TODO fail("Not yet implemented");
	}

	//also tests addpendingorders
	public void testGetPendingOrders() {

		
		testSession.addPendingOrder(testOrder);

		
		assertEquals(orders, testSession.getPendingOrders());
	}

	public void testGetCompletedOrders() {
		testSession.orderServed(testOrder);
		assertEquals(orders, testSession.getCompletedOrders());
	}

	public void testOrderServed() {
		testSession.orderServed(testOrder);
		assertEquals(new ArrayList<Order>(), testSession.getPendingOrders());
	}

	public void testGetTableID() {
		assertEquals(32, testSession.getTableID());
	}

	public void testResetTableID() {
		testSession.resetTableID(42);
		assertEquals(42, testSession.getTableID());
	}

	public void testGetUsers() {
		assertEquals(new ArrayList<UserInfo>(), testSession.getUsers());
	}

	public void testAddUser() {
		testSession.addUser(testUInfo);
		assertEquals(testUInfos, testSession.getUsers());
	}

	public void testGetStartTime() {
		assertEquals(new Date(3254645), testSession.getStartTime());
	}

}
