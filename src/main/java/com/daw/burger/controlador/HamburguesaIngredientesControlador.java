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
import com.daw.burger.servicio.HamburguesaException;
import com.daw.burger.servicio.HamburguesaServicio;
import com.daw.burger.servicio.IngredienteException;
import com.daw.burger.servicio.IngredienteServicio;
import com.daw.burger.servicio.PanServicio;

@Controller
@RequestMapping("/hamburguesa/ingredientes")
public class HamburguesaIngredientesControlador {
	@Autowired
	private HamburguesaServicio hamburguesaServicio;

	@Autowired
	private PanServicio panServicio;

	@Autowired
	private IngredienteServicio ingredienteServicio;
	
	@GetMapping("/{idh}")
	public String editarHamburguesaIngredientes(
			@PathVariable("idh") Long idHamburguesa,
			Model modelo,
			RedirectAttributes redirAttrs) {
		
		Hamburguesa item;
		
		try {
			item = hamburguesaServicio.getById(idHamburguesa);
		}
		catch (HamburguesaException e) {
			mostrarAlerta(redirAttrs, e.getMessage());
			return "redirect:..";
		}

		modelo.addAttribute("hamburguesa", item);
		modelo.addAttribute("listaPanes", panServicio.getAll());
		modelo.addAttribute("listaIncorpora", item.getListaIncorpora());

		return "hamburguesa/ingredientes/listar";
	}

	@GetMapping("/{idh}/borrar/{idi}")
	public String borrarHamburguesaIngrediente(
			@PathVariable(name = "idh") Long idHamburguesa,
			@PathVariable(name = "idi") Long idIngrediente,
			Model modelo,
			RedirectAttributes redirAttrs) {
		
		try {
			modelo.addAttribute("idHamburguesa", idHamburguesa);
			modelo.addAttribute("ingrediente", ingredienteServicio.getById(idIngrediente));
		}
		catch (HamburguesaException | IngredienteException e) {
			mostrarAlerta(redirAttrs, e.getMessage());
			return "redirect:..";
		}

		return "hamburguesa/ingredientes/borrar";
	}

	@GetMapping("/{idh}/borrar-definitivamente/{idi}")
	public String borrarDefinitivamenteHamburguesaIngrediente(
			@PathVariable(name = "idh") Long idHamburguesa,
			@PathVariable(name = "idi") Long idIngrediente,
			RedirectAttributes redirAttrs) {

		try {
			Hamburguesa hamburguesa = hamburguesaServicio.getById(idHamburguesa);
			Ingrediente ingrediente = ingredienteServicio.getById(idIngrediente);
			hamburguesa.removeIngrediente(ingrediente);
			hamburguesaServicio.update(hamburguesa);
		}
		catch (HamburguesaException | IngredienteException e) {
			mostrarAlerta(redirAttrs, e.getMessage());
		}
		
		return "redirect:../../" + idHamburguesa;
	}

	@GetMapping("/{idh}/nuevo/item")
	public String nuevaHamburguesaIngrediente(
			@PathVariable("idh") Long idHamburguesa,
			Model modelo,
			RedirectAttributes redirAttrs) {
		
		Hamburguesa unaHamburguesa;
		
		try {
			unaHamburguesa = hamburguesaServicio.getById(idHamburguesa);
		}
		catch (HamburguesaException e) {
			mostrarAlerta(redirAttrs, e.getMessage());
			return "redirect:..";
		}

		LineaIngrediente item = new LineaIngrediente(null, null, 1);

		modelo.addAttribute("hamburguesa", unaHamburguesa);
		modelo.addAttribute("listaIngredientes", ingredienteServicio.getAll());
		modelo.addAttribute("lineaIngrediente", item);

		return "hamburguesa/ingredientes/formulario";
	}

