package uw.cse.dineon.user.restaurantselection;

import uw.cse.dineon.user.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RestaurantInfoFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_restaurant_info,
				container, false);
		return view;
	}

	public void setRestaurantForDisplay(/*TODO Change to Restaurant data type*/
			String restaurantName) {
		TextView view = (TextView) getView().findViewById(R.id.label_restaurant_info);
		view.setText("Restaurant Name: " + restaurantName);
	}
}
