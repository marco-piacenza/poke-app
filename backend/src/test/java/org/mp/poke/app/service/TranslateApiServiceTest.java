package org.mp.poke.app.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mp.poke.app.exception.ApiException;

/**
 * Test class for {@link TranslateApiService}.
 * 
 * @author Marco Piacenza
 * @version 0.0.1
 * @since 0.0.1
 */
public class TranslateApiServiceTest {

	@InjectMocks
	private TranslateApiService translateApiService;
	
	@BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

	private static final String TEST_TEXT = "I'm your father, Luke";
	
	@Test
	public void testCallTranslateApi() throws Exception {
		try {
			Optional<String> translatedText = translateApiService.getShakespearText(TEST_TEXT);
			assertNotNull(translatedText, "expected non-null result");
			assertNotNull(translatedText.get(), "expected non-null description");
		} catch (Exception exc) {
			assertTrue(exc instanceof ApiException, "Expcted exception of type ApiException");
		}
	}
	
}
