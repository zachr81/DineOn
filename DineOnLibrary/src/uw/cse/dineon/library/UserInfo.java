package uw.cse.dineon.library;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.parse.ParseObject;

/**
 * @author Espeo196
 *
 */
public class UserInfo extends Storable implements Parcelable {
	private String name;
	private int phone;
	private String email;

	public UserInfo(){
		
	}
	
	public UserInfo(Parcel source) {
		// TODO Auto-generated constructor stub
	}

	/**
	 *
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 *
	 * @param n String name
	 */
	public void setName(String n) {
		this.name = n;
	}

	/**
	 *
	 * @return int
	 */
	public int getPhone() {
		return phone;
	}

	/**
	 *
	 * @param number int Phone number
	 */
	public void setPhone(int number) {
		this.phone = number;
	}

	/**
	 *
	 * @return String
	 */
	public String getEmail() {
		return email;
	}

	/**
	 *
	 * @param e String email
	 */
	public void setEmail(String e) {
		this.email = e;
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

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Parcelable creator object of a Menu.
	 * Can create a Menu from a Parcel.
	 */
	public static final Parcelable.Creator<UserInfo> CREATOR = 
			new Parcelable.Creator<UserInfo>() {

				@Override
				public UserInfo createFromParcel(Parcel source) {
					return new UserInfo(source);
				}

				@Override
				public UserInfo[] newArray(int size) {
					return new UserInfo[size];
				}
			};
}
