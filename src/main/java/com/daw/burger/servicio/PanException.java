package com.daw.burger.servicio;

public class PanException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PanException(String mensaje) {
		super(mensaje);
	}
}
