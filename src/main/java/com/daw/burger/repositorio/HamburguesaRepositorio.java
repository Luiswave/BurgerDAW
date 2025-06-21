package com.daw.burger.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.daw.burger.modelo.Hamburguesa;

public interface HamburguesaRepositorio extends JpaRepository<Hamburguesa, Long>{

}
