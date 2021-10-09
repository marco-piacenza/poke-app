package org.mp.poke.app.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mp.poke.app.exception.PokemonNotFoundException;

/**
 * Test class for {@link PokeApiService}.
 * 
 * @author Marco Piacenza
 * @version 0.0.1
 * @since 0.0.1
 */
public class PokeApiServiceTest {

	@InjectMocks
	private PokeApiService pokeApiService;
	
	@BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

	private static final String POKEMON_SPECIES = "charizard";
	
	private static final String POKEMON_SPECIES_NOT_FOUND = "qwerty";
	
	@Test
	public void testGetPokemonDescription() throws Exception {
		Optional<String> pokemonDescription = pokeApiService.getPokemonDescription(POKEMON_SPECIES);
		assertNotNull(pokemonDescription, "expected non-null result");
		assertNotNull(pokemonDescription.get(), "expected non-null description");
	}
	
	@Test
	public void testGetPokemonDescriptionThrowsPokemonNotFoundException() throws Exception {
		assertThrows(PokemonNotFoundException.class, () -> {
			pokeApiService.getPokemonDescription(POKEMON_SPECIES_NOT_FOUND);
		});
	}
	
}
