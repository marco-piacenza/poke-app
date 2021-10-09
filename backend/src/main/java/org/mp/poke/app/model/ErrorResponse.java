package org.mp.poke.app.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Base POJO for sending error details to client.
 * 
 * @author Marco Piacenza
 * @version 0.0.1
 * @since 0.0.1
 */
public class ErrorResponse implements Serializable {

	private static final long serialVersionUID = -4763647585658309628L;

	private String errorMessage;

	public ErrorResponse() {
		this.errorMessage = null;
	}
	
	public ErrorResponse(String errorMessage) {
		super();
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Override
	public int hashCode() {
		return Objects.hash(errorMessage);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ErrorResponse other = (ErrorResponse) obj;
		return Objects.equals(errorMessage, other.errorMessage);
	}
	
}
