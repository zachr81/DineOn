package uw.cse.dineon.user.restaurantselection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import uw.cse.dineon.user.R;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * TODO
 * @author mhotan
 */
public class RestaurantListFragment extends ListFragment {

	private final String TAG = this.getClass().getSimpleName();

	private RestaurantListListener mListener;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// TODO make Parse queries
		// Or load from local data base
		// TODO Change String to a new data type (Restaurant)
		String[] values = new String[] { "Marty's" , "Burger King", "Some other stupid restaurant"  };
		List<String> tediousList = new ArrayList<String>();
		for (String s:values)
			tediousList.add(s);

		ArrayAdapter<String> adapter = new RestaurantListAdapter(
				this.getActivity(), tediousList);
		setListAdapter(adapter);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// Do something with the data
		// Never gets touched
//		TextView restaurantView = (TextView) v.findViewById(R.id.label_restaurant_name);
//		String restName = restaurantView.getText().toString();
//		mListener.onRestaurantSelected(restName);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof RestaurantListListener) {
			mListener = (RestaurantListListener) activity;
		} else {
			throw new ClassCastException(activity.toString()
					+ " must implemenet RestaurantListFragment.RestaurantListListener");
		}
	}

	/**
	 * TODO
	 * @author mhotan
	 */
	public interface RestaurantListListener {

		/*
		 * TODO: Place parameter preferable a "Restaurant" Instance
		 * */

		/**
		 * TODO
		 * @param restaurant
		 */
		public void onRestaurantSelected(String restaurant);

		/**
		 * TODO
		 * @param restaurant
		 */
		public void onRestaurantFocusedOn(String restaurant);

	}

	/**
	 * Class that will help display custom list view items
	 * TODO Change generic type from String to Restaurant
	 * @author mhotan
	 */
	private class RestaurantListAdapter extends ArrayAdapter<String> {

		private final Context mContext;
		private final List<String> mValues;

		/**
		 * This is a runtime mapping between "More Info buttons"
		 * and there respective restaurants
		 * TODO Change String to restaurant;
		 * NOTE (MH): Not exactly sure if this works
		 */
		private final HashMap<View, String> mMapping;

		private final View.OnClickListener mButtonListener, mItemSelectedListener;

		public RestaurantListAdapter(Context context, List<String> values) {
			super(context, R.layout.listitem_restaurant, values); // Use our custom row layout
			this.mContext = context;
			this.mValues = new ArrayList<String>(values);
			mMapping = new HashMap<View, String>();
			mButtonListener = new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// Have to handle the case where 
					// Listener is not yet instantiated
					if (mListener != null) {
						// Make sure the mapping has the right value
						String name = mMapping.get(v);
						if (name == null) {
							Log.w(TAG, "Unable to find restaurant that is attached to this view: " + v);
							return; //FAIL
						}
						
						// Potentially change the attributes of the view
						// To show focus on a particular restaurant
						
						mListener.onRestaurantFocusedOn(name);
					}
				}
			};
			
			mItemSelectedListener = new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					TextView restaurantView = (TextView) v.findViewById(R.id.label_restaurant_name);
					String restName = restaurantView.getText().toString();
					if (mListener != null)
						mListener.onRestaurantSelected(restName);
				}
			};
		}

		@Override
		public View getView(int position, View covnertView, ViewGroup parent){
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.listitem_restaurant, parent, false);

			// TODO Here is where we adjust the contents of the list row
			// with attributes determined by the restaurant
			// Now we are just setting the text to be the name of restaurant
			
			TextView restLabel = (TextView) rowView.findViewById(R.id.label_restaurant_name);
			restLabel.setOnClickListener(mItemSelectedListener);
			
			// Get the restaurant name by associating with the position
			String name = mValues.get(position);
			restLabel.setText(name);	

			// TODO Change the button to a more intuitive picture that describes "more info"
			Button moreInfoButton = (Button) rowView.findViewById(R.id.button_restaurant_info);

			// Add to the mapping so listeners can reference it later
			mMapping.put(moreInfoButton, name);

			moreInfoButton.setOnClickListener(mButtonListener);

			return rowView;
		}
	}

}