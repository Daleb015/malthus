package com.daleb.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daleb.dto.MalthusRequestDTO;
import com.daleb.dto.MalthusResponseDTO;
import com.daleb.models.domain.Calculo;
import com.daleb.services.impl.CalculoServiceImpl;

@RestController
@RequestMapping("malthus/api/")
public class MalthusController {

	@Autowired
	CalculoServiceImpl calculoService;

	@GetMapping("calculate")
	public MalthusResponseDTO calcular(@RequestBody MalthusRequestDTO malthusRequestDTO) {

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

		return malthusResponseDTO;
	}
}
