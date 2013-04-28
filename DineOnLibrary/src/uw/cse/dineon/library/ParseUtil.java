/**
 * 
 */
package uw.cse.dineon.library;


import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

/**
 * @author mtrathjen08
 * 
 * Class will wrap all functions that abstract interaction with
 * the Parse framework
 */
public class ParseUtil {
	private static final String TAG = "ParseUtil";
	/**
	 * Save obj into the cloud and store the acquired objID into obj
	 * 
	 * @param obj the java object that will be saved to the cloud
	 */
	public static void saveDataToCloud(Storable obj) {
		ParseObject pObj = obj.packObject();
		obj.setObjId(pObj.getObjectId());
		pObj.saveInBackground( new SaveCallback() {
			@Override
			public void done(ParseException e) {
				if (e == null) {
					// save was successful so send push
					Log.d(TAG, "Successfully saved object.");
				} else {
					// Error occured
					Log.d(TAG, "Error: " + e.getMessage());
				}
			}
		});
	}
	
	/**
	 * Query for object in the cloud given a list of attributes. On return the
	 * call-back provided will be invoked with the list of results.
	 * 
	 * @param c Static reference to the type of java class to query on 
	 * @param handle Call back for the query operation (Method must be static)
	 * @param attr The attributes used for the query. Every object in the returned
	 * list will satisfy the attributes passed.
	 * 
	 * Note: Returns a List<Storable> to the handler.
	 */
	public static void getDataFromCloud(Class<Storable> c, Method handle, Map<String, String> attr) {
		
		ParseQuery query = new ParseQuery(c.getSimpleName());
		Set<String> kSet = attr.keySet();
		for (String k : kSet) {
			String val = attr.get(k);
			query.whereEqualTo(k, val);
		}
		final Method h = handle;
		query.findInBackground(new FindCallback() {
		    public void done(List<ParseObject> list, ParseException e) {
		        if (e == null) {
		        	if (list.size() > 0) {
		        		String className = list.get(0).getClassName();
		        		List<Storable> classList = new LinkedList<Storable>();
		        		
		        		try {
		        			Storable s;
		        			for (ParseObject p : list) {
		        				s = (Storable) Class.forName(className).newInstance();
		        				s.unpackObject(p);
		        				classList.add(s);
		        			}
		        			h.invoke(null, classList);
		        		} catch (Exception ex) {
		        			
		        		}
		        	}
		        } else {
		            Log.d(TAG, "Error: " + e.getMessage());
		        }
		    }
		});
	}
}
