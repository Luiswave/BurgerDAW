package com.daw.burger.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.daw.burger.modelo.Pan;

@Repository
public interface PanRepositorio extends JpaRepository<Pan, Long> {
	Pan findByDescripcion(@Param("descripcion") String descripcion);
}
