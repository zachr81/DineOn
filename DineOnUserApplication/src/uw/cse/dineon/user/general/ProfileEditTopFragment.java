package uw.cse.dineon.user.general;

import uw.cse.dineon.library.UserInfo;
import uw.cse.dineon.user.R;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * 
 * @author espeo196
 *
 */
public class ProfileEditTopFragment extends Fragment {

	private ImageButton mProfileImage;
	
	/**
	 * Returns a fragment that will present the information present in the
	 * User Info object.
	 * 
	 * @param info
	 *            User Info to be prepared to present
	 * @return New image fragment.
	 */
	public static ProfileEditTopFragment newInstance(UserInfo info) {
		// Prepare a Bundle argument
		// for starting an activity with
		ProfileEditTopFragment frag = new ProfileEditTopFragment();
		Bundle args = new Bundle();
		frag.setArguments(args);
		return frag;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_profile_edit_top,
				container, false);

		mProfileImage = (ImageButton) view.findViewById(R.id.image_profile_picture);
		
		return view;
	}
	
	/**
	 * 
	 * @param b bitmap to set Profile image as
	 */
	public void setProfileImage(Bitmap b) {
		mProfileImage.setImageBitmap(b);
	}
}
