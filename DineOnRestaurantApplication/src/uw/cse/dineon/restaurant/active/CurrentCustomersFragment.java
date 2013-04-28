package uw.cse.dineon.restaurant.active;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;

/**
 * For displaying current restaurant customers
 * TODO Improve and comlete
 * @author mhotan
 */
public class CurrentCustomersFragment extends ListFragment {

	/**
	 * For Logging
	 */
	private final String TAG = this.getClass().getSimpleName();

	private CustomerListener mListener;

	private static final String KEY_LIST = "MY LIST";

	//TODO change string to User
	private ArrayAdapter<String> mAdapter;

	/**
	 * Creates a new customer list fragment
	 * @param customers TODO Change to user class
	 * @return new fragment
	 */
	public static CurrentCustomersFragment newInstance(List<String> customers){
		CurrentCustomersFragment frag = new CurrentCustomersFragment();
		ArrayList<String> mList = new ArrayList<String>();
		if (customers != null) 
			mList.addAll(customers);

		Bundle args = new Bundle();
		args.putStringArrayList(KEY_LIST, mList);
		frag.setArguments(args);
		return frag;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		List<String> mCustomers = getArguments() != null ? getArguments().getStringArrayList(KEY_LIST) : null;
		if (mCustomers == null){
			if (mListener != null)
				mCustomers = mListener.getCurrentUsers();
			else
				mCustomers = new ArrayList<String>(); // Empty
		}

		//TODO Create custom adapter to handle custom layoutss
		mAdapter = new ArrayAdapter<String>(getActivity(), 
				android.R.layout.simple_list_item_1, mCustomers);
		setListAdapter(mAdapter);	
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
//	 TODO Check Functionality before deleting
//		List<String> users = mListener.getCurrentUsers();
//
//		mAdapter = new ArrayAdapter<String>(getActivity(), 
//				android.R.layout.simple_list_item_1, mCustomers);
//		setListAdapter(mAdapter);	
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof CustomerListener) {
			mListener = (CustomerListener) activity;
		} else {
			throw new ClassCastException(activity.toString()
					+ " must implemenet CurrentCustomersFragment.CustomerListener");
		}
	}

	public void addCustomer(String customer) {
		if (mAdapter != null) {
			mAdapter.add(customer);
		}
	}

	public void removeCustomer(String customer){
		if (mAdapter != null) {
			mAdapter.remove(customer);
		}
	}

	/**
	 * Mandatory interface for this fragment
	 * @author mhotan
	 */
	public interface CustomerListener {

		/**
		 * Retrieves the current user 
		 * TODO Change to type User
		 * @return
		 */
		public List<String> getCurrentUsers();

		/**
		 * TODO Add more methods here as needed
		 * To complete the full functionality of this fragment
		 * These methods will probably be methods that signify the 
		 * user wants do conduct a certain action for this user
		 * 
		 * IE: Send the user a message
		 */
	}
}
