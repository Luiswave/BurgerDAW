package com.daw.burger.modelo;

import java.util.Objects;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Incorpora {
	@EmbeddedId
	private IncorporaId id;
	
    @ManyToOne
	@JoinColumn(name = "idh", insertable = false, updatable = false)
	private Hamburguesa hamburguesa;

	@ManyToOne
	@JoinColumn(name = "idi", insertable = false, updatable = false)
	private Ingrediente ingrediente;

	private int cantidad;

	public Incorpora() { }

	public Incorpora(Hamburguesa hamburguesa, Ingrediente ingrediente, int cantidad) {
		super();
		this.hamburguesa = hamburguesa;
		this.ingrediente = ingrediente;
		this.cantidad = cantidad;
		this.id = new IncorporaId(hamburguesa.getId(), ingrediente.getId());
	}

	public IncorporaId getId() {
		return id;
	}

	public Hamburguesa getHamburguesa() {
		return hamburguesa;
	}

	public Ingrediente getIngrediente() {
		return ingrediente;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setId(IncorporaId id) {
		this.id = id;
	}

	public void setHamburguesa(Hamburguesa hamburguesa) {
		this.hamburguesa = hamburguesa;
	}

	public void setIngrediente(Ingrediente ingrediente) {
		this.ingrediente = ingrediente;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	@Override
	public String toString() {
		return "Incorpora [hamburguesa=" + hamburguesa + ", ingrediente=" + ingrediente + ", cantidad=" + cantidad
				+ "]";
	}

	@Override
	public int hashCode() {
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
		Incorpora other = (Incorpora) obj;
		return Objects.equals(id, other.id);
	}

}
