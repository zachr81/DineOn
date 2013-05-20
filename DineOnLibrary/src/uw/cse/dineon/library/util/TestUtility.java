package uw.cse.dineon.library.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.util.Log;

import com.parse.ParseException;
import com.parse.ParseUser;

import uw.cse.dineon.library.DineOnUser;
import uw.cse.dineon.library.DiningSession;
import uw.cse.dineon.library.Menu;
import uw.cse.dineon.library.MenuItem;
import uw.cse.dineon.library.Order;
import uw.cse.dineon.library.Restaurant;

/**
 * 
 * @author Espeo196, Jordan
 *
 */
public final class TestUtility {
	public static DineOnUser dineOnUser;
	public static Restaurant rest;
	public static ParseUser user;
	public static ParseUser restUser;
	
	/**
	 * Hidden constructor.
	 */
	private TestUtility() {	}
	
	/**
	 * Sets up a fields for testing use.
	 * Fields include DineOnUser ParseUser (user: testUser, pass: 12345),
	 * Restaurant ParseUser (user: testRestUser, pass: 12345) with a Menu
	 * and 1 menu item. DiningSession between testUser and testRestUser.
	 * @param isUserTest boolean whether to log in as testUser or testRestUser
	 * @throws ParseException 
	 */
	public static void testSetUp(boolean isUserTest) throws ParseException {
		user = new ParseUser();
		user.setUsername("testUser");
		user.setPassword("12345");
		user.signUp();
			
		restUser = new ParseUser();
		restUser.setUsername("testRestUser");
		restUser.setPassword("12345");
		restUser.signUp();				
		
		if(isUserTest) {
			ParseUser.logIn("testUser", "12345");				
		} else {
			ParseUser.logIn("testRestUser", "12345");				
		}
		dineOnUser = new DineOnUser(user);
		dineOnUser.saveOnCurrentThread();
		rest = new Restaurant(restUser);
		
		DiningSession ds = 
				new DiningSession(10, new Date(), dineOnUser.getUserInfo(), rest.getInfo());
		
		List<MenuItem> mi = getFakeMenuItems();
		
		Menu m = getFakeMenu();
		m.addNewItem(mi.get(0));
		m.saveOnCurrentThread();

		rest.getInfo().addMenu(m);
		rest.saveOnCurrentThread();

		Order one = new Order(1, dineOnUser.getUserInfo(), mi);
		ds.addPendingOrder(one);
		ds.saveOnCurrentThread();
		
		dineOnUser.setDiningSession(ds);
		dineOnUser.saveOnCurrentThread();
		
		rest.addDiningSession(ds);
		rest.saveOnCurrentThread();
	}

	/**
	 * Deletes ParseUsers set up by testSetUp.
	 * @throws ParseException 
	 */
	public static void testTearDown() throws ParseException {
		dineOnUser.getDiningSession().getOrders().
				get(0).getMenuItems().get(0).deleteFromCloud();
		rest.getInfo().getMenuList().get(0).deleteFromCloud();
		dineOnUser.getUserInfo().deleteFromCloud();
		rest.getInfo().deleteFromCloud();
		rest.deleteFromCloud();
		dineOnUser.getDiningSession().getOrders().get(0).deleteFromCloud();
		dineOnUser.getDiningSession().deleteFromCloud();
		dineOnUser.deleteFromCloud();
		
		if(user.getSessionToken() != null) {
			user.delete();				
		} else {
			ParseUser.logIn("testUser", "12345");
			user.delete();
		}
		if(restUser.getSessionToken() != null) {
			restUser.delete();
		} else {
			ParseUser.logIn("testRestUser", "12345");
			restUser.delete();
		}
	}
	
	/**
	 * Returns a list of fake menu items.
	 * 
	 * @return a list of fake menu items
	 */
	private static List<MenuItem> getFakeMenuItems() {
		List<MenuItem> menuItems = new ArrayList<MenuItem>();
		int itemCnt = 100;
		MenuItem steak = new MenuItem(itemCnt++, 12.99, "testSteak", 
				"A juicy (test) hunk of meat.");

		menuItems.add(steak);
	
		return new ArrayList<MenuItem>(menuItems);
	}
	
	/**
	 * @return A Menu representing a menu
	 */
	private static Menu getFakeMenu() {
		Menu entreeMenu = new Menu("testEntrees");
		for (MenuItem item: getFakeMenuItems()) {
			entreeMenu.addNewItem(item);
		}
		return entreeMenu;
	}
	
	/**
	 * @return DineOnUser
	 */
	public static DineOnUser getDineOnUser() {
		return dineOnUser;
	}

	/**
	 * @return Restaurant
	 */
	public static Restaurant getRest() {
		return rest;
	}

	/**
	 * @return ParseUser
	 */
	public static ParseUser getUser() {
		return user;
	}

	/**
	 * @return restaurant ParseUser
	 */
	public static ParseUser getRestUser() {
		return restUser;
	}
	
}
