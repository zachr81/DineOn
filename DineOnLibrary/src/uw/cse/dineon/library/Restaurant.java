package uw.cse.dineon.library;

import java.util.*;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.parse.ParseObject;

/**
 * 
 * @author zachr81
 *
 */
public class Restaurant extends Storable implements Parcelable {

	public static final String MENUS = "menus";
	public static final String RESERVATION_LIST = "reservationList";
	public static final String INFO = "info";
	public static final String ORDERS = "orders";
	public static final String SESSIONS = "sessions";
	
	private Map<String,Menu> menus;
	private List<Reservation> reservationList;
	private RestaurantInfo info;
	private List<Order> orders;
	private List<DiningSession> sessions;
	
	/**
	 * @param Menu
	 * @param reservationList
	 * @param info
	 * @param orders
	 * @Param sessions
	 */
	public Restaurant(Map<String,Menu> menus, List<Reservation> reservationList, RestaurantInfo info,
			List<Order> orders, List<DiningSession> sessions) {
		super();
		this.menus = menus;
		this.reservationList = reservationList;
		this.info = info;
		this.orders = orders;
		this.sessions = sessions;
	}
	
	/**
	 * Creates a Restaurant object from the given Parcel.
	 * 
	 * @param source Parcel of information in:
	 * 		Menu, ReservationList, RestaurantInfo, Orders, Sessions.
	 */
	public Restaurant(Parcel source) {
		readFromParcel(source);
	}
	
	/**
	 * @return a shallow copy of the Menu map
	 */
	public Map<String,Menu> getMenus() {
		return new HashMap<String,Menu>(menus);
	}
	
	/**
	 * @param menu: A Menu object to set as the new menu
	 * 
	 */
	public void setMenus(Map<String,Menu> menus) {
		this.menus = menus;
	}
	
	/**
	 * @return List<Reservation>
	 */
	public List<Reservation> getReservationList() {
		List<Reservation> copy = new ArrayList<Reservation>(reservationList.size());
		Collections.copy(copy, reservationList);
		return copy;
	}
	
	/**
	 * @param reservationList: A List of Reservations to set as the new reservation list
	 * 
	 */
	public void setReservationList(List<Reservation> newReservationList) {
		this.reservationList = newReservationList;
	}
	
	/**
	 * @return RestaurantInfo
	 */
	public RestaurantInfo getInfo() {
		return info;
	}
	
	/**
	 * Sets info to the param value
	 * @param RestaurantInfo
	 */
	public void setInfo(RestaurantInfo newInfo) {
		this.info = newInfo;
	}
	
	/**
	 * @return List<Order>
	 */
	public List<Order> getOrders() {
		List<Order> copy = new ArrayList<Order>(orders.size());
		Collections.copy(copy, orders);
		return copy;
	}
	
	/**
	 * Sets orders to the parameter value
	 * @param List<Order>
	 */
	public void setOrders(List<Order> newOrders) {
		orders = newOrders;
	}
	
	/**
	 * @return List<DiningSession>
	 */
	public List<DiningSession> getSessions() {
		List<DiningSession> copy = new ArrayList<DiningSession>(sessions.size());
		Collections.copy(copy, sessions);
		return copy;
	}
	
	/**
	 * Sets sessions to the parameter value
	 * @param List<DiningSession>
	 */
	public void setSessions(List<DiningSession> newSessions) {
		sessions = newSessions;
	}
	
	/**
	 * Adds the given menu to the menu map
	 * @param String for the name
	 * @param Menu to add
	 */
	public void addMenu(String name, Menu newMenu) {
		menus.put(name, newMenu);
	}
	
	/**
	 * Remove the specified menu
	 * @param name
	 */
	public void removeMenu(String name) {
		menus.remove(name);
	}
	
	/**
	 * Return the menu for the specified name
	 * @param name
	 */
	public Menu getMenu(String name) {
		return menus.get(name);
	}
	
	/**
	 * Adds the given reservation to the reservation list
	 * @param Reservation
	 */
	public void addReservation(Reservation newReservation) {
		reservationList.add(newReservation);
	}
	
	/**
	 * Remove the specified reservation
	 * @param Reservation
	 */
	public void removeReservation(Reservation removeReservation) {
		reservationList.remove(removeReservation);
	}
	
	/**
	 * Adds given order to orders
	 * @param Order
	 */
	public void addOrder(Order order) {
		orders.add(order);
	}
	
	/**
	 * Remove given order from orders
	 * @param Order
	 */
	public void removeOrder(Order order) {
		orders.remove(order);
	}
	
	/**
	 * Adds given DiningSession to sessions
	 * @param DiningSession
	 */
	public void addDiningSession(DiningSession session) {
		sessions.add(session);
	}
	
	/**
	 * Removes given DiningSession from sessions
	 * @param DiningSession
	 */
	public void removeDiningSession(DiningSession session) {
		sessions.remove(session);
	}


	@Override
	public ParseObject packObject() {
		ParseObject pobj = new ParseObject(this.getClass().getSimpleName());
		pobj.add(Restaurant.MENUS, this.menus);
		pobj.add(Restaurant.INFO, this.info);
		pobj.add(Restaurant.RESERVATION_LIST, this.reservationList);
		pobj.add(Restaurant.ORDERS, this.orders);
		pobj.add(Restaurant.SESSIONS, this.sessions);
		//in case this storable is going to be used after the pack.
		this.setObjId(pobj.getObjectId());
				
		return pobj;
	}

	@Override
	public void unpackObject(ParseObject pobj) {
		this.setObjId(pobj.getObjectId());
		this.setMenus((Map<String, Menu>) pobj.get(Restaurant.MENUS));
		this.setInfo((RestaurantInfo)pobj.get(Restaurant.INFO));
		this.setReservationList((List<Reservation>) pobj.get(Restaurant.RESERVATION_LIST));
		this.setOrders((List<Order>) pobj.get(Restaurant.ORDERS));
		this.setSessions((List<DiningSession>) pobj.get(Restaurant.SESSIONS));
		
	}

	/**
	 * A Parcel method to describe the contents of the object
	 */
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Write the object to a parcel object
	 * @param the Parcel to write to and any set flags
	 */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeMap(menus);
		dest.writeTypedList(reservationList);
		dest.writeParcelable(info, flags);
		dest.writeTypedList(orders);
		dest.writeTypedList(sessions);
						
	}
	
	/**
	 * Parcelable creator object of a Restaurant.
	 * Can create a Restaurant from a Parcel.
	 */
	public static final Parcelable.Creator<Restaurant> CREATOR = 
			new Parcelable.Creator<Restaurant>() {

				@Override
				public Restaurant createFromParcel(Parcel source) {
					return new Restaurant(source);
				}

				@Override
				public Restaurant[] newArray(int size) {
					return new Restaurant[size];
				}
	};
			
	//read an object back out of parcel
	private void readFromParcel(Parcel source) {
		source.readMap(menus, Menu.class.getClassLoader());
		source.readTypedList(reservationList, Reservation.CREATOR);
		info = source.readParcelable(RestaurantInfo.class.getClassLoader());
		source.readTypedList(orders, Order.CREATOR);
		source.readTypedList(sessions, DiningSession.CREATOR);
	}
}
