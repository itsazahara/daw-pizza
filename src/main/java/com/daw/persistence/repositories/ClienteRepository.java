package com.daw.persistence.repositories;

import java.awt.print.Pageable;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;

import com.daw.persistence.entities.Cliente;

public interface ClienteRepository extends ListCrudRepository<Cliente, Integer> {

	List<Cliente> findByTelefonoContaining(String telefono);

	@Query("SELECT c FROM Cliente c ORDER BY c.totalCompras DESC")
	List<Cliente> findTop3Clientes(Pageable pageable);
}
