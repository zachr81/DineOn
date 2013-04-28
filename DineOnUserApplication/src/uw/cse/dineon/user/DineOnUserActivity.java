package uw.cse.dineon.user;

import uw.cse.dineon.user.general.ProfileActivity;
import uw.cse.dineon.user.general.UserPreferencesActivity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
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


}
