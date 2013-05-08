package uw.cse.dineon.library.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.parse.ParseObject;

import uw.cse.dineon.library.Storable;
import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Utility class to hold a lot of reusable method and procedures
 * @author mhotan
 *
 */
public class Utility {

	/**
	 * Returns whether the screen can be split horizontally into two panes
	 * for optimal user interaction.  Currently the restriction is 
	 * that if the screen is very large 
	 * <b>Currently: XLarge screen layout
	 * <b><b>OR if the screen is large and in landscape
	 * @param context Context of the activity
	 * @return true if it is able to split, false otherwise
	 */
	public static boolean isPaneSplitable(Context context){
		int mask = (context.getResources().getConfiguration().screenLayout & 
				Configuration.SCREENLAYOUT_SIZE_MASK);
		boolean isLargeEnough = mask == Configuration.SCREENLAYOUT_SIZE_LARGE 
				|| mask == Configuration.SCREENLAYOUT_SIZE_XLARGE;
		//		boolean isLayoutOriented = context.getResources().getConfiguration().orientation
		//				== Configuration.ORIENTATION_LANDSCAPE;
		return isLargeEnough;
	}

	/**
	 * Checks whether the given context is connected to 
	 * the network either via wifi or data
	 * @param context Context that needs network
	 * @return true if connected to the network, false otherwise
	 */
	public static boolean isConnectedToNetwork(Context context){
		ConnectivityManager connMgr = (ConnectivityManager) 
				context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		} else {
			return false;
		}
	}
	
	

}
