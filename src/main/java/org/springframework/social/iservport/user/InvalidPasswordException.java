package org.springframework.social.iservport.user;

/**
 * The password entered during an sign-in attempt was invalid.
 * 
 * @author Keith Donald
 * @author mauriciofernandesdecastro
 */
@SuppressWarnings("serial")
public final class InvalidPasswordException extends RemoteUserException {

	public InvalidPasswordException() {
		super("invalid password");
	}
}
