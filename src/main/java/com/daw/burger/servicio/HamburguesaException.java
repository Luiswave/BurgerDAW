package com.daw.burger.servicio;

public class HamburguesaException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public HamburguesaException(String mensaje) {
		super(mensaje);
	}
}
