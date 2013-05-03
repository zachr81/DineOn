package uw.cse.dineon.library.util;

/**
 * Static tool to help validate passwords.
 * @author mhotan
 */
public final class CredentialValidator {

	private static final String RESOLVED = "Valid password";
	
	private static final int MIN_LENGTH = 1; // TODO change later
	
	private static final Resolution RESOLVED_INSTANCE = new Resolution();
	private static final Resolution NULL_PW = new Resolution(false, "Can't have a NULL password");
	private static final Resolution EMPTY_PW = new Resolution(false, 
			"Can't have a password with no characters");
	private static final Resolution SHORT_PW = new Resolution(false, 
			"Password must have more then " + MIN_LENGTH + " characters");
	
	
	/**
	 * Can't create instance.
	 */
	private CredentialValidator() { }
	
	/**
	 * A Password validator that checks whether a string password would make a good password.
	 * 
	 * @param password Password to validate as a "Good" password
	 * @return A resolution instance stating the result of validifying the password 
	 */
	public static Resolution isValidPassword(String password){
		if (password == null) {
			return NULL_PW;
		} 
		if (password.isEmpty()) {
			return EMPTY_PW;
		}
		if (password.length() < MIN_LENGTH) {
			return SHORT_PW;
		}
		
		return RESOLVED_INSTANCE;
	}
	
	/**
	 * TODO Might use later pending how we create messages
	 * Handles the distribution and creation of Resolution instances.
	 * Allows memory conservation and virtualization of Resolution construction
	 * @author mhotan
	 */
	private static class ResolutionFactory {
//		
//		/**
//		 * 
//		 * @param pw
//		 * @return
//		 */
//		static Resolution getNullOrEmptyResolution(String pw){
//			return NULL_PW;
//		}
	}
	
	/**
	 * @author mhotan
	 *
	 */
	public static class Resolution {
		
		private final boolean isResolved;
		private final String message;
		
		/**
		 * Returns resolved password result.
		 * Only Password validator can generate a resolution
		 */
		Resolution() {
			this(true, RESOLVED);
		}
		
		/**
		 * 
		 * @param valid true if valid password, false other wise
		 * @param message 
		 */
		Resolution(boolean valid, String message) {
			this.isResolved = valid;
			this.message = message;
		}
		
		/**
		 * Returns whether the password is valid.
		 * @return true if valid
		 */
		public boolean isValid(){
			return isResolved;
		}
		
		/**
		 * Returns the message associated with this resolution.
		 * @return message as string literal
		 */
		public String getMessage(){
			return message;
		}
	}
	
}
