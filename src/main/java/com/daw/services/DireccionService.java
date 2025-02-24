package com.daw.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.persistence.entities.Cliente;
import com.daw.persistence.entities.Direccion;
import com.daw.persistence.repositories.ClienteRepository;
import com.daw.persistence.repositories.DireccionRepository;

@Service
public class DireccionService {

	@Autowired
	private DireccionRepository direccionRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	public List<Direccion> findAll() {
		return this.direccionRepository.findAll();
	}

	public boolean existsDireccion(int idDireccion) {
		return this.direccionRepository.existsById(idDireccion);
	}

	public Optional<Direccion> findById(int idDireccion) {
		return this.direccionRepository.findById(idDireccion);
	}

	public Direccion crearDireccion(Integer idCliente, Direccion direccion) {
		Cliente cliente = clienteRepository.findById(idCliente)
				.orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

		List<Direccion> direcciones = cliente.getDirecciones();
		for (Direccion d : direcciones) {
			d.setActiva(false);
			direccionRepository.save(d);
		}

		direccion.setCliente(cliente);
		direccion.setActiva(true);

		return direccionRepository.save(direccion);
	}

	public Direccion save(Direccion direccion) {
		return this.direccionRepository.save(direccion);
	}

	public boolean delete(int idDireccion) {
		boolean result = false;

		if (this.direccionRepository.existsById(idDireccion)) {
			this.direccionRepository.deleteById(idDireccion);
			result = true;
		}

		return result;
	}

	public void toggleActiva(Integer idDireccion) {
		Direccion direccion = direccionRepository.findById(idDireccion)
				.orElseThrow(() -> new RuntimeException("Dirección no encontrada"));

		if (direccion.isActiva()) {
			direccion.setActiva(false);
		} else {
			direccion.setActiva(true);

			List<Direccion> direcciones = direccion.getCliente().getDirecciones();
			for (Direccion d : direcciones) {
				if (!d.getId().equals(idDireccion)) {
					d.setActiva(false);
					direccionRepository.save(d);
				}
			}
		}

		direccionRepository.save(direccion);
	}

}
