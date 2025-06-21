package com.daw.burger.modelo;

public class LineaIngrediente {
	private Long idIngredienteActual;
	private Long idIngredienteNuevo;
	private int cantidad;
	
	public LineaIngrediente() {}

	public LineaIngrediente(Long idIngredienteActual, Long idIngredienteNuevo, int cantidad) {
		super();
		this.idIngredienteActual = idIngredienteActual;
		this.idIngredienteNuevo = idIngredienteNuevo;
		this.cantidad = cantidad;
	}

	public Long getIdIngredienteActual() {
		return idIngredienteActual;
	}

	public Long getIdIngredienteNuevo() {
		return idIngredienteNuevo;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setIdIngredienteActual(Long idIngredienteActual) {
		this.idIngredienteActual = idIngredienteActual;
	}

	public void setIdIngredienteNuevo(Long idIngredienteNuevo) {
		this.idIngredienteNuevo = idIngredienteNuevo;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

}
