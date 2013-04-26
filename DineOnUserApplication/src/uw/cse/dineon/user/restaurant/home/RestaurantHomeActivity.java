package uw.cse.dineon.user.restaurant.home;

import uw.cse.dineon.user.R;
import uw.cse.dineon.user.restaurantselection.RestaurantInfoFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

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

	// This activity has the capabilties of handling this

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
