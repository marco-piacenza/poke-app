package org.mp.poke.app.exception;

/**
 * Exception for errors related to API interaction.
 * 
 * @author Marco Piacenza
 * @version 0.0.1
 * @since 0.0.1
 */
public class ApiException extends RuntimeException {

	private static final long serialVersionUID = 1697007438128136609L;

	public ApiException() {
		super();
	}

	public ApiException(String message) {
		super(message);
	}

	public ApiException(String message, Throwable cause) {
		super(message, cause);
	}
}
