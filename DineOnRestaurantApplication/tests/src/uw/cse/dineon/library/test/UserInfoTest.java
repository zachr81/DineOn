package uw.cse.dineon.library.test;

import uw.cse.dineon.library.UserInfo;
import android.test.AndroidTestCase;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;



/**
 * Tests library class UserInfo
 * 
 * White box tests
 * @author espeo196
 */
public class UserInfoTest extends AndroidTestCase {

	public static final int TIMEOUT = 1000; 
	ParseUser testUser;
	UserInfo testUInfo;
	
	//Still contains a parse initialize to allow the ParseUser to set username, needed to test functionality of
	//UserInfo's constructor
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		Parse.initialize(this.getContext(), "RUWTM02tSuenJPcHGyZ0foyemuL6fjyiIwlMO0Ul", "wvhUoFw5IudTuKIjpfqQoj8dADTT1vJcJHVFKWtK");
		
		testUser = new ParseUser();
		testUser.setUsername("utester");
		testUser.setPassword("pass");
		
		testUInfo = new UserInfo(testUser);
	}

	/**
	 * Asserts that the userinfo correctly stores a name and phone.
	 */
	public void testDefault() {
		assertEquals("Undetermined", testUInfo.getPhone());
		assertTrue(null == testUInfo.getEmail());
	}

	/**
	 * Asserts that the userinfo correctly stores a name and phone.
	 */
	public void testGetName() {
		assertEquals("utester", testUser.getUsername());
		assertEquals("utester", testUInfo.getName());
	}
	
	/**
	 * Asserts that the userinfo correctly stores a phone #.
	 */
	public void testSetAndGetBasicPhoneNumber() {
		testUInfo.setPhone("911");
		assertEquals("911", testUInfo.getPhone());
	}
	
	/**
	 * Asserts that the userinfo correctly stores an email.
	 */
	public void testSetAndGetBasicEmail() {
		testUInfo.setEmail("tester@cs.washington.edu");
		assertEquals("tester@cs.washington.edu", testUInfo.getEmail());
	}
	
	/**
	 * Asserts that the userinfo stays the same when packed and
	 * unpacked.
	 */
	public void testPackAndUnpack() throws ParseException {
		ParseObject pObj = testUInfo.packObject();
		UserInfo unPacked = new UserInfo(pObj);
		assertEquals(testUInfo.getEmail(), unPacked.getEmail());
		assertEquals(testUInfo.getName(), unPacked.getName());
		assertEquals(testUInfo.getPhone(), unPacked.getPhone());
		assertEquals(testUInfo.getObjId(), unPacked.getObjId());
		assertEquals(testUInfo.getClass(), unPacked.getClass());
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
