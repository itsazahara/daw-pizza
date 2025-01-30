package com.daw.persistence.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import com.daw.persistence.entities.Pedido;

public interface PedidoRepository extends ListCrudRepository<Pedido, Integer> {

	List<Pedido> findByMetodo(String metodo);

	List<Pedido> findByFecha(LocalDateTime metodo);

	List<Pedido> findByIdCliente(int idCliente);

	@Query("SELECT p FROM Pedido p WHERE p.cliente.id = :clienteId ORDER BY p.fecha DESC")
	Optional<Pedido> findLastPedidoByClienteId(@Param("clienteId") int clienteId);

}
