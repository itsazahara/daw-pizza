package com.daw.persistence.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "direccion")
@Getter
@Setter
@NoArgsConstructor
public class Direccion {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	@Column(length = 30, nullable = false)
    private String calle;
	
	@Column(length = 30, nullable = false)
	private String numero;
	
	@Column(length = 30, nullable = false)
    private String ciudad;
	
    private boolean activa;
    
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

}
