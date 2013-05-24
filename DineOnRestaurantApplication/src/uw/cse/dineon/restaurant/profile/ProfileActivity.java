package uw.cse.dineon.restaurant.profile;

import uw.cse.dineon.library.MenuItem;
import uw.cse.dineon.library.Restaurant;
import uw.cse.dineon.library.RestaurantInfo;
import uw.cse.dineon.library.image.DineOnImage;
import uw.cse.dineon.library.image.ImageCache.ImageGetCallback;
import uw.cse.dineon.library.image.ImageIO;
import uw.cse.dineon.library.image.ImageObtainer;
import uw.cse.dineon.library.util.DineOnConstants;
import uw.cse.dineon.restaurant.DineOnRestaurantActivity;
import uw.cse.dineon.restaurant.R;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.SaveCallback;

/**
 * Activity that allows the user (Restaurant) to access and alter their menu
 * items and profile.
 * 
 * @author mhotan
 */
public class ProfileActivity extends DineOnRestaurantActivity implements
TabListener, RestaurantInfoFragment.InfoChangeListener,
MenuItemsFragment.MenuItemListener {

	private static final String TAG = ProfileActivity.class.getSimpleName();

	/**
	 * Keeps track of last tab position. This lets us gain a reference to which
	 * tab needs to be presented
	 */
	private int mLastTabPosition;

	/**
	 * Last used fragment string.  used to reference for fragment substitution.
	 */
	private static final String LAST_FRAG_TAG = "LAST_FRAG";

	private MenuItemsFragment mItemsFragment;
	private RestaurantInfoFragment mRestInfoFragment;
	
	private Context This;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		This = this;
		
		mLastTabPosition = 1; // Let the tab be either the 0 or 1

		if (!isLoggedIn()) {
			Log.w(TAG, "User not logged in cant show profile");
			return;
		}

		// If logged in fill views appropriately
		// Set the actionbar with associated tabs
		ActionBar ab = getActionBar();
		if (ab != null) { // Support older builds
			ab.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
			ab.setTitle(getRestaurant().getName());
			ab.setDisplayShowTitleEnabled(true);
			ab.addTab(ab.newTab()
					.setText(R.string.tab_actionbar_restaurant_profile)
					.setTabListener(this));
			ab.addTab(ab.newTab()
					.setText(R.string.tab_actionbar_restaurant_menuitems)
					.setTabListener(this));
		}
	}

	@Override
	public void updateUI() {
		super.updateUI();	
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);

		android.view.MenuItem itemProfile = menu
				.findItem(R.id.item_restaurant_profile);
		// Already at profile page so remove the button
		if (itemProfile != null) { // If exists
			itemProfile.setEnabled(false);
			itemProfile.setVisible(false);
		}
		return true;
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// Obtain a reference on which tab is being selected
		int pos = tab.getPosition();
		int diff = pos - mLastTabPosition;

		// Ignore ft because it is not support fragment transaction
		android.support.v4.app.FragmentTransaction supFT = getSupportFragmentManager()
				.beginTransaction();

		Restaurant rest = getRestaurant();
		RestaurantInfo info = rest.getInfo();
		assert (info != null);

		Fragment frag = null;
		if (diff < 0) { // move to a tab that relatively left

			frag = new RestaurantInfoFragment();
			mRestInfoFragment = (RestaurantInfoFragment) frag;
			// Assign the animation where the fragment slides
			// in from the right
			supFT.setCustomAnimations(android.R.anim.slide_in_left,
					android.R.anim.slide_out_right);
		} else { // move the tab relatively rights
			// TODO Correctly obtain the Restaurant

			frag = new MenuItemsFragment();
			mItemsFragment = (MenuItemsFragment)frag;

			// Assign the animation where the fragment slides
			// in from the
			supFT.setCustomAnimations(R.anim.slide_in_right,
					R.anim.slide_out_left);
		}

		// Update the position
		mLastTabPosition = pos;

		if (frag != null) {
			supFT.replace(android.R.id.content, frag, LAST_FRAG_TAG );
			supFT.commit();
		}
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// Nothing to really do.
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// Nothing to really do.
	}

	// ////////////////////////////////////////////////////
	// // Following are fragment call backs that signify user interaction
	// ////////////////////////////////////////////////////

	@Override
	public void onMenuItemDeleted(MenuItem item) {
		Toast.makeText(this, "Delete not available yet", Toast.LENGTH_SHORT)
		.show();
	}

	@Override
	public void onMenuItemAdded(MenuItem item) {
		// getRestaurant().saveInBackGround(new SaveCallback() {
		item.saveInBackGround(new SaveCallback() {
			@Override
			public void done(ParseException e) {
				if (e == null) {

					getRestaurant().getInfo().saveInBackGround(new SaveCallback() {

						@Override
						public void done(ParseException e) {
							notifyAllRestaurantChange();
							Toast.makeText(getApplicationContext(),
									"Menu Item Added!", Toast.LENGTH_SHORT)
									.show();
						}

					});
				} else {
					Log.e(TAG, e.getMessage() + " #" + e.getCode());
					Log.d(TAG, getRestaurant().packObject().toString());
				}
			}
		});
	}

	@Override
	public void onMenuItemModified(MenuItem item) {
		getRestaurant().saveInBackGround(new SaveCallback() {
			@Override
			public void done(ParseException e) {
				if (e == null) {
					notifyAllRestaurantChange();
					Toast.makeText(getApplicationContext(),
							"Menu Item Updated!", Toast.LENGTH_SHORT).show();
				} else {
					Log.e(TAG, e.getMessage() + " #" + e.getCode());
					Log.d(TAG, getRestaurant().packObject().toString());
				}
			}
		});
	}

	@Override
	public void onRestaurantInfoUpdate(RestaurantInfo rest) {
		rest.saveInBackGround(new SaveCallback() {
			@Override
			public void done(ParseException e) {
				if (e == null) {
					notifyAllRestaurantChange();
					Toast.makeText(getApplicationContext(),
							"Restaurant Info Updated!", Toast.LENGTH_SHORT)
							.show();
				} else {
					Log.e(TAG, e.getMessage() + " #" + e.getCode());
					Log.d(TAG, getRestaurant().packObject().toString());
				}
			}
		});
	}

	@Override
	public RestaurantInfo getInfo() {
		if (getRestaurant() == null) {
			return null;
		}
		return getRestaurant().getInfo();
	}

	@Override
	public void onRequestTakePicture() {
		ImageObtainer.launchTakePictureIntent(this, DineOnConstants.REQUEST_TAKE_PHOTO);
	}

	@Override
	public void onRequestGetPictureFromGallery() {
		ImageObtainer.launchChoosePictureIntent(this, DineOnConstants.REQUEST_CHOOSE_PHOTO);
	}

	@Override
	public void onSelectImageAsDefault(int i) {
		// TODO set image at index I as  the default
	}
	
	@Override
	public void onImageRemoved(int index) {
		getRestaurant().getInfo().removeImageAt(index);
		getRestaurant().saveInBackGround(new SaveCallback() {
			
			@Override
			public void done(ParseException e) {
				notifyAllRestaurantChange();
			}
		});
	}
	
	@Override
	public void getThisImage(DineOnImage image, final ViewGroup layout, final int id) {
		
		// Make a potentiall asyncronous call to download the image
		// from local storage or network.
		mImageCache.getImageFromCache(image, new ImageGetCallback() {
			
			@Override
			public void onImageReceived(Exception e, Bitmap b) {
				if (e != null) { // Error
					Log.e(TAG, "Unable to get image because e: " + e.getMessage());
					return;
				}
				// we go the picture.
				if (mRestInfoFragment == null) {
					Log.e(TAG, "Can't replace the correct view fragment is null");
					return;
				}
				
				mRestInfoFragment.replaceWithImage(layout, b, id);
			}
		});
	}

	/**
	 * Adds the photo to the restaurant. This method takes care of 
	 * all the image sizing and alignment.
	 * 
	 * This method will notify all the views in this activity that 
	 * need to know about a new photo.
	 * 
	 * This method will also handle the save of the new image
	 * While also adding it to the cache.
	 * 
	 * @param uri Uri for the image to download.
	 */
	private void addPhotoToRestaurant(Uri uri) {
		Bitmap b = ImageIO.loadBitmapFromURI(getContentResolver(), uri);
		final DineOnImage NEWIMG = new DineOnImage(b, null);

		if (mRestInfoFragment != null) {
			mRestInfoFragment.addImage(b);
		}
		// Recycle the image after adding it.
		b.recycle();

		Restaurant rest = getRestaurant();
		rest.addImage(NEWIMG);
		rest.saveInBackGround(new SaveCallback() {

			@Override
			public void done(ParseException e) {
				if (e != null) {
					// Able to save the restaurant
					mImageCache.addToCache(NEWIMG);	
				} else {
					// unable to save restaurant
					Log.w(TAG, "Unable to update restaurant with new image in the cloud");
				}
			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode != RESULT_OK) {
			Log.d(TAG, "User cancelled job for request code " + requestCode);
			return;
		}

		Uri u = data.getData();
		if (u == null) {
			Log.e(TAG, "Error getting image null Uri returned");
			return;
		}

		switch (requestCode) {
		// Took a photo.
		case DineOnConstants.REQUEST_TAKE_PHOTO:
		case DineOnConstants.REQUEST_CHOOSE_PHOTO: 
			addPhotoToRestaurant(u);
			break;
		default:
			Log.w(TAG, "Unsupported operation occured onActivityResult");
		}
	}

	/**
	 * 
	 * @author mhotan
	 */
	private class AsynchronousImageSaver extends AsyncTask<Void, Void, DineOnImage> {

		private final Bitmap mBitmap;
		
		/**
		 * 
		 * @param b bitmap to save in background thread.
		 */
		public AsynchronousImageSaver(Bitmap b) {
			if (b == null) {
				throw new NullPointerException("AsynchronousImageSaver image cannot be null");
			}
			mBitmap = b;
		}
		
		@Override
		protected DineOnImage doInBackground(Void... params) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}

}
