package com.api.benchfitness.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.benchfitness.models.CategoriaModel;
import com.api.benchfitness.repositories.ICategoriaRepository;

@Service
public class CategoriaService {

	
	@Autowired
	ICategoriaRepository categoriaRepository;
	
	/**
	 * Método que retorna todos las categorias encontradas
	 * @return
	 */
	public ArrayList<CategoriaModel> getCategorias() {
		return (ArrayList<CategoriaModel>) categoriaRepository.findAll();
	}
	
	
	/**
	 * Método que retorna la cateogira que coincida con el id introducido
	 * EL optional devuelve el objeto o si no lo encuentra nulo
	 * @param id
	 * @return
	 */
	public Optional<CategoriaModel> getById(Long id){
		return categoriaRepository.findById(id);
	}
	
	
	
	
}
