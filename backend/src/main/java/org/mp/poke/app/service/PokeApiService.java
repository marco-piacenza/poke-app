package org.mp.poke.app.service;

import java.util.Optional;

import org.mp.poke.app.exception.PokeApiException;
import org.mp.poke.app.exception.PokemonNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

/**
 * Service for interacting with the PokeApi
 * 
 * @author Marco Piacenza
 * @version 0.0.1
 * @since 0.0.1
 */
@Service
public class PokeApiService {

	private static final String BASE_POKEAPI_URL = "https://pokeapi.co/api/v2/pokemon-species/"; 
	
	private static final Logger LOG = LoggerFactory.getLogger(PokeApiService.class);
	
	/**
	 * Get the pokemon description from PokeApi
	 * 
	 * @param pokemonSpecies The pokemon species to search
	 * @return The first available description for the given pokemon species, if the
	 *         species exists.
	 * @throws PokeApiException         If an error occurs invoking PokeApi
	 * @throws PokemonNotFoundException If the given species does not exists.
	 */
	@Cacheable("pokemonSpeciesDescription")
	public Optional<String> getPokemonDescription(final String pokemonSpecies) throws PokeApiException, PokemonNotFoundException {
		Optional<String> callPokemonSpeciesApiResponseBody = callPokemonSpeciesApi(pokemonSpecies);
		if (callPokemonSpeciesApiResponseBody.isPresent()) {
			return processPokemonSpeciesApiResponseBody(callPokemonSpeciesApiResponseBody.get());
		} else {
			throw new PokeApiException("Failure contacting PokeApi");
		}
	}
	
	/**
	 * Perform the HTTP call to PokeApi
	 * {@code https://pokeapi.co/docs/v2#pokemon-species}
	 * 
	 * @param pokemonSpecies The species to search for.
	 * @return An {@link Optional} filled with the pokemon species description, if
	 *         the given species exists.
	 * @throws PokemonNotFoundException If the given species does not exists.
	 */
	private Optional<String> callPokemonSpeciesApi(final String pokemonSpecies) throws PokemonNotFoundException {
		try {
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> responseEntity = restTemplate
					.getForEntity(getPokemonSpeciesEndopintUrl(pokemonSpecies), String.class);
			LOG.info("Call to API returned 2xx HTTP status.");
			return Optional.of(responseEntity.getBody());
		} catch (HttpClientErrorException.NotFound exc) {
			LOG.error("Pokemon species not found.");
			throw new PokemonNotFoundException();
		} catch (RestClientException exc) {
			LOG.error("An error occurred interacting with PokeApi.");
			LOG.error(exc.getMessage(), exc);
			return Optional.empty(); 
		}
	}
	
	/**
	 * Build PokeApi {@code https://pokeapi.co/docs/v2#pokemon-species} URL.
	 * 
	 * @param pokemonSpecies The species to search for.
	 * @return The API URL
	 */
	private String getPokemonSpeciesEndopintUrl(final String pokemonSpecies) {
		return BASE_POKEAPI_URL + "/" + pokemonSpecies;
	}

	/**
	 * Process {@code https://pokeapi.co/docs/v2#pokemon-species} API response.
	 * 
	 * @param responseBody The PokeApi response body, as a {@link String}.
	 * @return An {@link Optional} with the pokemon species {@link String}
	 *         description if this information is available, an empty
	 *         {@link Optional} otherwise.
	 */
	private Optional<String> processPokemonSpeciesApiResponseBody(final String responseBody) {
		String pokemonSpeciesDescription = null;
		JsonNode responseAsJson = null;
		final ObjectMapper objectMapper = new ObjectMapper();
		try {
			responseAsJson = objectMapper.readTree(responseBody);
		} catch (JsonProcessingException exc) {
			LOG.error("An error occurred reading PokeApi JSON response.");
			LOG.error(exc.getMessage(), exc);

		}
		if (responseAsJson != null) {
			if (responseAsJson.has("flavor_text_entries")) {
				ArrayNode arrayNode = (ArrayNode) responseAsJson.get("flavor_text_entries");
				if (arrayNode.size() > 0) {
					pokemonSpeciesDescription = arrayNode.get(0).get("flavor_text").asText();
				}
			}
		}
		return Optional.ofNullable(pokemonSpeciesDescription);
	}
	
}
