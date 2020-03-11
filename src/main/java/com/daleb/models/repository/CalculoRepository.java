package com.daleb.models.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.daleb.models.domain.Calculo;

@Repository
public interface CalculoRepository extends CrudRepository<Calculo, Long> {

}
