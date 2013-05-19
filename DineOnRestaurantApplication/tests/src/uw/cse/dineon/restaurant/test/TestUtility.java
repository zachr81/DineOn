package uw.cse.dineon.restaurant.test;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import uw.cse.dineon.library.CustomerRequest;
import uw.cse.dineon.library.DiningSession;
import uw.cse.dineon.library.MenuItem;
import uw.cse.dineon.library.Order;
import uw.cse.dineon.library.Restaurant;
import uw.cse.dineon.library.RestaurantInfo;
import uw.cse.dineon.library.UserInfo;

import com.parse.ParseException;
import com.parse.ParseUser;

public class TestUtility {
	
	public static Restaurant createFakeRestaurant(ParseUser user) throws ParseException{
		Restaurant r = new Restaurant(user);
		return r;
	}

	public static Order createFakeOrder(int orderNum, UserInfo info){
		return new Order(orderNum, info, createFakeMenuItems(2));
	}

	public static CustomerRequest createFakeRequest(UserInfo info){
		return new CustomerRequest("[fake] I want my food now!", info, new GregorianCalendar().getTime());
	}

	public static List<MenuItem> createFakeMenuItems(int qty){
		qty = Math.max(0, qty);
		List<MenuItem> items = new ArrayList<MenuItem>();
		for (int i = 0; i < qty; ++i) {
			MenuItem m = new MenuItem(
					qty+1+i, 3.99, "FakeMenuItem " + (i+1), "FakeMenuItemDescription " + (i+1));	
			items.add(m);
		}
		return items;
	}

	public static DiningSession createFakeDiningSession(UserInfo user, RestaurantInfo restInfo) throws ParseException {
		return new DiningSession(1, 
				new GregorianCalendar().getTime(), 
				user, restInfo);
	}
	
}
