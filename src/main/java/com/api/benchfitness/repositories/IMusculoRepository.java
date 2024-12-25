package com.api.benchfitness.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.benchfitness.models.MusculoModel;

@Repository
public interface IMusculoRepository extends JpaRepository<MusculoModel, Long>{

}
