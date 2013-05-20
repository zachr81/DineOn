package uw.cse.dineon.restaurant.test;

import uw.cse.dineon.library.Restaurant;
import uw.cse.dineon.library.RestaurantInfo;
import uw.cse.dineon.restaurant.active.RestauarantMainActivity;
import uw.cse.dineon.restaurant.login.CreateNewAccountFragment;
import uw.cse.dineon.restaurant.login.CreateNewRestaurantAccountActivity;
import android.app.Instrumentation.ActivityMonitor;
import android.support.v4.app.Fragment;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQuery.CachePolicy;
import com.parse.ParseUser;

public class CreateRestaurantAccountTest extends
ActivityInstrumentationTestCase2<CreateNewRestaurantAccountActivity> {

	private CreateNewRestaurantAccountActivity activity;

	private static final int WAIT_TIME = 30000;

	private static final String fakeUserName = "createRestaurantFakeUserName";
	private static final String fakePassword = "createRestaurantFakePassword";
	private static final String fakeEmail = "fakeemail@yourmomhouse.com";
	private ParseUser mFakeUser;
	private CreateNewRestaurantAccountActivity mActivity;

	public CreateRestaurantAccountTest() throws ParseException {
		super(CreateNewRestaurantAccountActivity.class);

	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		// initialize Parse
		Parse.initialize(getInstrumentation().getTargetContext(),
				"RUWTM02tSuenJPcHGyZ0foyemuL6fjyiIwlMO0Ul",
				"wvhUoFw5IudTuKIjpfqQoj8dADTT1vJcJHVFKWtK");

		setActivityInitialTouchMode(false);

		mActivity = getActivity();
	}

	@Override
	protected void tearDown() throws Exception {
		//		mActivity.finish();
		if (mFakeUser != null) {
			try{
				mFakeUser.delete();
			} catch (Exception e) {}
		}
		super.tearDown();
	}

	public void test() {
		assertTrue(true);
	} 

	/**
	 * Test the existence of the CreateNewAccount Fragment
	 */
	public void testFragmentExistence() {
		Fragment f = getFragment();
		assertNotNull(f);
		assertNotNull(f.getView());
	}

	public void testCreateNewAccount() throws ParseException {

		ActivityMonitor monitor = getInstrumentation().addMonitor(
				RestauarantMainActivity.class.getName(), null, false);

		CreateNewAccountFragment frag = getFragment();
		View current = frag.getView();
		final EditText username = (EditText) 
				current.findViewById(
						uw.cse.dineon.restaurant.R.id.input_createnewaccount_username);
		final EditText password = (EditText) 
				current.findViewById(
						uw.cse.dineon.restaurant.R.id.input_createnewaccount_password);
		final EditText passwordrepeat = (EditText) 
				current.findViewById(
						uw.cse.dineon.restaurant.R.id.input_createnewaccount_repeat_password);
		final EditText email = (EditText) 
				current.findViewById(
						uw.cse.dineon.restaurant.R.id.input_createnewaccount_email);
		final Button submit = (Button) current.findViewById(
				uw.cse.dineon.restaurant.R.id.button_create_account);

		mActivity.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				username.setText(fakeUserName);
				password.setText(fakePassword);
				passwordrepeat.setText(fakePassword);
				email.setText(fakeEmail);
				submit.performClick();
			}
		});

		RestauarantMainActivity mainAct = (RestauarantMainActivity) 
				monitor.waitForActivityWithTimeout(WAIT_TIME);
		assertNotNull(mainAct);

		mFakeUser = ParseUser.getCurrentUser();
		mFakeUser.fetch();
		assertNotNull(mFakeUser);
		assertEquals(mFakeUser.getUsername(), fakeUserName);
		assertEquals(mFakeUser.getEmail(), fakeEmail);

		ParseQuery inner = new ParseQuery(RestaurantInfo.class.getSimpleName());
		inner.whereEqualTo(RestaurantInfo.PARSEUSER, mFakeUser);
		ParseQuery query = new ParseQuery(Restaurant.class.getSimpleName());
		query.whereMatchesQuery(Restaurant.INFO, inner);
		ParseObject object = query.getFirst();

		assertNotNull(object);

		Restaurant justMade = new Restaurant(object);

		assertNotNull(justMade);
		assertNotNull(justMade.getInfo());

		assertEquals(justMade.getName(), fakeUserName);

		justMade.deleteFromCloud();
		mFakeUser.delete();

		mainAct.finish();
	}

	private CreateNewAccountFragment getFragment(){
		Fragment f = mActivity.getSupportFragmentManager().findFragmentById(
				uw.cse.dineon.restaurant.R.id.fragment1);
		CreateNewAccountFragment frag = (CreateNewAccountFragment) f;
		return frag;
	}
}
