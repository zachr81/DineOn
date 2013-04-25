package uw.cse.dineon.user.restaurantselection;

import uw.cse.dineon.user.R;
import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

public class RestaurantInfoActivity extends Activity{

	private final String TAG = this.getClass().getSimpleName();

	/**
	 * Extra used as key for passing values to 
	 */
	public static final String EXTRA_RESTAURANT = "RESTAURANT";

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
			String restaurantName = extras.getString(EXTRA_RESTAURANT);
			if (restaurantName == null) { // Improper call of activity check
				Log.e(TAG, "Null restaurant name found when extrating bundle");
				return;
			}
			
			RestaurantInfoFragment frag = (RestaurantInfoFragment) getFragmentManager()
					.findFragmentById(R.id.restaurantInfo);
			frag.setRestaurantForDisplay(restaurantName);

		}

	}
}
