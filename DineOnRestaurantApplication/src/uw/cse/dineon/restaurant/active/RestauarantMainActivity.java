package uw.cse.dineon.restaurant.active;

import java.util.ArrayList;
import java.util.List;

import uw.cse.dineon.library.CustomerRequest;
import uw.cse.dineon.library.DiningSession;
import uw.cse.dineon.library.Order;
import uw.cse.dineon.library.Restaurant;
import uw.cse.dineon.library.util.DevelopTools;
import uw.cse.dineon.restaurant.DineOnRestaurantActivity;
import uw.cse.dineon.restaurant.R;
import uw.cse.dineon.restaurant.active.DiningSessionListFragment.DiningSessionListListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

/**
 * This activity supports the main features for this restaurant
 * It presents a swipable interface for Restaurants to view 
 * 
 * 1. Pending Order
 * 2. Pending Requests
 * 3. Current Customers
 *  
 * @author mhotan
 */
public class RestauarantMainActivity extends DineOnRestaurantActivity implements
OrderListFragment.OrderItemListener,
RequestListFragment.RequestItemListener,
DiningSessionListListener {

	private static final String TAG = RestauarantMainActivity.class.getSimpleName(); 

	private static final String[] CONTENT = 
		{"Pending Orders" , "Pending Requests", "Current Sessions"};

	/**
	 * The pager widget, which handles animation and allows swiping horizontally to access previous
	 * and next wizard steps.
	 */
	private ViewPager mPager;

	/**
	 * The pager adapter, which provides the pages to the view pager widget.
	 */
	private ScreenSlidePagerAdapter mPagerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_restaurant_main);

		mPager = (ViewPager) findViewById(R.id.pager_restaurant_main);
		// Reset the adapter
		mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
		mPager.setAdapter(mPagerAdapter);
	}

	@Override
	protected void updateUI() {
		super.updateUI();
	}

	@Override
	protected void addDiningSession(DiningSession session) {
		super.addDiningSession(session);

		// Update our UI for the current dining session
		DiningSessionListFragment frag = mPagerAdapter.getCurrentDiningSessionListFragment();
		if (frag != null) {
			frag.addDiningSession(session);
		}
	}

	@Override
	protected void removeDiningSession(DiningSession session) {
		// Update our UI for the current dining session
		DiningSessionListFragment frag = mPagerAdapter.getCurrentDiningSessionListFragment();
		if (frag != null) {
			frag.removeDiningSession(session);
		}

		super.removeDiningSession(session);
	}

	@Override
	protected void addOrder(Order order) {
		super.addOrder(order);

		// Update our UI for the current added Order 
		OrderListFragment frag = mPagerAdapter.getCurrentOrderListFragment();
		if (frag != null) {
			frag.addOrder(order);
		}
	}

	@Override
	protected void completeOrder(Order order) {

		// Update our UI for the current added Order 
		OrderListFragment frag = mPagerAdapter.getCurrentOrderListFragment();
		if (frag != null) {
			frag.deleteOrder(order);
		}

		super.completeOrder(order);
	}

	@Override 
	protected void addCustomerRequest(CustomerRequest request) {
		super.addCustomerRequest(request);

		// Update our UI for the current added Request
		RequestListFragment frag = mPagerAdapter.getCurrentRequestListFragment();
		if (frag != null) {
			frag.addRequest(request);
		}	
	}

	@Override
	protected void removeCustomerRequest(CustomerRequest request) {

		// Update our UI for the current added Request
		RequestListFragment frag = mPagerAdapter.getCurrentRequestListFragment();
		if (frag != null) {
			frag.deleteRequest(request);
		}	

		super.removeCustomerRequest(request);
	}


	//////////////////////////////////////////////////////////////////////
	////	Listener for OrderDetailFragment.OrderDetailListener
	////	For Fragment call backs  
	//////////////////////////////////////////////////////////////////////

	@Override
	public List<DiningSession> getCurrentSessions() {
		if (getRestaurant() != null) {
			return getRestaurant().getSessions();
		}
		Log.w(TAG, "[getCurrentSessions] Restaurant is null");
		return new ArrayList<DiningSession>();
	}

	@Override
	public List<CustomerRequest> getCurrentRequests() {
		if (getRestaurant() != null) {
			return getRestaurant().getCustomerRequests();
		}
		Log.w(TAG, "[getCurrentRequests] Restaurant is null");
		return new ArrayList<CustomerRequest>();
	}

	@Override
	public List<Order> getCurrentOrders() {
		if (getRestaurant() != null) {
			return getRestaurant().getPendingOrders();
		}
		Log.w(TAG, "[getCurrentOrders] Restaurant is null");
		return new ArrayList<Order>();
	}

	@Override
	public void onRequestRequestDetail(CustomerRequest request) {
		DevelopTools.getUnimplementedDialog(this, null);

		//		Intent intent = new Intent(getApplicationContext(),
		//				RequestDetailActivity.class);
		//		startActivity(intent);
	}

	@Override
	public void onRequestOrderDetail(Order order) {
		DevelopTools.getUnimplementedDialog(this, null);
		//		Intent intent = new Intent(getApplicationContext(),
		//				OrderDetailActivity.class);
		//		startActivity(intent);
	}


	@Override
	public void onAssignStaffToRequest(CustomerRequest request, String staff) {
		// TODO Implement Add a field in customer request
		// Assigns a staff to customer request.
		request.setWaiter(staff);
	}

	@Override
	public void onRemoveRequest(CustomerRequest request) {
		// Use this and the super class to appropiately remove
		// the customer request
		removeCustomerRequest(request);
	}	

	@Override
	public void onProgressChanged(Order order, int progress) {
		// TODO Implement
		Log.i(TAG, "Progress of order: " + order + " changed to " + progress);
	}

	@Override
	public void onOrderComplete(Order order) {
		// Use this and the super class to appropriately complete
		// the Order
		completeOrder(order);
	}

	//////////////////////////////////////////////////////////////////////
	////	Methods overriden from DineOnRestaurantActivity
	//////////////////////////////////////////////////////////////////////

	/**
	 * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
	 * sequence.
	 */
	private class ScreenSlidePagerAdapter extends FragmentPagerAdapter {

		private final Fragment[] mFragments;

		/**
		 * This is a PageAdapter to control Restaurant displays
		 * that show orders, customer requests, and sessions.
		 * @param fragmentManager Fragment manager of this activity
		 */
		public ScreenSlidePagerAdapter(FragmentManager fragmentManager) {
			super(fragmentManager);
			mFragments = new Fragment[3];
		}

		@Override
		public Fragment getItem(int position) {

			Restaurant restaurant = getRestaurant();
			assert (restaurant != null); // WTF if that is null?
			
			// Narrow in position
			position = Math.min(Math.max(position, 0), CONTENT.length - 1);

			Fragment f;
			switch (position) {
			case 0:
				f = OrderListFragment.newInstance(restaurant.getPendingOrders());
				break;
			case 1:
				f = RequestListFragment.newInstance(restaurant.getCustomerRequests());
				break;
			case 2: // Should be 2
				f = DiningSessionListFragment.newInstance(restaurant.getSessions());
				break;
			// TODO Add more options
			default:
				Log.wtf(TAG, "ScreenSlidePagerAdapter weird index requested: " + position);
				return null;
			}
			return (mFragments[position] = f);
		}

		@Override
		public int getCount() {
			return CONTENT.length;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			position = Math.max(Math.min(position, CONTENT.length - 1), 0);
			return CONTENT[position];
		}

		/**
		 * Returns the a reference to the current Order list fragment. 
		 * @return Order List fragment if it exists, else null
		 */
		public OrderListFragment getCurrentOrderListFragment() {
			return (OrderListFragment) mFragments[0];
		}

		/**
		 * Returns a reference to the current Request List fragment. 
		 * @return Request List fragment if it exists, else null
		 */
		public RequestListFragment getCurrentRequestListFragment() {
			return (RequestListFragment) mFragments[1];
		}
		
		/**
		 * Returns current Dining Session list fragment instance.
		 * @return Dining Session list fragment if it exists, else null
		 */
		public DiningSessionListFragment getCurrentDiningSessionListFragment() {
			return (DiningSessionListFragment) mFragments[2];
		}
	}

}
