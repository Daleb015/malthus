package com.daleb.dto;

import java.util.List;

import lombok.Data;

@Data
public class DirichletResponseDTO {
  private int nodosErrados;
  private int nodos;
  private int decisiones;
  private int maxProfundid;
  private int trampas;
  private Double precision;
  private String msgError;
  private List<Solucion> soluciones;
 
}

