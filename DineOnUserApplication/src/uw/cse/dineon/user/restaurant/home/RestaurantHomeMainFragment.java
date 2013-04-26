package uw.cse.dineon.user.restaurant.home;

import uw.cse.dineon.user.R;
import uw.cse.dineon.user.restaurantselection.RestaurantInfoActivity;
import uw.cse.dineon.user.restaurantselection.RestaurantInfoFragment;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RestaurantHomeMainFragment extends Fragment {

	private final String TAG = this.getClass().getSimpleName();

	private ReferenceDataListener mListener;

	private static final String[] CONTENT = new String[] { 
		"Information", "Entrees", "Appetizers", "Drink Menu", "Happy Hour" };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_menu_and_info_selection,
				container, false);

		ViewPager pager = (ViewPager)view.findViewById(R.id.pager_menu_info);

		// Use activity's Fragment Manager
		RestaurantMenuCategoryAdapter adapter = 
				new RestaurantMenuCategoryAdapter(getFragmentManager());

		pager.setAdapter(adapter);

		// TODO Need to figure a process of passing the restaurant we chose
		// in the previous process and send to this containing context
		// However we cant assume the previous activity is RestaurantSelectionActivity
		// That wont always be true

		return view;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof ReferenceDataListener) {
			mListener = (ReferenceDataListener) activity;
		} else {
			throw new ClassCastException(activity.toString()
					+ " must implemenet RestaurantHomeMainFragment.ReferenceDataListener");
		}
	}

	/**
	 * Adapter class that manages the view 
	 * @author mhotan
	 *
	 */
	class RestaurantMenuCategoryAdapter extends FragmentPagerAdapter {

		public RestaurantMenuCategoryAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// Show the appropiate menu on according to position
			if (Math.min(position, CONTENT.length - 1) <= CONTENT.length - 1) {
				String menuType = CONTENT[Math.min(position, CONTENT.length - 1)];
				Fragment f = new SubMenuFragment();
				Bundle data = new Bundle();
				data.putString(SubMenuFragment.EXTRA_MENU_TYPE, menuType);
				f.setArguments(data);
				return f;
			}

			// Here the value is 0 or negative indicating the first information screen
			// TODO Figure out restaurant
			String restaurant = "FIXME: Restaurant should be here!";
			Fragment f = new RestaurantInfoFragment();
			Bundle data = new Bundle();
			data.putString(RestaurantInfoActivity.EXTRA_RESTAURANT, mListener.getCurrentRestaurant());
			f.setArguments(data);
			return f;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			position = Math.max(Math.min(position, CONTENT.length-1), 0);
			return CONTENT[position];
		}

		@Override
		public int getCount() {
			return CONTENT.length;
		}
	}
	
	public interface ReferenceDataListener {
		
		/**
		 * TODO change to Restaurant datatype
		 */
		public String getCurrentRestaurant();
		
	}

}
