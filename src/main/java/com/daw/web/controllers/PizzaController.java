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

import com.daw.persistence.entities.Pizza;
import com.daw.services.PizzaService;

@RestController
@RequestMapping("/pizzas")
public class PizzaController {

	@Autowired
	private PizzaService pizzaService;

	// Obtener todas las pizzas

	@GetMapping
	public ResponseEntity<List<Pizza>> list() {
		return ResponseEntity.ok(this.pizzaService.findAll());
	}

	// Obtener una pizza según su ID

	@GetMapping("/{idPizza}")
	public ResponseEntity<Pizza> findById(@PathVariable int idPizza) {
		Optional<Pizza> pizza = this.pizzaService.findById(idPizza);
		if (pizza.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(pizza.get());
	}

	// Crear una pizza

	@PostMapping
	public ResponseEntity<Pizza> create(@RequestBody Pizza pizza) {
		return new ResponseEntity<Pizza>(this.pizzaService.create(pizza), HttpStatus.CREATED);
	}

	// Actualizar o modificar una pizza

	@PutMapping("/{idPizza}")
	public ResponseEntity<Pizza> update(@PathVariable int idPizza, @RequestBody Pizza pizza) {
		if (idPizza != pizza.getId()) {
			return ResponseEntity.badRequest().build();
		} else if (!this.pizzaService.existsPizza(idPizza)) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(this.pizzaService.save(pizza));
	}

	// Borrar una pizza

	@DeleteMapping("/{idPizza}")
	public ResponseEntity<Pizza> delete(@PathVariable int idPizza) {
		if (this.pizzaService.delete(idPizza)) {
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.notFound().build();
	}

	// Obtener las pizzas ordenadas

	@GetMapping("/carta")
	public ResponseEntity<List<Pizza>> carta() {
		return ResponseEntity.ok(this.pizzaService.getCarta());
	}

	// Obtener las pizzas por nombre

	@GetMapping("/nombre")
	public ResponseEntity<List<Pizza>> findByNombre(@RequestParam String nombre) {
		return ResponseEntity.ok(this.pizzaService.getByNombre(nombre));
	}

	// Obtener las pizzas con ingrediente

	@GetMapping("/conIngrediente")
	public ResponseEntity<List<Pizza>> findIngrediente(@RequestParam String ingrediente) {
		return ResponseEntity.ok(this.pizzaService.getIngrediente(ingrediente));
	}

	// Obtener las pizzas sin ingrediente

	@GetMapping("/sinIngrediente")
	public ResponseEntity<List<Pizza>> findSinIngrediente(@RequestParam String ingrediente) {
		return ResponseEntity.ok(this.pizzaService.getSinIngrediente(ingrediente));
	}

	// Actualizar el precio de una pizza

	@PutMapping("/{idPizza}/precio")
	public ResponseEntity<Pizza> actualizarPrecio(@PathVariable int idPizza, @RequestParam double nuevoPrecio) {
		if (this.pizzaService.existsPizza(idPizza)) {
			return ResponseEntity.ok(this.pizzaService.actualizarPrecio(idPizza, nuevoPrecio));
		}

		return ResponseEntity.notFound().build();
	}

	// Marcar una pizza como disponible o no disponible

	@PutMapping("/{idPizza}/disponible")
	public ResponseEntity<Pizza> marcarDesmarcarDisponible(@PathVariable int idPizza) {
		if (this.pizzaService.existsPizza(idPizza)) {
			return ResponseEntity.ok(this.pizzaService.marcarDesmarcarDisponible(idPizza));
		}

		return ResponseEntity.notFound().build();
	}

}
