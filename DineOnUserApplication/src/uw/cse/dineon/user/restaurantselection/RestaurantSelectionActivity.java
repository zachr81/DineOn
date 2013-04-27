

package uw.cse.dineon.user.restaurantselection;

import uw.cse.dineon.library.DineOnConstants;
import uw.cse.dineon.user.DineOnUserActivity;
import uw.cse.dineon.user.R;
import uw.cse.dineon.user.checkin.CheckInActivity;
import uw.cse.dineon.user.restaurant.home.RestaurantHomeActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * TODO
 * @author mhotan
 */
public class RestaurantSelectionActivity extends DineOnUserActivity implements 
RestaurantSelectionButtonsFragment.OnClickListener, // Listening for button actions
RestaurantListFragment.RestaurantListListener, //  Listening for List items
RestaurantInfoFragment.RestaurantInfoListener
{
	
	private final String TAG = this.getClass().getSimpleName();

	public static final String EXTRA_USER = "USER";

	private String /*Change to ParseUser*/ mUser;

	private String mCurRestaurant;

	//////////////////////////////////////////////////////////////////////
	////  Android specific 
	//////////////////////////////////////////////////////////////////////

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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.restaurant_selection_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case R.id.option_check_in:
			// Start an activity for result
			// Attempt to check in at a special
			Intent i = new Intent(getApplicationContext(), CheckInActivity.class);
			startActivityForResult(i, DineOnConstants.REQUEST_CHECK_IN);
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
