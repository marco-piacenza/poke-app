package org.mp.poke.app.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.mp.poke.app.service.PokeApiService;
import org.mp.poke.app.service.TranslateApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Test class of back-end controller.
 * 
 * @author Marco Piacenza
 * @version 0.0.1
 * @since 0.0.1
 */
@WebMvcTest
public class BackendControllerTest {

	private static final String POKEMON_SPECIES = "test";

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PokeApiService pokeApiService;

	@MockBean
	private TranslateApiService translateApiService;

	@Test
	public void testGetPokemonSpeciedDescription() throws Exception {
		Optional<String> pokemonDescriptionMocked = Optional.of("Mocked description");
		when(pokeApiService.getPokemonDescription(POKEMON_SPECIES)).thenReturn(pokemonDescriptionMocked);
		Optional<String> translatedDescriptionMocked = Optional.of("Translated text");
		when(translateApiService.getShakespearText(pokemonDescriptionMocked.get()))
				.thenReturn(translatedDescriptionMocked);
		MvcResult response = mockMvc.perform(get("/pokemon/" + POKEMON_SPECIES)).andExpect(status().isOk()).andReturn();
		assertNotNull(response, "expected non-null response");
		String responseAsString = response.getResponse().getContentAsString();
		assertNotNull(responseAsString, "expected nont-null response body");
		final ObjectMapper objectMapper = new ObjectMapper();
		JsonNode responseAsJson = objectMapper.readTree(responseAsString);
		assertTrue(responseAsJson.has("name"), "Expected field 'name' in response");
		assertTrue(responseAsJson.has("description"), "Expected field 'description' in response");
		assertEquals(POKEMON_SPECIES, responseAsJson.get("name").asText(), "Expected value: " + POKEMON_SPECIES);
		assertEquals("Translated text", responseAsJson.get("description").asText(), "Expected value: Translated text");
	}

	@Test
	public void testGetPokemonSpeciedDescriptionWithBlankNullInput() throws Exception {
		MvcResult response = mockMvc.perform(get("/pokemon/" + " ")).andExpect(status().is(HttpStatus.SC_NOT_FOUND))
				.andReturn();
		assertNotNull(response, "expected non-null response");
		String responseAsString = response.getResponse().getContentAsString();
		assertNotNull(responseAsString, "expected non-null response body");
		final ObjectMapper objectMapper = new ObjectMapper();
		JsonNode responseAsJson = objectMapper.readTree(responseAsString);
		assertTrue(responseAsJson.has("errorMessage"), "Expected field 'errorMessage' in response");
		assertEquals("Pokemon Not Found", responseAsJson.get("errorMessage").asText(),
				"Expected value: Pokemon Not Found");
	}

	@Test
	public void testGetPokemonSpeciedDescriptionThrowsExceptionOnPokeApiFails() throws Exception {
		Optional<String> pokemonDescriptionMocked = Optional.empty();
		when(pokeApiService.getPokemonDescription(POKEMON_SPECIES)).thenReturn(pokemonDescriptionMocked);
		MvcResult response = mockMvc.perform(get("/pokemon/" + POKEMON_SPECIES))
				.andExpect(status().is(HttpStatus.SC_INTERNAL_SERVER_ERROR)).andReturn();
		assertNotNull(response, "expected non-null response");
		String responseAsString = response.getResponse().getContentAsString();
		assertNotNull(responseAsString, "expected non-null response body");
		final ObjectMapper objectMapper = new ObjectMapper();
		JsonNode responseAsJson = objectMapper.readTree(responseAsString);
		assertTrue(responseAsJson.has("errorMessage"), "Expected field 'errorMessage' in response");
		assertEquals("Third-party API error", responseAsJson.get("errorMessage").asText(), "Expected value: Translated text");
	}

	@Test
	public void testGetPokemonSpeciedDescriptionThrowsExceptionOnTranslateApiFails() throws Exception {
		Optional<String> pokemonDescriptionMocked = Optional.of("Mocked description");
		when(pokeApiService.getPokemonDescription(POKEMON_SPECIES)).thenReturn(pokemonDescriptionMocked);
		Optional<String> translatedDescriptionMocked = Optional.empty();
		when(translateApiService.getShakespearText(pokemonDescriptionMocked.get()))
				.thenReturn(translatedDescriptionMocked);
		MvcResult response = mockMvc.perform(get("/pokemon/" + POKEMON_SPECIES))
				.andExpect(status().is(HttpStatus.SC_INTERNAL_SERVER_ERROR)).andReturn();
		assertNotNull(response, "expected non-null response");
		String responseAsString = response.getResponse().getContentAsString();
		assertNotNull(responseAsString, "expected non-null response body");
		final ObjectMapper objectMapper = new ObjectMapper();
		JsonNode responseAsJson = objectMapper.readTree(responseAsString);
		assertTrue(responseAsJson.has("errorMessage"), "Expected field 'errorMessage' in response");
		assertEquals("Third-party API error", responseAsJson.get("errorMessage").asText(), "Expected value: Translated text");
	}
	
}
