package uw.cse.dineon.user;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uw.cse.dineon.library.DiningSession;
import uw.cse.dineon.library.Storable;
import uw.cse.dineon.library.util.DineOnConstants;
import uw.cse.dineon.library.util.ParseUtil;
import uw.cse.dineon.user.general.ProfileActivity;
import uw.cse.dineon.user.general.UserPreferencesActivity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Generl Fragment Activity class that pertains to a specific user.
 * Once the User is logged in then they are allowed specific access to 
 * different regions of the application.
 * 
 * <b><b>In Particular their user specific preferences
 * @author mhotan
 */
public class DineOnUserActivity extends FragmentActivity {

	private static final String TAG = DineOnUserActivity.class.getSimpleName();

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		// Note that override this method does not mean the actualy 
		//  UI Menu is updated this is done manually
		//  See basic_menu under res/menu for ids
		inflater.inflate(R.menu.basic_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent i = null;
		switch (item.getItemId()) {
		case R.id.option_profile:
			i = new Intent(getApplicationContext(), ProfileActivity.class);
			break;
		case R.id.option_settings:
			i = new Intent(getApplicationContext(), UserPreferencesActivity.class);
			break;
		}
		if (i != null)
			startActivity(i);
		return true;
	}

	/**
	 * This is the defined call back method for when the 
	 * User attempts to check in to a restaurant.  Once the check
	 * @param session
	 */
	public void onCheckInCallback(Map<String, String> session){
		try {
			// Extract the object ID from the return map
			String objID = session.get(DineOnConstants.OBJ_ID);
			
			// Use Utility to call Parse and get the Dining Session instance
			if (objID == null || objID.isEmpty()){
				// TODO Update the UI
				// Handle the fail case where no dining session 
				// was created
			}			
			
			List<Storable> list = new ArrayList<Storable>();
			// Then Bundle the Dining Session Instance into 
			Method m = null;

			m = DineOnUserActivity.class.getMethod("onDiningSessionRecievedCallback",
					list.getClass());

			Map<String, String> attr = new HashMap<String, String>();
			attr.put(DineOnConstants.OBJ_ID, objID);

			ParseUtil.getDataFromCloud(DiningSession.class, m, attr);
		} catch (NoSuchMethodException e) {
			Log.e(TAG, "Failed to invocate method onDiningSessionRecievedCallback()");
		}

	}
	
	/**
	 * 
	 * @param list
	 */
	public void onDiningSessionRecievedCallback(List<Storable> list){
		// Assert that the first item in the list is
		// is a DiningSession
		if (list != null && list.size() == 1){
			throw new IllegalArgumentException("List returned is not valid: " + list);
		}
		DiningSession currentSession = (DiningSession)list.get(0);
		// TODO Extract channel for push
		// TODO Extract object id for restaurant

		// Bundle up dining session
		// Start RestaurantMainActivity with bundle 

	}
}
