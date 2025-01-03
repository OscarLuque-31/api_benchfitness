package com.api.benchfitness.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.benchfitness.models.CategoriaModel;

@Repository
public interface ICategoriaRepository extends JpaRepository<CategoriaModel, Long>{

}
