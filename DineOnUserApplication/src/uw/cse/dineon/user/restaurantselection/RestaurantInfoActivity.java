package uw.cse.dineon.user.restaurantselection;

import uw.cse.dineon.user.R;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class RestaurantInfoActivity extends FragmentActivity implements
RestaurantInfoFragment.RestaurantInfoListener
{

	private final String TAG = this.getClass().getSimpleName();

	/**
	 * Extra used as key for passing values to 
	 */
	public static final String EXTRA_RESTAURANT = "RESTAURANT";

	private String mRestaurant;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Need to check if Activity has been switched to landscape mode
		// If yes, finished and go back to the start Activity
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			finish();
			return;
		}
		setContentView(R.layout.activity_restaurant_info);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			mRestaurant = extras.getString(EXTRA_RESTAURANT);
			if (mRestaurant == null) { // Improper call of activity check
				Log.e(TAG, "Null restaurant name found when extrating bundle");
				return;
			}
			
			RestaurantInfoFragment frag = (RestaurantInfoFragment) getSupportFragmentManager()
					.findFragmentById(R.id.restaurantInfo);
			frag.setRestaurantForDisplay(mRestaurant);

		}

	}

	@Override
	public void onMakeReservation(String reservation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getCurrentRestaurant() {
		// TODO Auto-generated method stub
		return mRestaurant;
	}
}
