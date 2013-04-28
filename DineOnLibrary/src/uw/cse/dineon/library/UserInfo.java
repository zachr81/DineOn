package uw.cse.dineon.library;

/**
 * @author Espeo196
 *
 */
public class UserInfo extends Storable {
	private String name;
	private int phone;
	private String email;

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
}
