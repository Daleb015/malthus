package com.daleb.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daleb.dto.MalthusRequestDTO;
import com.daleb.dto.MalthusResponseDTO;
import com.daleb.models.domain.Calculo;
import com.daleb.services.impl.CalculoServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("malthus/api/")
@Api(tags = "Malthus")
public class MalthusController {

  @Autowired
  CalculoServiceImpl calculoService;

  @PostMapping("calculate")
  @ApiOperation(value = "Calcular Poblacion", notes = "Servicio para consultar por medio de la ecuacion de malthus un inoculo, poblacion estimada y tiempo de generacion")
  @ApiResponses(value={ @ApiResponse(code = 200, message = "Calculo correcto"),
      @ApiResponse(code = 400, message = "Campos en la solicitud errados") })
  public ResponseEntity<MalthusResponseDTO> calcular(@Valid @RequestBody MalthusRequestDTO malthusRequestDTO) {
    try {
      Double tuno = malthusRequestDTO.getTiempo1();
      Double tdos = malthusRequestDTO.getTiempo2();
      Double nuno = malthusRequestDTO.getPoblacion1();
      Double ndos = malthusRequestDTO.getPoblacion2();
      Double ttres = malthusRequestDTO.getTiempo();

      Double constanteK = (1 / (tdos - tuno)) * (Math.log(ndos / nuno));

      Double inoculo = nuno * (Math.pow(Math.E, (constanteK * -1) * tuno));

      Double tiempoGeneracion = Math.log(tuno) / constanteK;

      Double cantidadfinal = inoculo * (Math.pow(Math.E, (constanteK * ttres)));

      MalthusResponseDTO malthusResponseDTO = new MalthusResponseDTO();

      malthusResponseDTO.setConstanteCrecimiento(constanteK);

      malthusResponseDTO.setTiempoGeneracion(tiempoGeneracion);

      malthusResponseDTO.setInoculo(inoculo);

      malthusResponseDTO.setCantidad(cantidadfinal);

      Calculo calculo = new Calculo();

      calculo.setCantidad(cantidadfinal);
      calculo.setConstanteCrecimiento(constanteK);
      calculo.setInoculo(inoculo);
      calculo.setPoblacion1(nuno);
      calculo.setPoblacion2(ndos);
      calculo.setTiempo(ttres);
      calculo.setTiempo1(tuno);
      calculo.setTiempo2(tdos);
      calculo.setTiempoGeneracion(tiempoGeneracion);

      calculoService.save(calculo);
      malthusResponseDTO.setMsgError("Aplicado calculo sistema discreto malthus");
      return new ResponseEntity<>(malthusResponseDTO,HttpStatus.OK);
    }catch(Exception e) {
      MalthusResponseDTO malthusResponseDTO = new MalthusResponseDTO();
      malthusResponseDTO.setMsgError("Error en los datos enviados para calculo");
      return new ResponseEntity<>(malthusResponseDTO,HttpStatus.BAD_REQUEST);
    }
  
  }
}
