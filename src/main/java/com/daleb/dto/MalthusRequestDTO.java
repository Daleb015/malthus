package com.daleb.dto;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class MalthusRequestDTO {

  @DecimalMin(value = "1.0", inclusive = true, message = "El valor minimo del campo tiempo 1 es 1.0")
  @DecimalMax(value = "50.0", inclusive = true, message = "El valor maximo del campo tiempo 1 es 50.0")
  @NotNull(message = "El campo tiempo 1 no puede ser nulo")
  private Double tiempo1;
  @DecimalMin(value = "1.0", inclusive = true, message = "El valor minimo del campo tiempo 2 es 1.0")
  @DecimalMax(value = "50.0", inclusive = true, message = "El valor maximo del campo tiempo 2 es 50.0")
  @NotNull(message = "El campo tiempo 2 no puede ser nulo")
  private Double tiempo2;
  @DecimalMin(value = "1.0", inclusive = true, message = "El valor minimo del campo poblacion 1 es 1.0")
  @DecimalMax(value = "12000.0", inclusive = true, message = "El valor maximo del campo poblacion 1 es 50.0")
  @NotNull(message = "El campo poblacion 1 no puede ser nulo")
  private Double poblacion1;
  @DecimalMin(value = "1.0", inclusive = true, message = "El valor minimo del campo poblacion 2 es 1.0")
  @DecimalMax(value = "12000.0", inclusive = true, message = "El valor maximo del campo poblacion 2 es 12000.0")
  @NotNull(message = "El campo poblacion 2 no puede ser nulo")
  private Double poblacion2;
  @DecimalMin(value = "1.0", inclusive = true, message = "El valor minimo del campo tiempo es 1.0")
  @DecimalMax(value = "50.0", inclusive = true, message = "El valor maximo del campo tiempo es 50.0")
  @NotNull(message = "El campo tiempo no puede ser nulo")
  private Double tiempo;
}
