package uw.cse.dineon.user.login;

import uw.cse.dineon.user.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class CreateNewAccountActivity extends Activity 
implements CreateNewAccountFragment.onCreateNewAccountListener {

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

}
