package uw.cse.dineon.user.general;

import uw.cse.dineon.library.UserInfo;
import uw.cse.dineon.user.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 
 * @author espeo196
 *
 */
public class ProfileEditFieldsFragment extends Fragment {
	
	/**
	 * Returns a fragment that will present the information present in the
	 * User Info object.
	 * 
	 * @param info
	 *            User Info to be prepared to present
	 * @return New image fragment.
	 */
	public static ProfileEditFieldsFragment newInstance(UserInfo info) {
		// Prepare a Bundle argument
		// for starting an activity with
		ProfileEditFieldsFragment frag = new ProfileEditFieldsFragment();
		Bundle args = new Bundle();
		frag.setArguments(args);
		return frag;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_profile_edit_fields,
				container, false);
		
		return view;
	}
}
