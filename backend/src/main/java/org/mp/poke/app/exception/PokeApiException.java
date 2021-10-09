package org.mp.poke.app.exception;

/**
 * Exception for errors related to PokeApi interaction.
 * 
 * @author Marco Piacenza
 * @version 0.0.1
 * @since 0.0.1
 */
public class PokeApiException extends RuntimeException {

	private static final long serialVersionUID = 1697007438128136609L;

	public PokeApiException() {
		super();
	}

	public PokeApiException(String message) {
		super(message);
	}

	public PokeApiException(String message, Throwable cause) {
		super(message, cause);
	}
}
