package org.mp.poke.app.exception;

/**
 * Exception used when a certain pokemon species is not found.
 * 
 * @author Marco Piacenza
 * @version 0.0.1
 * @since 0.0.1
 */
public class PokemonNotFoundException extends RuntimeException {
    
	private static final long serialVersionUID = 2734227994024753366L;

	public PokemonNotFoundException() {
        super();
    }

    public PokemonNotFoundException(String message) {
        super(message);
    }

    public PokemonNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
