package uw.cse.dineon.user.bill.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.parse.Parse;
import com.parse.ParseUser;

import uw.cse.dineon.library.CurrentOrderItem;
import uw.cse.dineon.library.DineOnUser;
import uw.cse.dineon.library.DiningSession;
import uw.cse.dineon.library.Menu;
import uw.cse.dineon.library.MenuItem;
import uw.cse.dineon.library.Order;
import uw.cse.dineon.library.Restaurant;
import uw.cse.dineon.library.util.DineOnConstants;
import uw.cse.dineon.library.util.TestUtility;
import uw.cse.dineon.user.DineOnUserApplication;
import uw.cse.dineon.user.R;
import uw.cse.dineon.user.bill.CurrentOrderActivity;
import uw.cse.dineon.user.bill.CurrentOrderFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

public class CurrentOrderActivityTest extends
		ActivityInstrumentationTestCase2<CurrentOrderActivity> {

	private CurrentOrderActivity mActivity;
	private DineOnUser dineOnUser;
	private DiningSession ds;
	private Instrumentation testIn;
	private Fragment mFrag;

	public CurrentOrderActivityTest() {
		super(CurrentOrderActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		
		testIn = new Instrumentation();
		
//		Parse.initialize(null, "RUWTM02tSuenJPcHGyZ0foyemuL6fjyiIwlMO0Ul", "wvhUoFw5IudTuKIjpfqQoj8dADTT1vJcJHVFKWtK");
//		setActivityInitialTouchMode(false);
		ParseUser user = new ParseUser();
		user.setUsername("testUser");
		user.setPassword("12345");
		
		ParseUser restUser = new ParseUser();
		restUser.setUsername("testRestUser");
		restUser.setPassword("12345");
		
		dineOnUser = new DineOnUser(user);
		
		Restaurant rest = new Restaurant(restUser);
		ds = 
				new DiningSession(10, new Date(), dineOnUser.getUserInfo(), rest.getInfo());
		
		List<MenuItem> mis = TestUtility.createFakeMenuItems(3);
		List<CurrentOrderItem> coi = new ArrayList<CurrentOrderItem>();
		for(MenuItem mi : mis) {
			coi.add(new CurrentOrderItem(mi));
		}
		Order one = new Order(1, dineOnUser.getUserInfo(), coi);
		ds.addPendingOrder(one);
		dineOnUser.setDiningSession(ds);
		Menu m = TestUtility.createFakeMenu();
		m.addNewItem(mis.get(0));
		rest.getInfo().addMenu(m);
		
	    Intent addEvent = new Intent();
	    addEvent.putExtra(DineOnConstants.KEY_USER, dineOnUser);
	    setActivityIntent(addEvent);
	    
	    DineOnUserApplication.setDineOnUser(dineOnUser);
		DineOnUserApplication.setCurrentDiningSession(ds);
	    
		mActivity = getActivity();
		
		FragmentManager mFragMan = mActivity.getFragmentManager();
		mFrag = mFragMan.findFragmentById(R.id.fragment_current_order);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testOnPlaceOrder() {
		//mActivity.onPlaceOrder(ds.getOrders().get(0));
	}

	public void testOnRequestMade() {
		//mActivity.onRequestMade("Water");
	}
	
	public void testonOptionsItemSelected() {
//		android.view.MenuItem mi = (android.view.MenuItem) mActivity.findViewById(R.id.option_paybill);
//		mActivity.onOptionsItemSelected(mi);
	}
	
	public void testFragmentMeth() {

	}
}
