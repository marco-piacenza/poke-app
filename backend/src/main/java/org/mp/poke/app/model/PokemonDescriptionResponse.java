package org.mp.poke.app.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * POJO that represents a basic pokemon species description.
 * 
 * @author Marco Piacenza
 * @version 0.0.1
 * @since 0.0.1
 */
public class PokemonDescriptionResponse implements Serializable {

	private static final long serialVersionUID = 4172897978859342569L;

	private String name;

	private String description;

	public PokemonDescriptionResponse() {
		this.name = null;
		this.description = null;
	}

	public PokemonDescriptionResponse(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PokemonDescriptionResponse other = (PokemonDescriptionResponse) obj;
		return Objects.equals(description, other.description) && Objects.equals(name, other.name);
	}

}
