package uw.cse.dineon.library;

import java.util.List;

import android.os.Bundle;

import com.parse.ParseObject;


/**
 * 
 * @author zachr81
 *
 */
public class Menu extends Storable {

	private List<MenuItem> items;

	/**
	 * 
	 * @param items
	 */
	public Menu(List<MenuItem> items) {
		super();
		this.items = items;
		
	}
	
	/**
	 * @return the items
	 */
	public List<MenuItem> getItems() {
		return items;
	}

	/**
	 * @param items the items to set
	 */
	public void setItems(List<MenuItem> items) {
		this.items = items;
	}
	
	/**
	 * 
	 * @param item
	 */
	public void addNewItem(MenuItem item) {
		items.add(item);
	}
	
	/**
	 * 
	 * @param item
	 */
	public void removeItem(MenuItem item) {
		items.remove(item);
	}

	@Override
	public Bundle bundle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void unbundle(Bundle b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ParseObject packObject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void unpackObject(ParseObject pobj) {
		// TODO Auto-generated method stub
		
	}
}
