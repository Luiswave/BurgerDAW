package com.daw.burger.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daw.burger.modelo.Ingrediente;

@Repository
public interface IngredienteRepositorio extends JpaRepository<Ingrediente, Long> {
}
