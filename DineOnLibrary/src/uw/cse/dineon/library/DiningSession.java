package uw.cse.dineon.library;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.parse.ParseObject;

/**
 * 
 * @author zachr81
 *
 */
public class DiningSession extends Storable implements Parcelable {
	
	private List<User> users;
	private long startTime;
	private long endTime;
	private boolean isCheckedIn;
	private List<Order> orders;
	private int sessToken;
	private int tableID;
	RequestType waiterRequest;
	
	/**
	 * Creates a new DiningSession object.
	 * 
	 * @param orders List of Orders.
	 * @param sessToken int token for tracking session.
	 * @param tableID int ID of session's table.
	 */
	public DiningSession(List<Order> orders, int sessToken, int tableID) {
		super();
		this.sessToken = sessToken;
		this.orders = orders;
		this.tableID = tableID;
	}
	
	/**
	 * Creates a new DiningSession using a given parcel.
	 * @param source Parcel of information in 
	 * 		List<User>, long, long, (boolean stored as an) int, List<Order>, int, int
	 * 		order.
	 */
	public DiningSession(Parcel source) {
		readFromParcel(source);
	}

	/**
	 * @return the orders
	 */
	public List<Order> getOrders() {
		List<Order> copy = new ArrayList<Order>(orders.size());
		Collections.copy(copy, orders);
		return copy;
	}
	/**
	 * @param orders the orders to set
	 */
	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
	/**
	 * @return the sessToken
	 */
	public int getSessToken() {
		return sessToken;
	}
	/**
	 * @param sessToken the sessToken to set
	 */
	public void setSessToken(int sessToken) {
		this.sessToken = sessToken;
	}
	/**
	 * @return the tableID
	 */
	public int getTableID() {
		return tableID;
	}
	/**
	 * @param tableID the tableID to set
	 */
	public void setTableID(int tableID) {
		this.tableID = tableID;
	}
	
	/**
	 * 
	 * @param order to add
	 */
	public void addOrder(Order order) {
		orders.add(order);
	}
	
	/**
	 * 
	 * @param order to remove
	 */
	public void removeOrder(Order order) {
		orders.remove(order);
	}

	@Override
	public ParseObject packObject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void unpackObject(ParseObject pobj) {
		// TODO Auto-generated method stub
	}
	
	/**
	 * @return the users
	 * 
	 */
	public List<User> getUsers() {
		List<User> copy = new ArrayList<User>(users.size());
		Collections.copy(copy, users);
		return copy;
	}

	/**
	 * @param users the users to set
	 */
	public void setUsers(List<User> users) {
		this.users = users;
	}

	/**
	 * @return the startTime
	 */
	public long getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public long getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the isCheckedIn
	 */
	public boolean isCheckedIn() {
		return isCheckedIn;
	}

	/**
	 * @param isCheckedIn the isCheckedIn to set
	 */
	public void setCheckedIn(boolean isCheckedIn) {
		this.isCheckedIn = isCheckedIn;
	}

	@Override
	public int describeContents() {
		// TODO ?? - examples online use 0.
		return 0;
	}

	/**
	 * Writes this DiningSession to Parcel dest in the order:
	 * List<User>, long, long, (boolean stored as an) int, List<Order>, int, int
	 * to be retrieved at a later time.
	 * 
	 * @param dest Parcel to write DiningSession data to.
	 * @param flags int
	 */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// dest.writeInt(productID);
		dest.writeTypedList(users);
		dest.writeLong(startTime);
		dest.writeLong(endTime);
		
		// workaround to write boolean (1 = true, 0 = false)
		dest.writeInt(isCheckedIn ? 1 : 0);
		dest.writeTypedList(orders);
		dest.writeInt(sessToken);
		dest.writeInt(tableID);
	}
	
	/**
	 * Helper method for updating DiningSession with the data from a Parcel.
	 * @param source Parcel containing data in the order:
	 * 		List<User>, long, long, (boolean stored as an) int, List<Order>, int, int
	 */
	private void readFromParcel(Parcel source) {
		source.readTypedList(users, User.CREATOR); // default class load used
		startTime = source.readLong();
		endTime = source.readLong();
		isCheckedIn = (source.readInt() == 1 ? true : false);
		source.readTypedList(orders, null);
		sessToken = source.readInt();
		tableID = source.readInt();
	}
	
	/**
	 * Parcelable creator object of a MenuItem.
	 * Can create a MenuItem from a Parcel.
	 */
	public static final Parcelable.Creator<DiningSession> CREATOR = 
			new Parcelable.Creator<DiningSession>() {

				@Override
				public DiningSession createFromParcel(Parcel source) {
					return new DiningSession(source);
				}

				@Override
				public DiningSession[] newArray(int size) {
					return new DiningSession[size];
				}
			};
}
