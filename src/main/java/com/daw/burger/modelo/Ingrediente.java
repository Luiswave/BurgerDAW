package com.daw.burger.modelo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Ingrediente {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "INGREDIENTE_SEQ")
	@SequenceGenerator(name = "INGREDIENTE_SEQ", allocationSize = 1)
	private Long id;

	@Column(nullable = false)
	private String descripcion;

	@Column(nullable = false)
	private int peso;
	
	@OneToMany(mappedBy="ingrediente", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Incorpora> listaIncorpora = new HashSet<>();
	
	public Ingrediente() {}

	public Ingrediente(Long id, String descripcion, int peso) {
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.peso = peso;
	}

	public Ingrediente(String descripcion, int peso) {
		super();
		this.descripcion = descripcion;
		this.peso = peso;
	}

	public Long getId() {
		return id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public int getPeso() {
		return peso;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	public Set<Incorpora> getListaIncorpora() {
		return listaIncorpora;
	}

	public List<String>getCadenaHamburguesas() {
		List<String> listaHamburguesas = new ArrayList<>();
		String cadena;
		
		for (Incorpora i : listaIncorpora) {
			cadena = i.getHamburguesa().getNombre();
			listaHamburguesas.add(cadena);
		}
		
		return listaHamburguesas;
	}

	@Override
	public String toString() {
		return "Ingrediente [id=" + id + ", descripcion=" + descripcion
				+ ", peso=" + peso + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ingrediente other = (Ingrediente) obj;
		return Objects.equals(id, other.id);
	}

}
