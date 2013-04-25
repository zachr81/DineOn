

package uw.cse.dineon.user.restaurantselection;

import uw.cse.dineon.user.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

/**
 * TODO
 * @author mhotan
 */
public class RestaurantSelectionActivity extends Activity implements 
RestaurantSelectionButtonsFragment.OnClickListener, // Listening for button actions
RestaurantListFragment.RestaurantListListener //  Listening for List items
{

	public static final String EXTRA_USER = "USER";
	
	private String /*Change to ParseUser*/ mUser;
	
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
		Toast.makeText(this, "Restaurant \"" + restaurant + "\" Selected", 
				Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public void onRestaurantFocusedOn(String restaurant) {
		// TODO Auto-generated method stub
		RestaurantInfoFragment frag = (RestaurantInfoFragment) getFragmentManager()
				.findFragmentById(R.id.restaurantInfo);
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

	
	
	
}
