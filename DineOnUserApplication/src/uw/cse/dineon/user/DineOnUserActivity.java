package uw.cse.dineon.user;

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
		switch (item.getItemId()) {
		case R.id.option_profile:

			break;
		case R.id.option_settings:

			break;
		case R.id.option_filter:

			break;
		}
		return true;
	}


}
