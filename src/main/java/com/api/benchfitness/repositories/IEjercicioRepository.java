package com.api.benchfitness.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.benchfitness.models.EjercicioModel;

@Repository
public interface IEjercicioRepository extends JpaRepository<EjercicioModel, Long> {

}
