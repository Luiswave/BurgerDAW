package com.daw.burger.servicio;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.burger.modelo.Hamburguesa;
import com.daw.burger.repositorio.HamburguesaRepositorio;

@Service
public class HamburguesaServicio {
	@Autowired
	private HamburguesaRepositorio hamburguesaRepositorio;
	
	public Collection<Hamburguesa> getAll() {
		return hamburguesaRepositorio.findAll();
	}
	
	public Hamburguesa getById(Long id) {
		final Hamburguesa unHamburguesa = hamburguesaRepositorio.findById(id).orElse(null);
		
		if (unHamburguesa == null)
			lanzarExcepcionNoExiste();
		
		return unHamburguesa;
	}
	
	public Hamburguesa create(Hamburguesa item) {
		lanzarExcepcionCuandoYaExiste(item.getId());
		return hamburguesaRepositorio.save(item);
	}

	public Hamburguesa update(Hamburguesa item) {
		lanzarExcepcionCuandoNoExiste(item.getId());
		return hamburguesaRepositorio.save(item);
	}
	
	public void delete(Hamburguesa item) {
		try {
			hamburguesaRepositorio.delete(item);
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
			lanzarExcepcionNoSePuedeBorrar();
		}
	}
	
	public void deleteById(Long id) {
		try {
			hamburguesaRepositorio.deleteById(id);;
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
			lanzarExcepcionNoSePuedeBorrar();
		}
	}
	
	// Métodos auxiliares
	private void lanzarExcepcionYaExiste() {
		throw new HamburguesaException("Ya existe la hamburguesa");
	}
	
	private void lanzarExcepcionNoExiste() {
		throw new HamburguesaException("No existe la hamburguesa");
	}
	
	private void lanzarExcepcionNoSePuedeBorrar() {
		throw new HamburguesaException("No se puede eliminar la hamburguesa");
	}

	private void lanzarExcepcionCuandoYaExiste(Long id) {
		if (id != null && hamburguesaRepositorio.existsById(id))
			lanzarExcepcionYaExiste();
	}

	private void lanzarExcepcionCuandoNoExiste(Long id) {
		if (id != null && !hamburguesaRepositorio.existsById(id))
			lanzarExcepcionNoExiste();
	}

}
