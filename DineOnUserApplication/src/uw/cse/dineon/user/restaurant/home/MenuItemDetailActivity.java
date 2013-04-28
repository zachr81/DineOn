package uw.cse.dineon.user.restaurant.home;

import uw.cse.dineon.library.DineOnConstants;
import uw.cse.dineon.user.DineOnUserActivity;
import uw.cse.dineon.user.R;
import uw.cse.dineon.user.bill.CurrentOrderActivity;
import uw.cse.dineon.user.checkin.CheckInActivity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class MenuItemDetailActivity extends DineOnUserActivity {

	public static final String EXTRA_MENUITEM = "menuitem";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Need to check if Activity has been switched to landscape mode
		// If yes, finished and go back to the start Activity
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			finish();
			return;
		}
		setContentView(R.layout.activity_menuitem_detail);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			String s = extras.getString(EXTRA_MENUITEM);
			TextView view = (TextView) findViewById(R.id.label_menuitem_details);
			view.setText(s);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.basic_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		Intent i;
		switch (item.getItemId()) {
		case R.id.option_view_order:
			i = new Intent(getApplicationContext(), CurrentOrderActivity.class);
			// Count all the elements that the user has currently selected
			startActivityForResult(i, DineOnConstants.REQUEST_VIEW_CURRENT_ORDER);
			break;
		case R.id.option_check_in:
			// Start an activity for result
			// Attempt to check in at a special
			i = new Intent(getApplicationContext(), CheckInActivity.class);
			startActivityForResult(i, DineOnConstants.REQUEST_CHECK_IN);
			break;
		default:
			// Dunno what happened here
			break;
		}
		return true;
	}

}
