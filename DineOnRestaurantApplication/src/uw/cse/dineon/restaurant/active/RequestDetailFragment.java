package uw.cse.dineon.restaurant.active;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uw.cse.dineon.library.CustomerRequest;
import uw.cse.dineon.library.UserInfo;
import uw.cse.dineon.restaurant.R;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Shows the details of a customer request.
 * @author mhotan
 */
public class RequestDetailFragment extends Fragment 
implements OnCheckedChangeListener, OnClickListener {

	private static final String TAG = RequestDetailFragment.class.getSimpleName();
	
	private TextView mTitle, mDetails, mTimeTaken;
	private ArrayAdapter<String> mStaffAdapter;
	private Map<RadioButton, String> mUrgencyMap;
	private ImageButton mSendMessage, mSendTask;
	private EditText mMessageBlock;
	private Spinner mStaffList;

	private RequestDetailListener mListener;
	private String mUrgency;

	private CustomerRequest mRequest;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mRequest = mListener.getRequest();
		updateState();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_request_detail,
				container, false);
		mRequest = null;

		mTitle = (TextView) view.findViewById(R.id.label_request_title_detail);
		mDetails = (TextView) view.findViewById(R.id.label_request_details);
		mTimeTaken = (TextView) view.findViewById(R.id.label_request_time);

		// TODO Add staff members implementation
		String[] employees = getActivity().getResources().
				getStringArray(R.array.default_employees);
		List<String> staff = new ArrayList<String>();
		for (String e: employees) {
			staff.add(e);
		}
		mStaffAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, staff);
		mStaffList = (Spinner) view.findViewById(R.id.spinner_staff);
		mStaffList.setAdapter(mStaffAdapter);

		// For all the radio buttons
		// Assign a string value to send back to user as a urgency level
		// Then add a listener for changes in state
		RadioButton normal = (RadioButton) view.findViewById(R.id.radio_urgency_normal);
		RadioButton important = (RadioButton) view.findViewById(R.id.radio_urgency_important);
		RadioButton priority = (RadioButton) view.findViewById(R.id.radio_urgency_priority);
		mUrgencyMap = new HashMap<RadioButton, String>();
		mUrgencyMap.put(normal, getString(R.string.normal));
		mUrgencyMap.put(important, getString(R.string.important));
		mUrgencyMap.put(priority, getString(R.string.priority));
		mUrgency = mUrgencyMap.get(normal);	
		normal.setOnCheckedChangeListener(this);
		important.setOnCheckedChangeListener(this);
		priority.setOnCheckedChangeListener(this);

		mMessageBlock = (EditText) view.findViewById(R.id.edittext_send_message_request);

		// For every button
		// Set listener to do fragment-> activity call backs
		mSendMessage = (ImageButton) view.findViewById(R.id.button_send_message);
		mSendTask = (ImageButton) view.findViewById(R.id.button_send_to_staff);

		mSendMessage.setOnClickListener(this);
		mSendTask.setOnClickListener(this);

		return view;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof RequestDetailListener) {
			mListener = (RequestDetailListener) activity;
		} else {
			throw new ClassCastException(activity.toString()
					+ " must implemenet RequestDetailFragment.RequestDetailListener");
		}
	}

	//////////////////////////////////////////////////////
	//// Following are public setters.  That Activities can use
	//// to set the values of what is showed to the user for this 
	//// fragment
	//////////////////////////////////////////////////////

	/**
	 * Sets the state of this fragment to this request.
	 * @param request request to update the fragment to
	 */
	public void setRequest(CustomerRequest request) {
		if (request == null) {
			Log.e(TAG, "Attempted to show a null request!");
			return;
		}
		
		mRequest = request;
		updateState();
	}

	/**
	 * Updates the state of the view pending the whether there is a request.
	 */
	private void updateState() {
		if (mRequest == null) { // No valid request
			mSendMessage.setEnabled(false);
			mSendTask.setEnabled(false);
		} else { // Valid Request
			mSendMessage.setEnabled(true);
			mSendTask.setEnabled(true);
			mMessageBlock.setHint(getString(R.string.quick_response));
			mTitle.setText(getString(R.string.request_from) + mRequest.getUserInfo().getName());
			mDetails.setText(getString(R.string.message) + mRequest.getDescription());
			mTimeTaken.setText(mRequest.getOriginatingTime().toString());
		}
	}

	//////////////////////////////////////////////////////
	//// Following is the interface in which activities
	//// that wish to attach this Fragment must implement
	//// Intended to use for user input
	//////////////////////////////////////////////////////

	/**
	 * Listener for this fragment.
	 * @author mhotan
	 */
	public interface RequestDetailListener {

		/**
		 * @param request Request to reference
		 * @param staff staff to assign to
		 * @param urgency Urgency to accomplish task
		 */
		public void onSendTaskToStaff(CustomerRequest request, String staff, String urgency);

		/**
		 * Call back that shows that the user wishes to send a message 
		 * to the customer pertaining that specific orders.
		 * @param user User to send message to
		 * @param message Message to send for this order
		 */
		public void sendShoutOut(UserInfo user, String message);
		
		/**
		 * This method allows the fragment to get the current customer request
		 * to focus its display on. 
		 * 
		 * @return The Customer request to present.
		 */
		public CustomerRequest getRequest();

	}

	@Override
	public void onCheckedChanged(CompoundButton button, boolean isChecked) {
		if (isChecked) {
			mUrgency = mUrgencyMap.get(button);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_send_message:
			mListener.sendShoutOut(mRequest.getUserInfo(), mMessageBlock.getText().toString());
			break;
		case R.id.button_send_to_staff:
			String staffMember = mStaffList.getSelectedItem().toString();
			mListener.onSendTaskToStaff(mRequest, staffMember, mUrgency);
		default:
			Log.wtf(TAG, "RequestDetailListener weird id requested: " + v.getId());
			break;
		}
	}
}
