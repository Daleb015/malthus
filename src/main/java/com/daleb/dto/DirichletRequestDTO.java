package com.daleb.dto;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class DirichletRequestDTO {
  
  @Max(value=20,message = "maximas iteraciones en el vector r son 20")
  @Min(value=2,message = "minimas iteraciones en el vector r son 2")
  @NotNull(message = "El campo vector r no puede ser nulo")
  int vectorR = 10;
  
  @Max(value=20,message = "maximas iteraciones en el vector c son 20")
  @Min(value=2,message = "minimas iteraciones en el vector r son 2")
  @NotNull(message = "El campo vector c no puede ser nulo")
  int vectorC = 10;
  
  @DecimalMin(value = "0.001", inclusive = true, message = "El valor minimo de precision soportada es 0.001")
  @DecimalMax(value = "0.01", inclusive = true, message = "El valor maximo de precision soportada es 0.01")
  @NotNull(message = "El campo precision no puede ser nulo")
  double precision = 0;
  @DecimalMin(value = "0.0", inclusive = true, message = "El valor minimo del campo limite inicial es 0.0")
  @DecimalMax(value = "100.0", inclusive = true, message = "El valor maximo del campo limite inicial es 100.0")
  @NotNull(message = "El campo limite inicial no puede ser nulo")
  double limiteInferior = 0.0;
  @DecimalMin(value = "0.0", inclusive = true, message = "El valor minimo del campo limite final es 0.0")
  @DecimalMax(value = "100.0", inclusive = true, message = "El valor maximo del campo limite final es 100.0")
  @NotNull(message = "El campo limite final no puede ser nulo")
  double LimiteSuperior = 100.0;
}
