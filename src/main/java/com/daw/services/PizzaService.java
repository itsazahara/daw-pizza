package com.daw.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.persistence.entities.Pizza;
import com.daw.persistence.repositories.PizzaRepository;

@Service
public class PizzaService {

	@Autowired
	private PizzaRepository pizzaRepository;

	public List<Pizza> findAll() {
		return this.pizzaRepository.findAll();
	}

	public boolean existsPizza(int idPizza) {
		return this.pizzaRepository.existsById(idPizza);
	}

	public Optional<Pizza> findById(int idPizza) {
		return this.pizzaRepository.findById(idPizza);
	}

	public Pizza create(Pizza pizza) {
		return this.pizzaRepository.save(pizza);
	}

	public Pizza save(Pizza pizza) {
		return this.pizzaRepository.save(pizza);
	}

	public boolean delete(int idPizza) {
		boolean result = false;

		if (this.pizzaRepository.existsById(idPizza)) {
			this.pizzaRepository.deleteById(idPizza);
			result = true;
		}

		return result;
	}

	public boolean exists(int idPizza) {
		if (this.pizzaRepository.existsById(idPizza)) {
			return true;
		}
		return false;
	}

	public List<Pizza> getCarta() {
		return this.pizzaRepository.findByDisponibleTrueOrderByPrecioAsc();
	}

	public List<Pizza> getByNombre(String nombre) {
		return this.pizzaRepository.findByDisponibleTrueAndNombreContaining(nombre);
	}

	public List<Pizza> getIngrediente(String ingrediente) {
		return this.pizzaRepository.findByDescripcionContaining(ingrediente);
	}

	public List<Pizza> getSinIngrediente(String ingrediente) {
		return this.pizzaRepository.findByDescripcionNotContaining(ingrediente);
	}

	public Pizza actualizarPrecio(int idPizza, double nuevoPrecio) {
		Pizza pizza = this.pizzaRepository.findById(idPizza).get();

		pizza.setPrecio(nuevoPrecio);
		pizza = this.pizzaRepository.save(pizza);

		return pizza;
	}

	public Pizza marcarDesmarcarDisponible(int idPizza) {
		Pizza pizza = this.pizzaRepository.findById(idPizza).get();

		if (pizza.getDisponible()) {
			pizza.setDisponible(false);
		} else {
			pizza.setDisponible(true);
		}

		pizza = this.pizzaRepository.save(pizza);

		return pizza;
	}

	// Actualizar el precio de una pizza

	public Pizza updatePrecio(int idPizza, double precio) {

		Pizza pizza = this.findById(idPizza).get();
		pizza.setPrecio(precio);
		pizza = this.pizzaRepository.save(pizza);
		return pizza;

	}

	// Me falta marcar pizza como disponible o no disponible

}