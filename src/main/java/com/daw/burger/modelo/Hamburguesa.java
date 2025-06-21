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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Hamburguesa {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HAMBURGUESA_SEQ")
	@SequenceGenerator(name = "HAMBURGUESA_SEQ", allocationSize = 1)
	private Long id;
	
	@Column(nullable = false)
	private String nombre;
	
	@Column(nullable = false)
	private double precio;
	
	@ManyToOne
	@JoinColumn(name = "idp")
	private Pan pan;
	
	@OneToMany(mappedBy="hamburguesa", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Incorpora> listaIncorpora = new HashSet<>();
	
	public Hamburguesa() {}

	public Hamburguesa(Long id, String nombre, double precio, Pan pan) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.precio = precio;
		this.pan = pan;
	}

	public Hamburguesa(String nombre, double precio, Pan pan) {
		super();
		this.nombre = nombre;
		this.precio = precio;
		this.pan = pan;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public double getPrecio() {
		return precio;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public Pan getPan() {
		return pan;
	}

	public void setPan(Pan pan) {
		this.pan = pan;
	}

	public Set<Incorpora> getListaIncorpora() {
		return listaIncorpora;
	}

	public void addIngrediente(Ingrediente ingrediente, int cantidad) {
		Incorpora incorpora = new Incorpora(this, ingrediente, cantidad);
		listaIncorpora.add(incorpora);
		// Da un error de objeto duplicado con la misma clave 
		//ingrediente.getListaIncorpora().add(incorpora);
	}

	public void removeIngrediente(Ingrediente ingrediente) {
		Incorpora incorpora = new Incorpora(this, ingrediente, 0);
		listaIncorpora.remove(incorpora);
		// Da un error de objeto duplicado con la misma clave 
		//ingrediente.getListaIncorpora().remove(incorpora);
	}

	public List<String>getCadenaIngredientes() {
		List<String> listaIngredientes = new ArrayList<>();
		String cadena;
		
		for (Incorpora i : listaIncorpora) {
			cadena = "" + i.getCantidad() + " " +i.getIngrediente().getDescripcion(); 
			listaIngredientes.add(cadena);
		}
		
		return listaIngredientes;
	}

	@Override
	public String toString() {
		return "Hamburguesa [id=" + id + ", nombre=" + nombre + ", precio="
				+ precio + ", pan=" + pan.getDescripcion() + "]";
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
		Hamburguesa other = (Hamburguesa) obj;
		return Objects.equals(id, other.id);
	}

}

