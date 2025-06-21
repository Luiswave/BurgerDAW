package com.daw.burger.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.daw.burger.modelo.Hamburguesa;
import com.daw.burger.modelo.Incorpora;
import com.daw.burger.modelo.Ingrediente;
import com.daw.burger.modelo.LineaIngrediente;
import com.daw.burger.modelo.Pan;
import com.daw.burger.servicio.HamburguesaException;
import com.daw.burger.servicio.HamburguesaServicio;
import com.daw.burger.servicio.IngredienteException;
import com.daw.burger.servicio.IngredienteServicio;
import com.daw.burger.servicio.PanServicio;

@Controller
@RequestMapping("/hamburguesa")
public class HamburguesaControlador {
	@Autowired
	private HamburguesaServicio hamburguesaServicio;

	@Autowired
	private PanServicio panServicio;

	@Autowired
	private IngredienteServicio ingredienteServicio;
	
	@GetMapping("/")
	public String listarHamburgesas(Model modelo) {
		modelo.addAttribute("listaHamburguesas", hamburguesaServicio.getAll());

		return "hamburguesa/listar";
	}

	@GetMapping("/{id}")
	public String verHamburguesa(
			@PathVariable(name = "id") Long id,
			Model modelo,
			RedirectAttributes redirAttrs) {
		
		Hamburguesa item;
		
		try {
			item = hamburguesaServicio.getById(id);
		}
		catch (HamburguesaException e) {
			mostrarAlerta(redirAttrs, e.getMessage());
			return "redirect:.";
		}
		
		modelo.addAttribute("hamburguesa", item);
		
		return "hamburguesa/ver";
	}

	@GetMapping("/borrar/{id}")
	public String borrarHamburguesa(
			@PathVariable(name = "id") Long id,
			Model modelo,
			RedirectAttributes redirAttrs) {
		
		try {
			modelo.addAttribute("hamburguesa", hamburguesaServicio.getById(id));
		}
		catch (HamburguesaException e) {
			mostrarAlerta(redirAttrs, e.getMessage());
			return "redirect:..";
		}

		return "hamburguesa/borrar";
	}

	@GetMapping("/borrar-definitivamente/{id}")
	public String borrarDefinitivamenteHamburguesa(
			@PathVariable(name = "id") Long id,
			RedirectAttributes redirAttrs) {

		try {
			hamburguesaServicio.deleteById(id);
		}
		catch (HamburguesaException e) {
			mostrarAlerta(redirAttrs, e.getMessage());
		}
		
		return "redirect:..";
	}

	@GetMapping("/nuevo/item")
	public String nuevaHamburguesa(Model modelo) {
		Hamburguesa item = new Hamburguesa("Nueva hamburguesa", 0.0, 
				new Pan("INDETERMINADO", true));

		modelo.addAttribute("hamburguesa", item);
		modelo.addAttribute("listaPanes", panServicio.getAll());

		return "hamburguesa/formulario";
	}

	@PostMapping("/nuevo/enviar")
	public String guardarNuevoHamburguesa(
			@ModelAttribute("hamburguesa") Hamburguesa hamburguesa,
			Model modelo,
			RedirectAttributes redirAttrs) {
		
		try {
			hamburguesaServicio.create(hamburguesa);
		}
		catch (HamburguesaException e) {
			mostrarAlerta(redirAttrs, e.getMessage());
		}

		return "redirect:..";
	}

	@GetMapping("/editar/{id}")
	public String editarHamburguesa(
			@PathVariable("id") Long id,
			Model modelo,
			RedirectAttributes redirAttrs) {
		
		Hamburguesa item;
		
		try {
			item = hamburguesaServicio.getById(id);
		}
		catch (HamburguesaException e) {
			mostrarAlerta(redirAttrs, e.getMessage());
			return "redirect:..";
		}

		modelo.addAttribute("hamburguesa", item);
		modelo.addAttribute("listaPanes", panServicio.getAll());

		return "hamburguesa/formulario";
	}

	@PostMapping("/editar/enviar")
	public String guardarHamburguesa(
			@ModelAttribute("hamburguesa") Hamburguesa hamburguesa,
			Model modelo,
			RedirectAttributes redirAttrs) {
	
		try {
			hamburguesaServicio.update(hamburguesa);
		}
		catch (HamburguesaException e) {
			mostrarAlerta(redirAttrs, e.getMessage());
		}

		return "redirect:..";
	}
	

	// Métodos auxiliares
	private void mostrarAlerta(RedirectAttributes redirAttrs, String mensaje) {
		redirAttrs.addFlashAttribute("error", true);
		redirAttrs.addFlashAttribute("mensaje", mensaje);
	}

}
