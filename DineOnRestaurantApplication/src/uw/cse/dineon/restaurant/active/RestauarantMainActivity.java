package uw.cse.dineon.restaurant.active;

import java.util.ArrayList;
import java.util.List;

import uw.cse.dineon.restaurant.DineOnRestaurantActivity;
import uw.cse.dineon.restaurant.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

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
CustomerListFragment.CustomerListener 
{

	private static final String TAG = RestauarantMainActivity.class.getSimpleName(); 

	private static final String[] CONTENT = 
		{"Pending Orders" , "Pending Requests", "Current Customers"};

	/**
	 * The pager widget, which handles animation and allows swiping horizontally to access previous
	 * and next wizard steps.
	 */
	private ViewPager mPager;

	/**
	 * The pager adapter, which provides the pages to the view pager widget.
	 */
	private ScreenSlidePagerAdapter mPagerAdapter;

	private List<String> mOrders, mRequests, mCustomers;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_restaurant_main);

		mPager = (ViewPager) findViewById(R.id.pager_restaurant_main);
		mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
		mPager.setAdapter(mPagerAdapter);

		// TODO Fill and update appropiately
		mOrders = new ArrayList<String>();
		mRequests = new ArrayList<String>();
		mCustomers = new ArrayList<String>();

		// TODO remove default values

		String[] orders = {"Fried Chicken", "Peanut butter and Jelly", 
				"Fried Rice", "Chicken Noodle Soup", "Butte Balls"};
		for (String s: orders) {
			mOrders.add(s);
		}

		String[] requests = {"Water Please", "Waiter Needed", "There is a booger in my soup"};
		for (String s: requests) {
			mRequests.add(s);
		}

		String[] customers = {"Batman", "Robin", "Superman", "Wonderwoman"};
		for (String s: customers) {
			mCustomers.add(s);
		}
	}

	/**
	 * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
	 * sequence.
	 */
	private class ScreenSlidePagerAdapter extends FragmentPagerAdapter {

		private Fragment mCurrent;

		public ScreenSlidePagerAdapter(FragmentManager fragmentManager) {
			super(fragmentManager);
		}

		@Override
		public Fragment getItem(int position) {
			position = Math.min(Math.max(position, 0), CONTENT.length -1);

			Bundle args = new Bundle();
			Fragment f;

			switch (position) {
			case 0:
				f = OrderListFragment.newInstance(mOrders);
				break;
			case 1:
				f = RequestListFragment.newInstance(mRequests);
				break;
			default:
				f = CustomerListFragment.newInstance(mCustomers);
			}

			return f;
		}

		@Override
		public int getCount() {
			return CONTENT.length;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			position = Math.max(Math.min(position, CONTENT.length-1), 0);
			return CONTENT[position];
		}
		
		public Fragment getCurrentFragment(){
			return mCurrent;
		}
	}

	/**
	 * Updates the current fragment if it is in focus
	 * 
	 * @param customer
	 */
	private void addCustomer(String customer){
		Fragment f = mPagerAdapter.getCurrentFragment();
		if (f != null && f instanceof CustomerListFragment) {
			CustomerListFragment frag = (CustomerListFragment) f;
			frag.addCustomer(customer);
		}
		mCustomers.add(customer);
	}
	
	/**
	 * Updates the current fragment if it is in focus
	 * @param order
	 */
	private void addOrder(String order){
		Fragment f = mPagerAdapter.getCurrentFragment();
		if (f != null && f instanceof OrderListFragment) {
			OrderListFragment frag = (OrderListFragment) f;
			frag.addOrder(order);
		}
		mOrders.add(order);
	}
	
	/**
	 * Updates the current fragment if it is in focus
	 * @param request
	 */
	private void addRequest(String request){
		Fragment f = mPagerAdapter.getCurrentFragment();
		if (f != null && f instanceof RequestListFragment) {
			RequestListFragment frag = (RequestListFragment) f;
			frag.addRequest(request);
		}
		mRequests.add(request);
	}

	//////////////////////////////////////////////////////////////////////
	////	Listener for OrderDetailFragment.OrderDetailListener
	////	For Fragment call backs  
	//////////////////////////////////////////////////////////////////////

	@Override
	public List<String> getCurrentUsers() {
		return mCustomers;
	}

	@Override
	public List<String> getCurrentRequests() {
		return mRequests;
	}

	@Override
	public List<String> getCurrentOrders() {
		return mOrders;
	}

	@Override
	public void onRequestRequestDetail(String request) {
		Intent intent = new Intent(getApplicationContext(),
				RequestDetailActivity.class);
		intent.putExtra(RequestDetailActivity.EXTRA_REQUEST, request);
		startActivity(intent);
	}

	@Override
	public void onRequestOrderDetail(String order) {
		Intent intent = new Intent(getApplicationContext(),
				OrderDetailActivity.class);
		intent.putExtra(OrderDetailActivity.EXTRA_ORDER, order);
		startActivity(intent);
	}

	@Override
	public void onAssignStaffToRequest(String request, String staff) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDismissRequest(String request) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRemoveRequest(String request) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProgressChanged(String order, int progress) {
		// TODO Auto-generated method stub

	}


	@Override
	public void onOrderComplete(String order) {
		// TODO Auto-generated method stub

	}


}