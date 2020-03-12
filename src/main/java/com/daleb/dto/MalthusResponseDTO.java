package com.daleb.dto;

import lombok.Data;

@Data
public class MalthusResponseDTO {
	//Constante de crecimiento en proporcion a una hora
	private Double constanteCrecimiento;
	//Tiempo de generacion en horas
	private Double tiempoGeneracion;
	//Poblacion inicial
	private Double inoculo;
	//Cantidad de bacterias por ml
	private Double cantidad;
	
	private String msgError;
}
