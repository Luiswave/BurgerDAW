package com.daw.burger.repositorio;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.daw.burger.modelo.Pan;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
@Qualifier("jpa")
//public class PanRepositorioJPAImpl implements PanRepositorio {
public class PanRepositorioJPAImpl {
	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
//	@Override
	public Collection<Pan> getAll() {
		return entityManager.createQuery("from Pan").getResultList();
	}

//	@Override
	public Pan getById(Long id) {
		return entityManager.find(Pan.class, id);
	}

//	@Override
	public void create(Pan item) {
		entityManager.persist(item);
	}

//	@Override
	public Pan update(Pan item) {
		return entityManager.merge(item);
	}

//	@Override
	public void delete(Pan item) {
		if (entityManager.contains(item))
			entityManager.remove(item);
		else
			entityManager.remove(entityManager.merge(item));
	}

}
