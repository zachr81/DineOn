package uw.cse.dineon.user.general;

import uw.cse.dineon.library.UserInfo;
import uw.cse.dineon.user.R;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @author mhotan
 */
public class ProfileFragment extends Fragment {

	private ImageView mProfileImage;
	private TextView mProfileName;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_profile,
				container, false);
		
		mProfileImage = (ImageView) view.findViewById(R.id.image_profile_picture);
		mProfileName = (TextView) view.findViewById(R.id.label_profile_name);
		
		return view;
	}
	
	/**
	 * @param info UserInfo of current profile
	 * @return a new ProfileFragment
	 */
	public static ProfileFragment newInstance(UserInfo info) {
		// Prepare a Bundle argument
		// for starting an activity with
		ProfileFragment frag = new ProfileFragment();
		Bundle args = new Bundle();
		frag.setArguments(args);
		return frag;
	}
	
	/**
	 * 
	 * @param b bitmap to set Profile image as
	 */
	public void setProfileImage(Bitmap b) {
		// TODO Narrow down size
		mProfileImage.setImageBitmap(b);
	}

	/**
	 * Sets profile name.
	 * @param n String to set
	 */
	public void mProfileName(String n) {
		mProfileName.setText(n);
	}
	
	
}
