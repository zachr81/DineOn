package uw.cse.dineon.restaurant.profile;

import uw.cse.dineon.restaurant.DineOnRestaurantActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ProfileActivity extends DineOnRestaurantActivity {

	
	
	/**
	 * A dummy fragment representing a section of the app
	 */
	public static class DummySectionFragment extends Fragment {
		public static final String ARG_SECTION_NUMBER = "placeholder_text";

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			TextView textView = new TextView(getActivity());
			textView.setGravity(Gravity.CENTER);
			textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
			return textView;
		}
	}
}
