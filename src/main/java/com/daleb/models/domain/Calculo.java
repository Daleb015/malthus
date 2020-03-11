package com.daleb.models.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
public class Calculo implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long id;

	private Double tiempo1;

	private Double tiempo2;

	private Double poblacion1;
	
	private Double poblacion2;

	private Double tiempo;

	private Double constanteCrecimiento;

	private Double tiempoGeneracion;

	private Double inoculo;

	private Double cantidad;

}
