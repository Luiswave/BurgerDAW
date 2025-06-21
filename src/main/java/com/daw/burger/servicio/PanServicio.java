package com.daw.burger.servicio;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.burger.modelo.Pan;
import com.daw.burger.repositorio.PanRepositorio;

@Service
public class PanServicio {
	@Autowired
	private PanRepositorio panRepositorio;
	
	public Collection<Pan> getAll() {
		return panRepositorio.findAll();
	}
	
	public Pan getById(Long id) {
		final Pan unPan = panRepositorio.findById(id).orElse(null);
		
		if (unPan == null)
			lanzarExcepcionNoExiste();
		
		return unPan;
	}
	
	public Pan create(Pan item) {
		lanzarExcepcionCuandoYaExiste(item.getId());
		return panRepositorio.save(item);
	}

	public Pan update(Pan item) {
		lanzarExcepcionCuandoNoExiste(item.getId());
		return panRepositorio.save(item);
	}
	
	public void delete(Pan item) {
		try {
			panRepositorio.delete(item);
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
			lanzarExcepcionNoSePuedeBorrar();
		}
	}
	
	public void deleteById(Long id) {
		try {
			panRepositorio.deleteById(id);;
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
			lanzarExcepcionNoSePuedeBorrar();
		}
	}
	
	public Pan findByDescripcion(String descripcion) {
		return panRepositorio.findByDescripcion(descripcion);
	}

	// Métodos auxiliares
	private void lanzarExcepcionYaExiste() {
		throw new PanException("Ya existe el pan");
	}
	
	private void lanzarExcepcionNoExiste() {
		throw new PanException("No existe el pan");
	}
	
	private void lanzarExcepcionNoSePuedeBorrar() {
		throw new PanException("No se puede eliminar el pan");
	}

	private void lanzarExcepcionCuandoYaExiste(Long id) {
		if (id != null && panRepositorio.existsById(id))
			lanzarExcepcionYaExiste();
	}

	private void lanzarExcepcionCuandoNoExiste(Long id) {
		if (id != null && !panRepositorio.existsById(id))
			lanzarExcepcionNoExiste();
	}
	
}
