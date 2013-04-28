package uw.cse.dineon.library.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

/**
 * General Class that will help up build the project
 * <b>Add helper methods to help notify the developer and 
 * user that a certain feature is not implemented
 * @author mhotan
 */
public class DevelopTools {

	/**
	 * Return an alert dialg alerting the user and developer a certain feature is not implemented
	 * <b>if null listener is implemented a listener is created that just cancels the dialog 
	 * in its place
	 * @param ctx
	 * @param listener listener to react when user clicks button
	 * @return
	 */
	public static AlertDialog getUnimplementedDialog(Context ctx, DialogInterface.OnClickListener listener) {
		if (listener == null) {
			listener = new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			};
		}
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctx);

		// set title
		alertDialogBuilder.setTitle("DARN! Not Implemented");
		alertDialogBuilder.setMessage("This feature is still being built. " +
				"Sorry, but we are working on it!");
		alertDialogBuilder.setPositiveButton("OK", listener);
		return alertDialogBuilder.create();
	}

}
