package com.daw.web.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daw.persistence.entities.Direccion;
import com.daw.services.DireccionService;

@RestController
@RequestMapping("/direcciones")
public class DireccionController {

	@Autowired
	private DireccionService direccionService;

	// Obtener todas las direcciones

	@GetMapping
	public ResponseEntity<List<Direccion>> list() {
		return ResponseEntity.ok(this.direccionService.findAll());
	}

	// Obtener una dirección según su ID

	@GetMapping("/{idDireccion}")
	public ResponseEntity<Direccion> findById(@PathVariable int idDireccion) {
		Optional<Direccion> direccion = this.direccionService.findById(idDireccion);
		if (direccion.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(direccion.get());
	}

	// Crear una direccion

	@PostMapping("/cliente/{idCliente}")
	public ResponseEntity<Direccion> crearDireccion(@PathVariable Integer idCliente, @RequestBody Direccion direccion) {
		Direccion nuevaDireccion = direccionService.crearDireccion(idCliente, direccion);
		return ResponseEntity.ok(nuevaDireccion);
	}

	// Actualizar o modificar una dirección

	@PutMapping("/{idDireccion}")
	public ResponseEntity<Direccion> update(@PathVariable int idDireccion, @RequestBody Direccion direccion) {
		if (idDireccion != direccion.getId()) {
			return ResponseEntity.badRequest().build();
		} else if (!this.direccionService.existsDireccion(idDireccion)) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(this.direccionService.save(direccion));
	}

	// Borrar una pizza

	@DeleteMapping("/{idDireccion}")
	public ResponseEntity<Direccion> delete(@PathVariable int idDireccion) {
		if (this.direccionService.delete(idDireccion)) {
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.notFound().build();
	}

	// Marcar o desmarcar como activa o desactiva

	@PostMapping("/{idDireccion}/toggle")
	public void toggleActiva(@PathVariable int idDireccion) {
		direccionService.toggleActiva(idDireccion);
	}

}
