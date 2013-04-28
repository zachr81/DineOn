package uw.cse.dineon.library.util;

import android.content.Context;
import android.content.res.Configuration;

/**
 * Utility class to hold a lot of reusable method and procedures
 * @author mhotan
 *
 */
public class Utility {

	/**
	 * Returns whether the screen can 
	 * @param context
	 * @return
	 */
	public static boolean isPaneSplitable(Context context){
		int mask = (context.getResources().getConfiguration().screenLayout & 
			    Configuration.SCREENLAYOUT_SIZE_MASK);
		boolean isLargeEnough = mask == Configuration.SCREENLAYOUT_SIZE_LARGE 
				|| mask == Configuration.SCREENLAYOUT_SIZE_XLARGE;
		boolean isLayoutOriented = context.getResources().getConfiguration().orientation
				== Configuration.ORIENTATION_LANDSCAPE;
		return isLargeEnough && isLayoutOriented;
	}
	
}
