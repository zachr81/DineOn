package uw.cse.dineon.restaurant.login;

import uw.cse.dineon.library.util.DevelopTools;
import uw.cse.dineon.restaurant.DineOnRestaurantActivity;
import uw.cse.dineon.restaurant.MyCustomReceiver;
import uw.cse.dineon.restaurant.R;
import uw.cse.dineon.restaurant.active.RestauarantMainActivity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.parse.ParseInstallation;
import com.parse.PushService;

public class RestaurantLoginActivity extends DineOnRestaurantActivity 
implements LoginFragment.OnLoginListener {
	private final String TAG = this.getClass().getSimpleName();
	
	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		//ParseAnalytics.trackAppOpened(this.getIntent());
		MyCustomReceiver rec = new MyCustomReceiver((TextView)this.findViewById(R.id.taskList));
		IntentFilter iff = new IntentFilter("com.parse.starter.UPDATE_STATUS");
		PushService.subscribe(this, "push", RestaurantLoginActivity.class);
		this.registerReceiver(rec, iff);
//		this.getOrders(null);
		ParseInstallation.getCurrentInstallation().saveInBackground();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		// Note that override this method does not mean the actualy 
		//  UI Menu is updated this is done manually
		//  See basic_menu under res/menu for ids
		inflater.inflate(R.menu.login_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.option_create_new_account) {
			//TODO Implement
			createNewAccount();
		} else if (id == R.id.option_forgot_password) {
			//TODO Implement
			DevelopTools.getUnimplementedDialog(this, null).show();
		}
		return true;
	}
	
	@Override
	public void onLogin(String loginCredentials) {
		// TODO Validate credentials
		Intent i = new Intent(this, RestauarantMainActivity.class);
		// Add some data about the user
		startActivity(i);
	}

	/**
	 * Attempts to create new account
	 */
	private void createNewAccount() {
		// TODO Auto-generated method stub
		DevelopTools.getUnimplementedDialog(this, null).show();
	}
	
	
//	/** Called when the user clicks the add task button */
//	public void addTask(View view) {
//		EditText taskText = (EditText) findViewById(R.id.newTaskText);
//		final String message = taskText.getText().toString();
//		
//		String user = "Restaurant";//ParseUser.getCurrentUser().getUsername();
//		
//		ParseObject testObject = new ParseObject("Order");
//		testObject.put("item", message);
//		testObject.put("uname", user);
//		
//		taskText.setText("");
//		
//		testObject.saveInBackground( new SaveCallback() {
//
//			@Override
//			public void done(ParseException e) {
//				if (e == null) {
//					// save was successful so send push
//					share(message);
//				} else {
//					// Error occured
//					Log.d("score", "Error: " + e.getMessage());
//				}
//			}
//		});
//	}
//	
//	/** Called when the user clicks the add task button */
//	public void getOrders(View view) {
//		final TextView list = (TextView) findViewById(R.id.taskList);
//		
//		ParseQuery query = new ParseQuery("Order");
//		query.findInBackground(new FindCallback() {
//		    public void done(List<ParseObject> taskList, ParseException e) {
//		        if (e == null) {
//		        	String newList = "";
//		        	for (int i = 0; i < taskList.size(); i++) {
//		        		newList += taskList.get(i).getString("uname") + " - " + taskList.get(i).getString("item") + "\n";
//		        	}
//		        	if (!newList.equals("")) list.setText(newList);
//		        } else {
//		            Log.d("score", "Error: " + e.getMessage());
//		        }
//		    }
//		});
//	}
//	
//	public void share(String msg){
//		
//		try{
//			String user = "Restaurant";//ParseUser.getCurrentUser().getUsername();
//
//			JSONObject data = new JSONObject();
//	        data.put("action", "com.parse.starter.UPDATE_STATUS");
//	        data.put("uname", user);
//	        data.put("item", msg);
//	
//			Log.d(TAG, data.toString());
//			ParsePush push = new ParsePush();
//			push.setChannel("push");
//			push.setData(data);
//			push.sendInBackground();
//		}
//		catch(Exception e){
//			Log.d(TAG, e.getMessage());
//		}
//	}
//

}
