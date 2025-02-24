package com.daw.services.dtos;

import com.daw.persistence.entities.Direccion;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClienteDTO {
	
	private Integer id;
    private String nombre;
    private String email;
    private String telefono;
    private Direccion direccionActiva;

}
