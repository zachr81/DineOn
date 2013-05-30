package uw.cse.dineon.restaurant.active;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import uw.cse.dineon.library.Order;
import uw.cse.dineon.library.RestaurantInfo;
import uw.cse.dineon.library.animation.ExpandAnimation;
import uw.cse.dineon.library.image.DineOnImage;
import uw.cse.dineon.library.image.ImageObtainable;
import uw.cse.dineon.library.image.ImageCache.ImageGetCallback;
import uw.cse.dineon.restaurant.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import edu.umd.cs.findbugs.annotations.SuppressWarnings;

/**
 * Fragment represents a List of Orders that are pending
 * for this restaurant.
 * @author mhotan
 */
public class OrderListFragment extends ListFragment {

	private static final String TAG = OrderListFragment.class.getSimpleName();

	/**
	 * Listener for fragment interactions.
	 */
	private OrderItemListener mListener;

	/**
	 * Adapter that controls the income of Orders.
	 */
	private OrderListAdapter mAdapter;

	private static final String ORDERS = "orders"; 

	/**
	 * Creates a new Order List Fragment from a list of orders.
	 * @param orders Orders to use
	 * @return an order list fragment.
	 */
	public static OrderListFragment newInstance(List<Order> orders) {
		OrderListFragment f = new OrderListFragment();
		Bundle args = new Bundle();

		Order[] orderArgs;
		if (orders != null) {
			orderArgs = new Order[orders.size()];
			for (int i = 0; i < orders.size(); i++) {
				orderArgs[i] = orders.get(i);
			}
		} else {
			orderArgs = new Order[0];
		}

		args.putParcelableArray(ORDERS, orderArgs);
		f.setArguments(args);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Order[] orderArray = null;
		if (savedInstanceState != null // From saved instance
				&& savedInstanceState.containsKey(ORDERS)) {
			orderArray = (Order[])savedInstanceState.getParcelableArray(ORDERS);
		} else if (getArguments() != null && getArguments().containsKey(ORDERS)) {
			// Ugh have to convert to array for type reasons.
			// List are not contravariant in java... :-(
			orderArray = (Order[])getArguments().getParcelableArray(ORDERS);	
		}

		// Error check
		if (orderArray == null) {
			Log.e(TAG, "Unable to extract list of orders");
			return;
		}

		// Must convert to array because this allows dynamic additions
		// Our Adapter needs a dynamic list.
		List<Order> orders = new ArrayList<Order>(orderArray.length);
		for (Order order : orderArray) {
			orders.add(order);
		}

		mAdapter = new OrderListAdapter(this.getActivity(), orders);
		setListAdapter(mAdapter);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		List<Order> orders = mListener.getPendingOrders();
		Order[] orderArray = new Order[orders.size()];
		for (int i = 0; i < orderArray.length; ++i) {
			orderArray[i] = orders.get(i);
		}
		outState.putParcelableArray(ORDERS, orderArray);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof OrderItemListener) {
			mListener = (OrderItemListener) activity;
		} else {
			throw new ClassCastException(activity.toString()
					+ " must implement OrderListFragment.OrderItemListener");
		}
	}

	//////////////////////////////////////////////////////
	//// Following are public setters.  That Activities Or parent Fragments can use
	//// to set the values of what is showed to the user for this 
	//// fragment TODO Change string to Order
	//////////////////////////////////////////////////////

	/**
	 * Adds order to this view.
	 * @param order String
	 */
	public void addOrder(Order order) {
		mAdapter.add(order);
		mAdapter.notifyDataSetChanged();
	}

	/**
	 * Adds all the orders to this view.
	 * @param orders Collection of Strings
	 */
	public void addAll(Collection<Order> orders) {
		for (Order o: orders) {
			mAdapter.add(o);
		}
		mAdapter.notifyDataSetChanged();
	}

	/**
	 * Deletes this order if it finds it.
	 * @param order String
	 */
	public void deleteOrder(Order order) {
		mAdapter.remove(order);
		mAdapter.notifyDataSetChanged();
	}

	/**
	 * Clears all orders.
	 */
	public void clearOrder() {
		mAdapter.clear();
		mAdapter.notifyDataSetChanged();
	}

	//////////////////////////////////////////////////////
	//// Following is the interface in which activities
	//// that wish to attach this Fragment must implement
	//// Intended to use for user input
	//////////////////////////////////////////////////////

	/**
	 * Mandatory interface for this fragment.
	 * @author mhotan
	 */
	public interface OrderItemListener {

		/**
		 * Notifies the progress of this order has changed.
		 * @param order order to reference
		 * @param progress progress of this order
		 */
		void onProgressChanged(Order order, int progress);

		/**
		 * Restaurant wants to notify customer that the order is complete.
		 * @param order order to reference
		 */
		void onOrderComplete(Order order);

		/**
		 * User(Restaurant Employee) wants to see details pertaining this order.
		 * @param order order to reference
		 */
		void onOrderSelected(Order order);

		/**
		 * Returns a non null list of orders to present.
		 * @return return the list of orders.
		 */
		List<Order> getPendingOrders();

	}

	//////////////////////////////////////////////////////
	//// Adapter to handle using listitems specific to 
	//// Showing Orders for restaurants
	//////////////////////////////////////////////////////

	/**
	 * Adapter to handle.
	 * @author mhotan
	 */
	private class OrderListAdapter extends ArrayAdapter<Order> {

		private final Context mContext;

		/**
		 * Creates an adapter that manages the addition and layout of
		 * Orders.
		 * @param ctx Context
		 * @param orders List of strings
		 */
		public OrderListAdapter(Context ctx, List<Order> orders) {
			super(ctx, R.layout.listitem_restaurant_order_bot, orders);
			this.mContext = ctx;
		}

		@Override
		public void add(Order o) {
			super.add(o);
			this.notifyDataSetChanged();
		}

		@Override
		public void addAll(Collection<? extends Order> collection) {
			super.addAll(collection);
			notifyDataSetChanged();
		}

		@Override
		public void clear() {
			super.clear();
			this.notifyDataSetChanged();
		}

		@SuppressWarnings("BC_UNCONFIRMED_CAST")
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {

			// Establish a reference to the top and bottom of ex
			View vwTop;
			View vwBot;

			LinearLayout layoutView = null;

			if(convertView == null) {
				layoutView = new LinearLayout(mContext);
				layoutView.setOrientation(LinearLayout.VERTICAL);
				LayoutInflater inflater = (LayoutInflater) mContext
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				vwTop = inflater.inflate(R.layout.listitem_restaurant_order_top, null, true);
				vwBot = inflater.inflate(R.layout.listitem_restaurant_order_bot, null, true);
				layoutView.addView(vwTop);
				layoutView.addView(vwBot);
				convertView = layoutView;
			} else {
				//Everything already created, just find them
				vwTop = convertView.findViewById(R.id.listitem_order_top);
				vwBot = convertView.findViewById(R.id.listitem_order_bot);
			}

			Order orderToShow = getItem(position);

			// For every restaurant to present create a handler for the order;
			// We are creating this view for the very first time
			if (layoutView != null) {
				// Create a handler just for the restaurant.
				new OrderHandler(orderToShow, vwTop, vwBot);
			}
			return convertView;
		}

		/**
		 * Listener for certain item of an order item view.
		 * @author mhotan
		 */
		private class OrderHandler implements OnClickListener, OnSeekBarChangeListener {

			private final Order mOrder;
			private final ImageView mExpandDown;
			private final ImageButton mPickOrder;
			private final View mTop, mBottom;

			/**
			 * Build this handler from the Order and its corresponding views.
			 * 
			 * @param order Order to associate to.
			 * @param top Top view for the order.
			 * @param bottom bottom view for the order.
			 */
			public OrderHandler(Order order, View top, View bottom) {
				mOrder = order;

				mTop = top;
				mBottom = bottom;

				// Get a reference to all the top pieces 
				final ImageView ORDERIMAGE = (ImageView) 
						mTop.findViewById(R.id.image_order_thumbnail);
				TextView orderTitle = 
						(TextView) mTop.findViewById(R.id.label_order_title);
				mExpandDown = (ImageView) 
						mTop.findViewById(R.id.button_expand_order);
				TextView time = (TextView) mTop.findViewById(R.id.label_order_time);
				mPickOrder = (ImageButton) mBottom.findViewById(R.id.button_proceed);	
				
				// Get a reference to all the bottom pieces
				Button buttonCompleteOrder = (Button) mBottom.findViewById(R.id.button_completed_order);

				SeekBar progressBar = (SeekBar) mBottom.findViewById(R.id.seekbar_order_progress);


				//Populate
				int table = order.getTableID();

				if(table == -1) { // No Table
					orderTitle.setText(order.getOriginalUser().getName());
				} else {
					orderTitle.setText("Table " + order.getTableID() 
							+ " - " + order.getOriginalUser().getName());
				}

				progressBar.setMax(100);
				progressBar.setProgress(0);

				time.setText(order.getOriginatingTime().toString());

				// Add listeners for reaction purposes
				buttonCompleteOrder.setOnClickListener(this);
				progressBar.setOnSeekBarChangeListener(this);
				
				// Set the image of this order
//				DineOnImage image = order.getMainImage();
//				if (image != null) {
//					mListener.onGetImage(image, new ImageGetCallback() {
//
//						@Override
//						public void onImageReceived(Exception e, Bitmap b) {
//							if (e == null) {
//								// We got the image so set the bitmap
//								ORDERIMAGE.setImageBitmap(b);
//							}
//						}
//					});
//				}

				// Set the bottom view to initial to be invisible
				mBottom.setVisibility(View.GONE);

				mTop.setOnClickListener(this);
				mPickOrder.setOnClickListener(this);
			}

			@Override
			public void onClick(View v) {

				if (v == mTop || v == mPickOrder) { 
					int bottomVisibility = mBottom.getVisibility();
					// Expand the bottom view if it is not shown
					// Hide the expand down button.
					if (bottomVisibility == View.GONE) {
						mExpandDown.setVisibility(View.GONE);
					} else if (bottomVisibility == View.VISIBLE) {
						mExpandDown.setVisibility(View.VISIBLE);
					}

					// Expand the animation
					ExpandAnimation expandAni = new ExpandAnimation(mBottom, 500);
					mBottom.startAnimation(expandAni);

				} else if (v.getId() == R.id.button_completed_order) {
					mAdapter.remove(mOrder);
					mListener.onOrderComplete(mOrder);
					mAdapter.notifyDataSetChanged();
				}
				
				if (v == mPickOrder) {
					mListener.onOrderSelected(mOrder);
				}
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				mListener.onProgressChanged(mOrder, progress);

				if (progress == seekBar.getMax()) {
					mAdapter.remove(mOrder);
					mListener.onOrderComplete(mOrder);
					mAdapter.notifyDataSetChanged();
				}
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) { }

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) { }
		}


	}
}
