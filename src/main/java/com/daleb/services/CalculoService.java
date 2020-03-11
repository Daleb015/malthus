package com.daleb.services;

import java.util.Optional;

import com.daleb.models.domain.Calculo;

public interface CalculoService {
	public Calculo save(Calculo calculo);

	public Optional<Calculo> find(Calculo calculo);
}
