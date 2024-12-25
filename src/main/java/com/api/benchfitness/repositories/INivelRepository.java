package com.api.benchfitness.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.benchfitness.models.NivelModel;

@Repository
public interface INivelRepository extends JpaRepository<NivelModel, Long>{

}
