

package uw.cse.dineon.user.restaurantselection;

import uw.cse.dineon.user.R;
import uw.cse.dineon.user.restaurant.home.RestaurantHomeActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

/**
 * TODO
 * @author mhotan
 */
public class RestaurantSelectionActivity extends FragmentActivity implements 
RestaurantSelectionButtonsFragment.OnClickListener, // Listening for button actions
RestaurantListFragment.RestaurantListListener, //  Listening for List items
RestaurantInfoFragment.RestaurantInfoListener
{

	public static final String EXTRA_USER = "USER";
	
	private String /*Change to ParseUser*/ mUser;
	
	private String mCurRestaurant;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_restaurant_selection);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			mUser = extras.getString(EXTRA_USER);
			Toast.makeText(this, "User: " + mUser + " logged in!", Toast.LENGTH_SHORT).show();
		}
	}
	
	// Method inherited from Restaurant list listener 
	// for use when user selects a restaurant to focus on
	@Override
	public void onRestaurantSelected(String restaurant) {
		// TODO Auto-generated method stub
		// Continue on to next activity
		mCurRestaurant = restaurant;
		Toast.makeText(this, "Restaurant \"" + restaurant + "\" Selected", 
				Toast.LENGTH_SHORT).show();
		
		Intent i = new Intent(getApplicationContext(), RestaurantHomeActivity.class);	
		i.putExtra(RestaurantHomeActivity.EXTRA_RESTAURANT, mCurRestaurant);
		startActivity(i);
	}
	
	@Override
	public void onRestaurantFocusedOn(String restaurant) {
		// TODO Auto-generated method stub
		mCurRestaurant = restaurant;
		FragmentManager fm = getSupportFragmentManager();
		RestaurantInfoFragment frag = (RestaurantInfoFragment) fm.findFragmentById(R.id.restaurantInfo);
		// If the fragment already exists then just update its value
		if (frag != null && frag.isInLayout()){
			frag.setRestaurantForDisplay(restaurant);
		} else {
			Intent i = new Intent(getApplicationContext(), RestaurantInfoActivity.class);	
			i.putExtra(RestaurantInfoActivity.EXTRA_RESTAURANT, restaurant);
			startActivity(i);
		}
	}
	

	@Override
	public void onShowNearbyRestaurants() {
		// TODO Implement by communicating with the list fragment 
	}

	@Override
	public void onShowFriendsFavoriteRestaurants() {
		// TODO Implement by communicating with the list fragment 
		
	}

	@Override
	public void onShowUserFavorites() {
		// TODO Implement by communicating with the list fragment 
		
	}

	@Override
	public void onMakeReservation(String reservation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getCurrentRestaurant() {
		// TODO Auto-generated method stub
		return mCurRestaurant;
	}

	
	
	
}
