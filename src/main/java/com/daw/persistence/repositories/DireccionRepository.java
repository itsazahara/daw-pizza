package com.daw.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;

import com.daw.persistence.entities.Direccion;

public interface DireccionRepository extends ListCrudRepository<Direccion, Integer>{
	
	List<Direccion> findByActivaIsTrue();

}
