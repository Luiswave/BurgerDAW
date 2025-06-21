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

import com.daw.burger.modelo.Ingrediente;
import com.daw.burger.servicio.IngredienteException;
import com.daw.burger.servicio.IngredienteServicio;

@Controller
@RequestMapping("/ingrediente")
public class IngredienteControlador {
	@Autowired
	private IngredienteServicio ingredienteServicio;

	
	@GetMapping("/")
	public String listarPanes(Model modelo) {
		modelo.addAttribute("listaIngredientes", ingredienteServicio.getAll());

		return "ingrediente/listar";
	}

	@GetMapping("/{id}")
	public String verIngrediente(
			@PathVariable(name = "id") Long id,
			Model modelo,
			RedirectAttributes redirAttrs) {
		
		Ingrediente item;
		
		try {
			item = ingredienteServicio.getById(id);
		}
		catch (IngredienteException e) {
			mostrarAlerta(redirAttrs, e.getMessage());
			return "redirect:.";
		}
		
		modelo.addAttribute("ingrediente", item);
		
		return "ingrediente/ver";
	}

	@GetMapping("/borrar/{id}")
	public String borrarIngrediente(
			@PathVariable(name = "id") Long id,
			Model modelo,
			RedirectAttributes redirAttrs) {
		
		try {
			modelo.addAttribute("ingrediente", ingredienteServicio.getById(id));
		}
		catch (IngredienteException e) {
			mostrarAlerta(redirAttrs, e.getMessage());
			return "redirect:..";
		}

		return "ingrediente/borrar";
	}

	@GetMapping("/borrar-definitivamente/{id}")
	public String borrarDefinitivamenteIngrediente(
			@PathVariable(name = "id") Long id,
			RedirectAttributes redirAttrs) {

		try {
			ingredienteServicio.deleteById(id);
		}
		catch (IngredienteException e) {
			mostrarAlerta(redirAttrs, e.getMessage());
		}
		
		return "redirect:..";
	}

	@GetMapping("/nuevo/item")
	public String nuevoIngrediente(Model modelo) {
		Ingrediente item = new Ingrediente();
		
		modelo.addAttribute("ingrediente", item);

		return "ingrediente/formulario";
	}

	@PostMapping("/nuevo/enviar")
	public String guardarNuevoIngrediente(
			@ModelAttribute("ingrediente") Ingrediente ingrediente,
			Model modelo) {
		
		ingredienteServicio.create(ingrediente);

		return "redirect:..";
	}

	@GetMapping("/editar/{id}")
	public String editarIngrediente(
			@PathVariable("id") Long id,
			Model modelo,
			RedirectAttributes redirAttrs) {
		
		Ingrediente item;
		
		try {
			item = ingredienteServicio.getById(id);
		}
		catch (IngredienteException e) {
			mostrarAlerta(redirAttrs, e.getMessage());
			return "redirect:..";
		}
			
		modelo.addAttribute("ingrediente", item);

		return "ingrediente/formulario";
	}

	@PostMapping("/editar/enviar")
	public String guardarIngrediente(
			@ModelAttribute("ingrediente") Ingrediente ingrediente,
			Model modelo,
			RedirectAttributes redirAttrs) {
		
			try {
				ingredienteServicio.update(ingrediente);
			}
			catch (IngredienteException e) {
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
