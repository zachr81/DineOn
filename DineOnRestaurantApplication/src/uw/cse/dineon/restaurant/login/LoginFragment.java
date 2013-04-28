package uw.cse.dineon.restaurant.login;

import uw.cse.dineon.restaurant.R;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class LoginFragment extends Fragment implements OnClickListener {

	private OnLoginListener mListener;
	
	private EditText mEmailInput, mPasswordInput;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_login,
				container, false);
		Button button = (Button) view.findViewById(R.id.button_login);
		button.setOnClickListener(this);
		
		mEmailInput = (EditText) view.findViewById(R.id.input_restaurant_login_name);		
		mPasswordInput = (EditText) view.findViewById(R.id.input_password);
		return view;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof OnLoginListener) {
			mListener = (OnLoginListener) activity;
		} else {
			throw new ClassCastException(activity.toString()
					+ " must implemenet " + this.getClass().getSimpleName() + ".OnLoginListener");
		}
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
		
		/**
		 * Login with certain credentials
		 * @param loginCredentials TODO 
		 */
		public void onLogin(String loginCredentials);
	}

	@Override
	public void onClick(View v) {
		String email = mEmailInput.getText().toString();
		String pw = mPasswordInput.getText().toString();
		
		// Combine into a valid login credential instance
		mListener.onLogin(email + ":" + pw);
		
	}

}
