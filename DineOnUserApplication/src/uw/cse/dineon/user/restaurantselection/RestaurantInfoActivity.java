package uw.cse.dineon.user.restaurantselection;

import uw.cse.dineon.library.DineOnConstants;
import uw.cse.dineon.user.DineOnUserActivity;
import uw.cse.dineon.user.R;
import uw.cse.dineon.user.checkin.CheckInActivity;
import uw.cse.dineon.user.checkin.IntentIntegrator;
import uw.cse.dineon.user.checkin.QRCheckin;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class RestaurantInfoActivity extends DineOnUserActivity implements
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.basic_menu, menu);
		return true;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		QRCheckin.QRResult(requestCode, resultCode, intent);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case R.id.option_check_in:
			// Start an activity for result
			// Attempt to check in at a special
			//Intent i = new Intent(getApplicationContext(), CheckInActivity.class);
			//startActivityForResult(i, DineOnConstants.REQUEST_CHECK_IN);
			
			// Start a QR scanner activity
			IntentIntegrator integrator = new IntentIntegrator(this);
			integrator.initiateScan();
			
			break;
		default:
			// Dunno what happened here
			break;
		}
		return true;
	}
}
