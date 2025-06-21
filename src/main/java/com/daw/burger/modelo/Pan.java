package com.daw.burger.modelo;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Pan {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PAN_SEQ")
	@SequenceGenerator(name = "PAN_SEQ", allocationSize = 1)
	private Long id;

	@Column(nullable = false)
	private String descripcion;

	@Column(nullable = false)
	private boolean gluten;

	public Pan() {
	}

	public Pan(Long id, String descripcion, boolean gluten) {
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.gluten = gluten;
	}

	public Pan(String descripcion, boolean gluten) {
		super();
		this.descripcion = descripcion;
		this.gluten = gluten;
	}

	public Long getId() {
		return id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public boolean isGluten() {
		return gluten;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setGluten(boolean gluten) {
		this.gluten = gluten;
	}

	@Override
	public String toString() {
		return "Pan [id=" + id + ", descripcion=" + descripcion + ", gluten="
				+ gluten + "]";
	}

	@Override
	public int hashCode() {
		//return Objects.hash(descripcion, gluten, id);
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pan other = (Pan) obj;
		//return Objects.equals(descripcion, other.descripcion)
		//	&& gluten == other.gluten && Objects.equals(id, other.id);
		return Objects.equals(id, other.id);
	}


}
