package uw.cse.dineon.user.login;

import uw.cse.dineon.user.R;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Fragment 
 * @author mhotan
 *
 */
public class CreateNewAccountFragment extends Fragment {

	private onCreateNewAccountListener mListener;

	// Input from users
	private EditText email_input, password_input, password_repeat_input;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_create_new_account,
				container, false);

		email_input = (EditText) view.findViewById(R.id.input_createnewaccount_email);
		password_input = (EditText) view.findViewById(R.id.input_createnewaccount_password);
		password_repeat_input = (EditText) view.findViewById(R.id.input_createnewaccount_repeat_password);

		Button createAccountButton = (Button) view.findViewById(R.id.button_create_account);
		createAccountButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				createAccout();
			}
		});
		
		Button facebookButton = (Button) view.findViewById(R.id.button_createnewaccount_facebook);
		facebookButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Fix
				mListener.onLoginThirdParty("Facebook");
			}
		});
		
		Button twitterButton = (Button) view.findViewById(R.id.button_createnewaccount_twitter);
		twitterButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Fix
				mListener.onLoginThirdParty("Twitter");
			}
		});

		return view;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof onCreateNewAccountListener) {
			mListener = (onCreateNewAccountListener) activity;
		} else {
			throw new ClassCastException(activity.toString()
					+ " must implemenet MyListFragment.OnItemSelectedListener");
		}
	}
	
	private void createAccout(){
		String email = email_input.getText().toString();
		String password = password_input.getText().toString();
		String password_repeat = password_repeat_input.getText().toString();
		
		// TODO Check valid email
		// TODO Check password
		mListener.onCreateNewAccount(email, password);
	}

	/**
	 * TODO
	 * @author mhotan
	 */
	public interface onCreateNewAccountListener {

		public void onCreateNewAccount(String email, String password);

		public void onLoginThirdParty(String loginCredentials);
	}

}
