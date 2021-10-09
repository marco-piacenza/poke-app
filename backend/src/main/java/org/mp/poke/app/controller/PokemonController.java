package org.mp.poke.app.controller;

import java.text.MessageFormat;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.mp.poke.app.exception.PokeApiException;
import org.mp.poke.app.exception.PokemonNotFoundException;
import org.mp.poke.app.model.PokemonDescriptionResponse;
import org.mp.poke.app.service.PokeApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Back-end controller.
 * 
 * @author Marco Piacenza
 * @version 0.0.1
 * @since 0.0.1
 */
@Controller
@RequestMapping("/pokemon")
public class PokemonController {

	@Autowired
	private PokeApiService pokeApiService;
	
	private static final Logger LOG = LoggerFactory.getLogger(PokemonController.class);
	
	/**
	 * Retrieve a Shakespearean description of the given pokemon species.
	 * 
	 * @param pokemonSpecies The pokemon species (e.g. charizard)
	 * @return A {@link PokemonDescriptionResponse} if the given species exists.
	 * @throws PokeApiException         If an error occurs invoking PokeApi
	 * @throws PokemonNotFoundException If the given species does not exists.
	 */
	@ResponseBody
	@GetMapping("/{pokemonSpecies}")
	public PokemonDescriptionResponse getPokemonSpeciedDescription(@PathVariable final String pokemonSpecies) throws PokeApiException, PokemonNotFoundException {
		String logMessage = MessageFormat.format("GET called on {0} resource", "/pokemon/" + pokemonSpecies);
		LOG.info(logMessage);
		if (StringUtils.isNotBlank(pokemonSpecies)) {
			Optional<String> pokemonSpeciesDescription = pokeApiService.getPokemonDescription(pokemonSpecies);
			PokemonDescriptionResponse response = new PokemonDescriptionResponse(pokemonSpecies, pokemonSpeciesDescription.get());
			return response;
		} else {
			LOG.warn("Received empty parameter- elaboration abort.");
			throw new PokemonNotFoundException();
		}
	}

}
