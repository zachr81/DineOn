package uw.cse.dineon.user.restaurant.home;

import uw.cse.dineon.library.DineOnConstants;
import uw.cse.dineon.user.R;
import uw.cse.dineon.user.checkin.CheckInActivity;
import uw.cse.dineon.user.restaurantselection.RestaurantInfoFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * TODO
 * @author mhotan
 */
public class RestaurantHomeActivity extends FragmentActivity
// Implement all the fragments callbacks
implements SubMenuFragment.MenuItemListListener, 
RestaurantInfoFragment.RestaurantInfoListener,
RestaurantHomeMainFragment.ReferenceDataListener
{

	private final String TAG = this.getClass().getSimpleName();

	public final static String EXTRA_RESTAURANT = "restaurant"; 
	
	private String /*Change to restaurant datatype*/ mRestaurant;

	//////////////////////////////////////////////////////////////////////
	////  Android specific 
	//////////////////////////////////////////////////////////////////////

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Attempt to get the restaurant name
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			mRestaurant = extras.getString(EXTRA_RESTAURANT);
		}

		setContentView(R.layout.activity_restaurant_home);

		// TODO FIX: Extract the value of the restaurant
		// Pull out all its menus and information

		// Then Replace fragments as appropiately
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.restaurant_home_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.option_view_order:
			
			break;
		case R.id.option_check_in:
			// Start an activity for result
			// Attempt to check in at a special
			Intent i = new Intent(getApplicationContext(), CheckInActivity.class);
			startActivityForResult(i, DineOnConstants.REQUEST_CHECK_IN);
			break;
		case R.id.option_profile:

			break;
		case R.id.option_settings:

			break;
		case R.id.option_filter:

			break;
		default:
			// Dunno what happened here
			break;
		}
		return true;
	}
	
	@Override
	protected void onActivityResult (int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode != RESULT_OK) {
			// TODO REmove Log for release
			Log.w(TAG, "Check in Failed or Cancelled");
			return;
		}
		
		if (requestCode == DineOnConstants.REQUEST_CHECK_IN) {
			// Register this user with this restaurant
		}
		
	}

	//////////////////////////////////////////////////////////////////////
	////   Call backs for Fragment methods
	//////////////////////////////////////////////////////////////////////


	@Override
	public void onMakeReservation(String reservation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMenuItemFocusedOn(String menuItem) {
		// Attempt to find if the fragment exists
		// If it does update its contents
		// TODO Implement the ability to switch inbetween fragments
		MenuItemDetailFragment frag = (MenuItemDetailFragment) 
				getSupportFragmentManager().findFragmentById(R.id.fragment_menu_info_detail);
		if (frag != null && frag.isInLayout()) {
			frag.setMenuItem(menuItem);
		} else {
			// If it doesn't start a new activity 
			Intent i = new Intent(getApplicationContext(), MenuItemDetailActivity.class);
			i.putExtra(MenuItemDetailActivity.EXTRA_MENUITEM, menuItem);
			startActivity(i);
		}
	}

	@Override
	public void onRestaurantInfoRequested() {
		// TODO Auto-generated method stub
		// TODO Replace MenuItemDetailFragment with Restaurant Info Fragment
	}


	@Override
	public void onMenuItemIncremented(String menuItem) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMenuItemDecremented(String menuItem) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onViewCurrentBill() {
		// TODO Auto-generated method stub
		// TODO Take to Current Bill screen
	}

	@Override
	public String getCurrentRestaurant() {
		// TODO Auto-generated method stub
		return mRestaurant;
	}




}
