package com.daw.burger.servicio;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.burger.modelo.Ingrediente;
import com.daw.burger.repositorio.IngredienteRepositorio;

@Service
public class IngredienteServicio {
	@Autowired
	private IngredienteRepositorio ingredienteRepositorio;
	
	public Collection<Ingrediente> getAll() {
		return ingredienteRepositorio.findAll();
	}
	
	public Ingrediente getById(Long id) {
		final Ingrediente unIngrediente = ingredienteRepositorio.findById(id).orElse(null);
		
		if (unIngrediente == null)
			throw new IngredienteException("No existe el ingrediente");
		
		return unIngrediente;
	}
	
	public Ingrediente create(Ingrediente item) {
		lanzarExcepcionSiYaExiste(item.getId());
		return ingredienteRepositorio.save(item);
	}

	public Ingrediente update(Ingrediente item) {
		lanzarExcepcionSiNoExiste(item.getId());
		return ingredienteRepositorio.save(item);
	}
	
	public void delete(Ingrediente item) {
		try {
			ingredienteRepositorio.delete(item);
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
			lanzarExcepcionNoSePuedeBorrar();
		}
	}
	
	public void deleteById(Long id) {
		try {
			ingredienteRepositorio.deleteById(id);;
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
			lanzarExcepcionNoSePuedeBorrar();
		}
	}
	
	// Métodos auxiliares
	private void lanzarExcepcionSiYaExiste(Long id) {
		if (id != null && ingredienteRepositorio.existsById(id))
			throw new IngredienteException("Ya existe el ingrediente");
	}

	private void lanzarExcepcionSiNoExiste(Long id) {
		if (id != null && !ingredienteRepositorio.existsById(id))
			throw new IngredienteException("No existe el ingrediente");
	}
	
	private void lanzarExcepcionNoSePuedeBorrar() {
		throw new IngredienteException("No se puede eliminar el ingrediente");
	}

}
