package org.mp.poke.app.service;

import java.util.Optional;

import org.mp.poke.app.exception.ApiException;
import org.mp.poke.app.exception.PokemonNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Service for interacting with the Translate API
 * 
 * @author Marco Piacenza
 * @version 0.0.1
 * @since 0.0.1
 */
@Service
public class TranslateApiService {

	private static final String TRANSLATE_API_BASE_URL = "https://api.funtranslations.com/translate/shakespeare.json";
	
	private static final String QUERY_PARAM_TEXT = "text";
	
	private static final Logger LOG = LoggerFactory.getLogger(TranslateApiService.class);
	
	@Cacheable("text")
	public Optional<String> getShakespearText(final String originalText) throws ApiException {
		Optional<String> callTranslateApiResponseBody = callTranslateApi(originalText);
		if (callTranslateApiResponseBody.isPresent()) {
			return processTranslateApiResponseBody(callTranslateApiResponseBody.get());
		} else {
			throw new ApiException("Failure contacting Translate API");
		}
	}
	
	private Optional<String> callTranslateApi(final String originalText) throws PokemonNotFoundException {
		try {
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> responseEntity = restTemplate
					.getForEntity(getTranslateApiEndopintUrl(originalText), String.class);
			LOG.info("Call to API returned 2xx HTTP status.");
			return Optional.of(responseEntity.getBody());
		} catch (HttpClientErrorException.BadRequest exc) {
			LOG.error("Bad request.");
			throw new ApiException();
		} catch (RestClientException exc) {
			LOG.error("An error occurred interacting with Translate Api.");
			LOG.error(exc.getMessage(), exc);
			return Optional.empty(); 
		}
	}
	
	/**
	 * Build Translate API
	 * {@code https://api.funtranslations.com/translate/shakespeare.json} URL.
	 * 
	 * @param originalText Text to translate.
	 * @return The API URL.
	 */
	private String getTranslateApiEndopintUrl(final String originalText) {
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(TRANSLATE_API_BASE_URL)
				.queryParam(QUERY_PARAM_TEXT, originalText);
		return uriComponentsBuilder.toUriString();
	}

	/**
	 * Process {@code https://api.funtranslations.com/translate/shakespeare.json}
	 * API response.
	 * 
	 * @param responseBody The Translate API response body, as a {@link String}.
	 * @return An {@link Optional} with the translated text, an empty
	 *         {@link Optional} if a JSON parsing error occurs.
	 */
	private Optional<String> processTranslateApiResponseBody(final String responseBody) {
		String translatedText = null;
		JsonNode responseAsJson = null;
		final ObjectMapper objectMapper = new ObjectMapper();
		try {
			responseAsJson = objectMapper.readTree(responseBody);
		} catch (JsonProcessingException exc) {
			LOG.error("An error occurred reading Translate API JSON response.");
			LOG.error(exc.getMessage(), exc);

		}
		if (responseAsJson != null) {
			if (responseAsJson.has("contents")) {
				JsonNode contentsNode = responseAsJson.get("contents");
				if (contentsNode.has("translated")) {
					String tmp = contentsNode.get("translated").asText();
					translatedText = tmp.replace("\n", " ").replace("\f", " ").replace("%20", " ");
				}
			}
		}
		return Optional.ofNullable(translatedText);
	}
	
}
