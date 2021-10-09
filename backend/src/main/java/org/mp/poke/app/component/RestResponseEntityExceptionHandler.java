package org.mp.poke.app.component;

import java.util.Optional;

import org.mp.poke.app.exception.ApiException;
import org.mp.poke.app.exception.PokemonNotFoundException;
import org.mp.poke.app.model.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { PokemonNotFoundException.class })
	protected ResponseEntity<Object> handlePokemonNotFound(RuntimeException ex, WebRequest request) {
		String errorMessage = "Pokemon Not Found";
		ErrorResponse errorResponse = new ErrorResponse(errorMessage);
		return handleExceptionInternal(ex, convertIntoJson(errorResponse).orElse(""), createStandardHttpHeader(),
				HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler(value = { ApiException.class })
	protected ResponseEntity<Object> handleApiexception(RuntimeException ex, WebRequest request) {
		String errorMessage = "Third-party API error";
		ErrorResponse errorResponse = new ErrorResponse(errorMessage);
		return handleExceptionInternal(ex, convertIntoJson(errorResponse).orElse(""), createStandardHttpHeader(),
				HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	private Optional<String> convertIntoJson(ErrorResponse errorResponse) {
		final ObjectMapper objectMapper = new ObjectMapper();
		try {
			return Optional.of(objectMapper.writeValueAsString(errorResponse));
		} catch (Exception exc) {
			return Optional.empty();
		}
	}

	private HttpHeaders createStandardHttpHeader() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
		return httpHeaders;
	}

}
