package uw.cse.dineon.user.login;

import java.lang.reflect.Method;

import uw.cse.dineon.library.util.ParseUtil;
import uw.cse.dineon.user.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.parse.ParseUser;

public class CreateNewAccountActivity extends FragmentActivity 
implements CreateNewAccountFragment.onCreateNewAccountListener {

	public static final String TAG = CreateNewAccountActivity.class.getSimpleName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_new_account);

		// TODO Complete Initialization
	}

	@Override
	public void onCreateNewAccount(String email, String password) {
		// TODO Auto-generated method stub
		// Attempt to create a new 
		try {
			Method m = this.getClass().getMethod("createNewAccountCallback", Boolean.class);
			ParseUtil.createDineOnUser(email, password, m);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finish();
	}

	@Override
	public void onLoginThirdParty(String loginCredentials) {
		// TODO Auto-generated method stub
		finish();
	}

	@Override
	public void finish() {
		
		// TODO Pass back the correct values
		
		// Prepare data intent 
		Intent data = new Intent();
		data.putExtra(UserLoginActivity.RETURN_CODE_LOGIN_CREDENTIALS, "JohDoe@gmail.com:password");
		data.putExtra(UserLoginActivity.RETURN_CODE_LOGIN_3RDPARTY, "facebook");
		// Activity finished ok, return the data
		setResult(RESULT_OK, data);
		super.finish();
	} 

	public static void createNewAccountCallback(Boolean success){
		if (success) {
			ParseUser me = ParseUser.getCurrentUser();
			Log.v(TAG, "NEW ACCOUNT CREATED! : " + me.getUsername());
			
			// Start Restaurant selection
			
		} else 
			Log.v(TAG, "Create new account failed");
	}
	
}
