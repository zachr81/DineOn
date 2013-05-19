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
import uw.cse.dineon.library.UserInfo;

/**
 * 
 * @author Espeo196, Jordan
 *
 */
public class TestUtility {
	public static DineOnUser dineOnUser;
	
	
	public static boolean testSetUp() {
		try {
			ParseUser user = new ParseUser();
			user.setUsername("testUser");
			user.setPassword("12345");
			user.signUp();
			ParseUser.logIn("testUser", "12345");
	
			ParseUser restUser = new ParseUser();
			restUser.setUsername("testRestUser");
			restUser.setPassword("12345");
			restUser.signUp();
			ParseUser.logIn("testRestUser", "12345");
			
			
			dineOnUser = new DineOnUser(user);
			
			Restaurant rest = new Restaurant(restUser);
			
			DiningSession ds = 
					new DiningSession(10, new Date(), dineOnUser.getUserInfo(), rest.getInfo());
			
			List<MenuItem> mi = getFakeMenuItems();
			Order one = new Order(1, dineOnUser.getUserInfo(), mi);
			ds.addPendingOrder(one);
			
			Menu m = getFakeMenu();
			m.addNewItem(mi.get(0));
			rest.getInfo().addMenu(m);
			
		} catch (ParseException e) {
			Log.e("TestUtility", "Failed in creation process.");
			return false;
		}
		
		
		
		return true;
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
	 * @return A Menu representing an Entree menu
	 */
	private static Menu getFakeMenu() {
		Menu entreeMenu = new Menu("testEntrees");
		for (MenuItem item: getFakeMenuItems()) {
			entreeMenu.addNewItem(item);
		}
		return entreeMenu;
	}
	
}
