package com.daleb.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daleb.models.domain.Calculo;
import com.daleb.models.repository.CalculoRepository;
import com.daleb.services.CalculoService;

@Service
public class CalculoServiceImpl implements CalculoService {

	@Autowired
	CalculoRepository calculoRepository;
	
	@Override
	public Calculo save(Calculo calculo) {
		
		calculoRepository.save(calculo);
		
		return calculo;
	}

	@Override
	public Optional<Calculo> find(Calculo calculo) {
		
		return calculoRepository.findById(calculo.getId());
	}

}
