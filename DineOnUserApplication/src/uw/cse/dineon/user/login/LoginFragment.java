package uw.cse.dineon.user.login;

import uw.cse.dineon.user.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * TODO
 * @author mhotan
 */
public class LoginFragment extends android.support.v4.app.Fragment {

	/**
	 * TODO
	 */
	private OnLoginListener mListener;

	private EditText email_input, password_input;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_login,
				container, false);

		// Associate the values of the input text boxes
		email_input = (EditText) view.findViewById(R.id.input_login_email);
		password_input = (EditText) view.findViewById(R.id.input_login_password);

		// Assign login listener
		Button loginButton = (Button) view.findViewById(R.id.button_login);
		loginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String email = email_input.getText().toString();
				String password = password_input.getText().toString();
				attemptLogin(email, password);
			}
		});
		Button facebookLoginButton = (Button) view.findViewById(R.id.button_facebook_login);
		facebookLoginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Implement accessing facebook credentials
				String facebookCred = "Facebook Credentials";
				attemptLogin(facebookCred);
			}
		});
		Button twitterLoginButton = (Button) view.findViewById(R.id.button_twitter_login);
		twitterLoginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Implement accessing facebook credentials
				String twitterCred = "Twitter Credentials";
				attemptLogin(twitterCred);
			}
		});
//		Button createNewAccountButton = (Button) view.findViewById(R.id.button_create_new_account);
//		createNewAccountButton.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				createNewAccount();
//			}
//		});
//		Button forgotPasswordButton = (Button) view.findViewById(R.id.button_forgot_password);
//		forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				forgotPassword(email_input.getText().toString());
//			}
//
//		});
		return view;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof OnLoginListener) {
			mListener = (OnLoginListener) activity;
		} else {
			throw new ClassCastException(activity.toString()
					+ " must implemenet LoginFragment.OnLoginListener");
		}
	}
	
	/**
	 * Create new account
	 */
	private void createNewAccount() {
		// TODO Auto-generated method stub
		mListener.onCreateNewAccount();
	}
	
	/**
	 * @return The email address that was forgotten
	 */
	public String getCurrentEmail(){
		return email_input.getText().toString();
	}

	/**
	 *TODO further define
	 * Attempt to login through facebook somehow
	 * @param facebookCred
	 */
	private void attemptLogin(String facebookOrTwitterCredentials) {
		// TODO Fix instead of string to other
		mListener.onLogin(facebookOrTwitterCredentials);
	}

	/**
	 * Attempt to login that can be accessed by external activities
	 * to login
	 * @param email email of user
	 * @param password password of user
	 */
	public void attemptLogin(String email, String password) {
		// TODO Translate email and password to a single login credential
		mListener.onLogin(email);
	}

	/**
	 * Listener that is intended to allow any owning context of an 
	 * LoginFragment instance to react and communicate Login related request
	 * All activities that uses a LoginFragment MUST implement this listener
	 *   
	 * @author mhotan
	 */
	public interface OnLoginListener {

		// This is how login listener communicates back to the activity
		
		// TODO Perhaps create a login credential class and pass that back to the activity
		public void onLogin(String loginCredentials);

		public void onCreateNewAccount();
	}

}
