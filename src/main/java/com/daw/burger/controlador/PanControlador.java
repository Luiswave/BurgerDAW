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

import com.daw.burger.modelo.Pan;
import com.daw.burger.servicio.PanException;
import com.daw.burger.servicio.PanServicio;

@Controller
@RequestMapping("/pan")
public class PanControlador {
	@Autowired
	private PanServicio panServicio;

	
	@GetMapping("/")
	public String listarPanes(Model modelo) {
		modelo.addAttribute("listaPanes", panServicio.getAll());

		return "pan/listar";
	}

	@GetMapping("/{id}")
	public String verPan(
			@PathVariable(name = "id") Long id,
			Model modelo,
			RedirectAttributes redirAttrs) {
		
		Pan item;
		
		try {
			item = panServicio.getById(id);
		}
		catch (PanException e) {
			mostrarAlerta(redirAttrs, e.getMessage());
			return "redirect:.";
		}
		
		modelo.addAttribute("pan", item);
		
		return "pan/ver";
	}

	@GetMapping("/borrar/{id}")
	public String borrarPan(
			@PathVariable(name = "id") Long id,
			Model modelo,
			RedirectAttributes redirAttrs) {
		
		try {
			modelo.addAttribute("pan", panServicio.getById(id));
		}
		catch (PanException e) {
			mostrarAlerta(redirAttrs, e.getMessage());
			return "redirect:..";
		}

		return "pan/borrar";
	}

	@GetMapping("/borrar-definitivamente/{id}")
	public String borrarDefinitivamentePan(
			@PathVariable(name = "id") Long id,
			RedirectAttributes redirAttrs) {

		try {
			panServicio.deleteById(id);
		}
		catch (PanException e) {
			mostrarAlerta(redirAttrs, e.getMessage());
		}
		
		return "redirect:..";
	}

	@GetMapping("/nuevo/item")
	public String nuevoPan(Model modelo) {
		Pan item = new Pan();
		
		item.setGluten(true);
		
		modelo.addAttribute("pan", item);

		return "pan/formulario";
	}

	@PostMapping("/nuevo/enviar")
	public String guardarNuevoPan(
			@ModelAttribute("pan") Pan pan,
			Model modelo,
			RedirectAttributes redirAttrs) {

		try {
			panServicio.create(pan);
		}
		catch (PanException e) {
			mostrarAlerta(redirAttrs, e.getMessage());
		}

		return "redirect:..";
	}

	@GetMapping("/editar/{id}")
	public String editarPan(
			@PathVariable("id") Long id,
			Model modelo,
			RedirectAttributes redirAttrs) {
		
		Pan item;
		
		try {
			item = panServicio.getById(id);
		}
		catch (PanException e) {
			mostrarAlerta(redirAttrs, e.getMessage());
			return "redirect:..";
		}
			
		modelo.addAttribute("pan", item);

		return "pan/formulario";
	}

	@PostMapping("/editar/enviar")
	public String guardarPan(
			@ModelAttribute("pan") Pan pan,
			Model modelo,
			RedirectAttributes redirAttrs) {
		
		try {
			panServicio.update(pan);
		}
		catch (PanException e) {
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
