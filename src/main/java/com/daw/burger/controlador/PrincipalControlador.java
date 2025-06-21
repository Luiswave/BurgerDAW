package com.daw.burger.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.daw.burger.modelo.Hamburguesa;
import com.daw.burger.modelo.Pan;
import com.daw.burger.repositorio.HamburguesaRepositorio;
import com.daw.burger.repositorio.PanRepositorio;

@Controller
@RequestMapping("/")
public class PrincipalControlador {
	@Autowired
	HamburguesaRepositorio hamburguesaRepositorio;

	@Autowired
	PanRepositorio panRepositorio;

	@GetMapping("/")
	public String index() {
		return "base";
	}

	@GetMapping("/test")
	public String test() {
		
		Pan unPan = new Pan("Tradicional", true);
		Hamburguesa unaHamburguesa = new Hamburguesa("Clásica", 4.50, unPan);
		
		panRepositorio.save(unPan);
		hamburguesaRepositorio.save(unaHamburguesa);

		return "base";
	}
}
