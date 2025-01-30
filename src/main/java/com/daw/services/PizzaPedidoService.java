package com.daw.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.persistence.entities.Pizza;
import com.daw.persistence.entities.PizzaPedido;
import com.daw.persistence.repositories.PizzaPedidoRepository;
import com.daw.services.dtos.PizzaPedidoInputDTO;
import com.daw.services.dtos.PizzaPedidoOutputDTO;
import com.daw.services.mappers.PizzaPedidoMapper;

@Service
public class PizzaPedidoService {
	
	@Autowired
	private PizzaPedidoRepository pizzaPedidoRepository;	
	
	@Autowired
	private PizzaService pizzaService;

	public List<PizzaPedido> findAll(){
		return this.pizzaPedidoRepository.findAll();
	}
	
	public boolean existsPizzaPedido(int idPizzaPedido){
		return this.pizzaPedidoRepository.existsById(idPizzaPedido);
	}
	
	public Optional<PizzaPedido> findById(int idPizzaPedido){
		return this.pizzaPedidoRepository.findById(idPizzaPedido);
	}
	
	public PizzaPedido create(PizzaPedido pizzaPedido){		
		return this.pizzaPedidoRepository.save(pizzaPedido);
	}
	
	public PizzaPedido save(PizzaPedido pizzaPedido){
		return this.pizzaPedidoRepository.save(pizzaPedido);
	}
	
	public boolean delete(int idPizzaPedido) {		
		boolean result = false;
		
		if(this.pizzaPedidoRepository.existsById(idPizzaPedido)) {
			this.pizzaPedidoRepository.deleteById(idPizzaPedido);
			result = true;
		}
		
		return result;
	}
	
	public List<PizzaPedidoOutputDTO> findByIdPedido(int idPedido){
		List<PizzaPedidoOutputDTO> dtos = new ArrayList<PizzaPedidoOutputDTO>();
		
		for(PizzaPedido pp : this.pizzaPedidoRepository.findByIdPedido(idPedido)) {
			dtos.add(PizzaPedidoMapper.toDTO(pp));
		}
		
		return dtos;
	}
	
	public PizzaPedidoOutputDTO findDTO(int idPizza) {
		PizzaPedido pp = this.pizzaPedidoRepository.findById(idPizza).get();
		
		return PizzaPedidoMapper.toDTO(pp);
	}
	
	public PizzaPedidoOutputDTO save(PizzaPedidoInputDTO inputDTO) {
		PizzaPedido entity = PizzaPedidoMapper.toEntity(inputDTO);
		
		Pizza pizza = this.pizzaService.findById(entity.getIdPizza()).get();
		entity.setPrecio(entity.getCantidad() * pizza.getPrecio());
		
		entity = this.pizzaPedidoRepository.save(entity);
		
		entity.setPizza(pizza);		
		
		return PizzaPedidoMapper.toDTO(entity);
	}

}