	@PostMapping("/{idh}/nuevo/enviar")
	public String guardarNuevoHamburguesaIngrediente(
			@PathVariable("idh") Long idHamburguesa,
			@ModelAttribute("lineaIngrediente") LineaIngrediente lineaIngrediente,
			Model modelo,
			RedirectAttributes redirAttrs) {
		
	
		try {
			Hamburguesa hamburguesa = hamburguesaServicio.getById(idHamburguesa);
			Ingrediente ingrediente = ingredienteServicio.getById(lineaIngrediente.getIdIngredienteNuevo());
			hamburguesa.addIngrediente(ingrediente, lineaIngrediente.getCantidad());
			hamburguesaServicio.update(hamburguesa);
		}
		catch (HamburguesaException | IngredienteException e) {
			mostrarAlerta(redirAttrs, e.getMessage());
		}

		return "redirect:../../" + idHamburguesa;
	}

	@GetMapping("/{idh}/editar/{idi}")
	public String editarHamburguesaIngrediente(
			@PathVariable("idh") Long idHamburguesa,
			@PathVariable("idi") Long idIngrediente,
			Model modelo,
			RedirectAttributes redirAttrs) {
		
		Hamburguesa unaHamburguesa;
		
		try {
			unaHamburguesa = hamburguesaServicio.getById(idHamburguesa);
		}
		catch (HamburguesaException e) {
			mostrarAlerta(redirAttrs, e.getMessage());
			return "redirect:..";
		}

		LineaIngrediente item = new LineaIngrediente();
		boolean encontrado = false;
		
		// Buscar el "Incorpora" (pareja hamburguesa-ingrediente) que tiene
		// el idHamburguesa y el idIngrediente a editar
		for (Incorpora incorpora : unaHamburguesa.getListaIncorpora()) {
			if (incorpora.getId().getIdHamburguesa() == idHamburguesa &&
					incorpora.getId().getIdIngrediente() == idIngrediente) {
				item.setIdIngredienteActual(incorpora.getIngrediente().getId());
				item.setIdIngredienteNuevo(incorpora.getIngrediente().getId());
				item.setCantidad(incorpora.getCantidad());
				encontrado = true;
			}
		}
		
		if (encontrado) { 
			modelo.addAttribute("hamburguesa", unaHamburguesa);
			modelo.addAttribute("listaIngredientes", ingredienteServicio.getAll());
			modelo.addAttribute("lineaIngrediente", item);
	
			return "hamburguesa/ingredientes/formulario";
		}
		else {
			mostrarAlerta(redirAttrs, "Ingrediente no encontrado");
			return "redirect:../../" + idHamburguesa;
		}
	}
	
	@PostMapping("/{idh}/editar/enviar")
	public String guardarHamburguesaIngrediente(
			@PathVariable("idh") Long idHamburguesa,
			@ModelAttribute("lineaIngrediente") LineaIngrediente lineaIngrediente,
			Model modelo,
			RedirectAttributes redirAttrs) {
		
	
		try {
			Hamburguesa hamburguesa = hamburguesaServicio.getById(idHamburguesa);
			Ingrediente nuevoIngrediente = ingredienteServicio.getById(lineaIngrediente.getIdIngredienteNuevo());
			Ingrediente actualIngrediente = ingredienteServicio.getById(lineaIngrediente.getIdIngredienteActual());
			
			hamburguesa.removeIngrediente(actualIngrediente);
			hamburguesa.addIngrediente(nuevoIngrediente, lineaIngrediente.getCantidad());

			hamburguesaServicio.update(hamburguesa);
		}
		catch (HamburguesaException | IngredienteException e) {
			mostrarAlerta(redirAttrs, e.getMessage());
		}

		return "redirect:../../" + idHamburguesa;
	}

	// Métodos auxiliares
	private void mostrarAlerta(RedirectAttributes redirAttrs, String mensaje) {
		redirAttrs.addFlashAttribute("error", true);
		redirAttrs.addFlashAttribute("mensaje", mensaje);
	}

}
