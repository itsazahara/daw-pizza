package com.daw.web.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.daw.persistence.entities.Cliente;
import com.daw.services.ClienteService;
import com.daw.services.dtos.ClienteDTO;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	// Obtener todos los clientes

	@GetMapping
	public ResponseEntity<List<Cliente>> list() {
		return ResponseEntity.ok(this.clienteService.findAll());
	}

	// Obtener un cliente según su ID (antiguo método)

	/*@GetMapping("/{idCliente}")
	public ResponseEntity<Cliente> findById(@PathVariable int idCliente) {
		Optional<Cliente> cliente = this.clienteService.findById(idCliente);
		if (cliente.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(cliente.get());
	}*/
	
	// Obtener un cliente según su ID con la dirección activa
	
	@GetMapping("/{idCliente}")
    public ClienteDTO obtenerCliente(@PathVariable int idCliente) {
        return clienteService.obtenerClientePorId(idCliente);
    }

	// Crear un cliente

	@PostMapping
	public ResponseEntity<Cliente> create(@RequestBody Cliente cliente) {
		return new ResponseEntity<Cliente>(this.clienteService.create(cliente), HttpStatus.CREATED);
	}

	// Actualizar o modificar un cliente

	@PutMapping("/{idCliente}")
	public ResponseEntity<Cliente> update(@PathVariable int idCliente, @RequestBody Cliente cliente) {
		if (idCliente != cliente.getId()) {
			return ResponseEntity.badRequest().build();
		} else if (!this.clienteService.existsCliente(idCliente)) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(this.clienteService.save(cliente));
	}

	// Borrar un cliente

	@DeleteMapping("/{idCliente}")
	public ResponseEntity<Cliente> delete(@PathVariable int idCliente) {
		if (this.clienteService.delete(idCliente)) {
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.notFound().build();
	}

	// Buscar a un cliente por su número de teléfono

	@GetMapping("/telefono")
	public ResponseEntity<List<Cliente>> findByTelefono(@RequestParam String telefono) {
		return ResponseEntity.ok(this.clienteService.getByTelefono(telefono));
	}

	// Top 3 de clientes

	/*
	 * @GetMapping("/top3") public List<Cliente> obtenerTop3Clientes() { return
	 * clienteService.obtenerTop3Clientes(); }
	 */

}