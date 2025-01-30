package com.daw.web.controllers;

import java.time.LocalDateTime;
import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.daw.persistence.entities.Pedido;
import com.daw.services.ClienteService;
import com.daw.services.PedidoService;
import com.daw.services.PizzaPedidoService;
import com.daw.services.PizzaService;
import com.daw.services.dtos.PedidoDTO;
import com.daw.services.dtos.PizzaPedidoInputDTO;
import com.daw.services.dtos.PizzaPedidoOutputDTO;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

	@Autowired
	private PedidoService pedidoService;

	@Autowired
	private PizzaPedidoService pizzaPedidoService;

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private PizzaService pizzaService;

	// Obtener todos los pedidos

	@GetMapping
	public ResponseEntity<List<PedidoDTO>> list() {
		return ResponseEntity.ok(this.pedidoService.findAll());
	}

	// Obtener un pedido según su ID

	@GetMapping("/{idPedido}")
	public ResponseEntity<PedidoDTO> findById(@PathVariable int idPedido) {
		if (this.pedidoService.existsPedido(idPedido)) {
			return ResponseEntity.ok(this.pedidoService.findById(idPedido));
		}

		return ResponseEntity.notFound().build();
	}

	// Crear un pedido

	@PostMapping
	public ResponseEntity<PedidoDTO> create(@RequestBody Pedido pedido) {
		if (!this.clienteService.existsCliente(pedido.getIdCliente())) {
			return ResponseEntity.notFound().build();
		}

		return new ResponseEntity<PedidoDTO>(this.pedidoService.create(pedido), HttpStatus.CREATED);
	}

	// Actualizar o modificar un pedido

	@PutMapping("/{idPedido}")
	public ResponseEntity<Pedido> update(@PathVariable int idPedido, @RequestBody Pedido pedido) {
		if (idPedido != pedido.getId()) {
			return ResponseEntity.badRequest().build();
		}
		if (!this.pedidoService.existsPedido(idPedido)) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(this.pedidoService.update(pedido));
	}

	// Borrar un pedido

	@DeleteMapping("/{idPedido}")
	public ResponseEntity<Pedido> delete(@PathVariable int idPedido) {
		if (this.pedidoService.delete(idPedido)) {
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.notFound().build();
	}

	// Obtener las pizzas de un pedido

	@GetMapping("/{idPedido}/pizzas")
	public ResponseEntity<List<PizzaPedidoOutputDTO>> listPizzas(@PathVariable int idPedido) {
		return ResponseEntity.ok(this.pizzaPedidoService.findByIdPedido(idPedido));
	}

	// Obtener una pizza de un pedido

	@GetMapping("/{idPedido}/pizzas/{idPizzaPedido}")
	public ResponseEntity<PizzaPedidoOutputDTO> findByIdPizza(@PathVariable int idPedido,
			@PathVariable int idPizzaPedido) {
		if (!this.pedidoService.existsPedido(idPedido)) {
			return ResponseEntity.notFound().build();
		}
		if (!this.pizzaPedidoService.existsPizzaPedido(idPizzaPedido)) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(this.pizzaPedidoService.findDTO(idPizzaPedido));
	}

	// Añadir una pizza a un pedido

	@PostMapping("/{idPedido}/pizzas")
	public ResponseEntity<PizzaPedidoOutputDTO> addPizza(@PathVariable int idPedido,
			@RequestBody PizzaPedidoInputDTO dto) {
		if (!this.pedidoService.existsPedido(idPedido)) {
			return ResponseEntity.notFound().build();
		}
		if (!this.pizzaService.existsPizza(dto.getIdPizza())) {
			return ResponseEntity.notFound().build();
		}

		return new ResponseEntity<PizzaPedidoOutputDTO>(this.pedidoService.addModPizza(dto), HttpStatus.CREATED);
	}

	// Modificar una pizza de un pedido

	@PutMapping("/{idPedido}/pizzas/{idPizzaPedido}")
	public ResponseEntity<PizzaPedidoOutputDTO> modPizza(@PathVariable int idPedido, @PathVariable int idPizzaPedido,
			@RequestBody PizzaPedidoInputDTO dto) {
		if (!this.pedidoService.existsPedido(idPedido)) {
			return ResponseEntity.notFound().build();
		}
		if (!this.pizzaPedidoService.existsPizzaPedido(idPizzaPedido)) {
			return ResponseEntity.notFound().build();
		}
		if (!this.pizzaService.existsPizza(dto.getIdPizza())) {
			return ResponseEntity.notFound().build();
		}
		if (idPizzaPedido != dto.getId()) {
			return ResponseEntity.badRequest().build();
		}
		if (idPedido != dto.getIdPedido()) {
			return ResponseEntity.badRequest().build();
		}

		return ResponseEntity.ok(this.pedidoService.addModPizza(dto));
	}

	// Borrar una pizza de un pedido

	@DeleteMapping("/{idPedido}/pizzas/{idPizzaPedido}")
	public ResponseEntity<PizzaPedidoOutputDTO> deletePizza(@PathVariable int idPedido,
			@PathVariable int idPizzaPedido) {
		if (!this.pedidoService.existsPedido(idPedido)) {
			return ResponseEntity.notFound().build();
		}
		if (this.pedidoService.deletePizza(idPizzaPedido)) {
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.notFound().build();
	}

	// Obtener los pedidos a domicilio

	@GetMapping("/domicilio")
	public ResponseEntity<List<Pedido>> findByDomicilio() {
		String metodo = "D";
		return ResponseEntity.ok(this.pedidoService.findByMetodo(metodo));
	}

	// Obtener los pedidos para llevar

	@GetMapping("/llevar")
	public ResponseEntity<List<Pedido>> findByLlevar() {
		String metodo = "L";
		return ResponseEntity.ok(this.pedidoService.findByMetodo(metodo));
	}

	// Obtener los pedidos consumidos en el local

	@GetMapping("/consumirLocal")
	public ResponseEntity<List<Pedido>> findByConsumirLocal() {
		String metodo = "C";
		return ResponseEntity.ok(this.pedidoService.findByMetodo(metodo));
	}

	// Obtener los pedidos de hoy

	@GetMapping("/hoy")
	public ResponseEntity<List<Pedido>> findByHoy() {
		LocalDateTime hoy = LocalDateTime.now();
		return ResponseEntity.ok(this.pedidoService.findByHoy(hoy));
	}

	// Obtener los pedidos de un cliente

	@GetMapping("/cliente/{idCliente}")
	public ResponseEntity<List<Pedido>> findByCliente(@PathVariable int idCliente) {
		if (!this.clienteService.existsCliente(idCliente)) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(this.pedidoService.findByCliente(idCliente));
	}

	// Repetir el último pedido de un cliente

	@PostMapping("/repetir/{clienteId}")
	public ResponseEntity<Pedido> repetirUltimoPedido(@PathVariable int clienteId) {
		Pedido nuevoPedido = pedidoService.repetirUltimoPedido(clienteId);
		return ResponseEntity.ok(nuevoPedido);
	}

}
