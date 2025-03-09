package com.daw.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.persistence.entities.Cliente;
import com.daw.persistence.entities.Pedido;
import com.daw.persistence.entities.PizzaPedido;
import com.daw.persistence.repositories.PedidoRepository;
import com.daw.services.dtos.ClienteDTO;
import com.daw.services.dtos.PedidoDTO;
import com.daw.services.dtos.PizzaPedidoInputDTO;
import com.daw.services.dtos.PizzaPedidoOutputDTO;
import com.daw.services.mappers.PedidoMapper;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private PizzaPedidoService pizzaPedidoService;

	// CRUDs
	public List<PedidoDTO> findAll() {
		List<PedidoDTO> pedidosDTO = new ArrayList<PedidoDTO>();

		for (Pedido p : this.pedidoRepository.findAll()) {
			pedidosDTO.add(PedidoMapper.toDto(p));
		}

		return pedidosDTO;
	}

	public PedidoDTO findById(int idPedido) {
		return PedidoMapper.toDto(this.pedidoRepository.findById(idPedido).get());
	}

	public Optional<Pedido> findByIdEntity(int idPedido) {
		return this.pedidoRepository.findById(idPedido);
	}

	public boolean existsPedido(int idPedido) {
		return this.pedidoRepository.existsById(idPedido);
	}

	public PedidoDTO create(Pedido pedido) {
		pedido.setFecha(LocalDateTime.now());
		pedido.setTotal(0.0);

		pedido = this.pedidoRepository.save(pedido);

		ClienteDTO clienteDTO = this.clienteService.obtenerClientePorId(pedido.getIdCliente());
		Cliente cliente = new Cliente();
		cliente.setId(clienteDTO.getId());
		cliente.setNombre(clienteDTO.getNombre());
		cliente.setEmail(clienteDTO.getEmail());

		pedido.setCliente(cliente);		
		pedido.setPizzaPedidos(new ArrayList<PizzaPedido>());

		return PedidoMapper.toDto(pedido);
	}

	public Pedido update(Pedido pedido) {
		return this.pedidoRepository.save(pedido);
	}

	public boolean delete(int idPedido) {
		boolean result = false;

		if (this.pedidoRepository.existsById(idPedido)) {
			this.pedidoRepository.deleteById(idPedido);
			result = true;
		}

		return result;
	}

	public PizzaPedidoOutputDTO addModPizza(PizzaPedidoInputDTO inputDTO) {
		PizzaPedidoOutputDTO outDTO = this.pizzaPedidoService.save(inputDTO);

		this.actualizarTotal(inputDTO.getIdPedido());

		return outDTO;
	}

	public boolean deletePizza(int idPizzaPedido) {
		boolean result = this.pizzaPedidoService.delete(idPizzaPedido);

		if (result) {
			this.actualizarTotal(idPizzaPedido);
		}

		return result;
	}

	public void actualizarTotal(int idPedido) {
		Pedido pedido = this.pedidoRepository.findById(idPedido).get();
		Double total = 0.0;

		for (PizzaPedido pp : pedido.getPizzaPedidos()) {
			total += pp.getPrecio();
		}

		pedido.setTotal(total);

		this.pedidoRepository.save(pedido);
	}

	public List<Pedido> findByMetodo(String metodo) {
		return this.pedidoRepository.findByMetodo(metodo);
	}

	public List<Pedido> findByHoy(LocalDateTime fecha) {
		return this.pedidoRepository.findByFecha(fecha);
	}

	public List<Pedido> findByCliente(int cliente) {
		return this.pedidoRepository.findByIdCliente(cliente);
	}

	public Pedido repetirUltimoPedido(int clienteId) {
		Optional<Pedido> ultimoPedidoOpt = pedidoRepository.findLastPedidoByClienteId(clienteId);

		if (ultimoPedidoOpt.isEmpty()) {
			throw new RuntimeException("No se encontró ningún pedido para este cliente.");
		}

		Pedido ultimoPedido = ultimoPedidoOpt.get();

		Pedido nuevoPedido = new Pedido();
		nuevoPedido.setCliente(ultimoPedido.getCliente());
		nuevoPedido.setDetalles(ultimoPedido.getDetalles());
		nuevoPedido.setFecha(LocalDateTime.now());

		return pedidoRepository.save(nuevoPedido);
	}

}
