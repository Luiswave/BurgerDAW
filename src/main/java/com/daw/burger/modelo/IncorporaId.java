package com.daw.burger.modelo;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;

public class IncorporaId implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "idh")
	private Long idHamburguesa;

	@Column(name = "idi")
	private Long idIngrediente;
	
	public IncorporaId() {}

	public IncorporaId(Long idHamburguesa, Long idIngrediente) {
		super();
		this.idHamburguesa = idHamburguesa;
		this.idIngrediente = idIngrediente;
	}

	public Long getIdHamburguesa() {
		return idHamburguesa;
	}

	public Long getIdIngrediente() {
		return idIngrediente;
	}

	public void setIdHamburguesa(Long idHamburguesa) {
		this.idHamburguesa = idHamburguesa;
	}

	public void setIdIngrediente(Long idIngrediente) {
		this.idIngrediente = idIngrediente;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idHamburguesa, idIngrediente);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IncorporaId other = (IncorporaId) obj;
		return Objects.equals(idHamburguesa, other.idHamburguesa) && Objects.equals(idIngrediente, other.idIngrediente);
	}

}
