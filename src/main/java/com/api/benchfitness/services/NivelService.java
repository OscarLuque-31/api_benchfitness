package com.api.benchfitness.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.benchfitness.models.EjercicioModel;
import com.api.benchfitness.models.NivelModel;
import com.api.benchfitness.repositories.IEjercicioRepository;
import com.api.benchfitness.repositories.INivelRepository;

@Service
public class NivelService {

	
	@Autowired
	INivelRepository nivelRepository;
	
	/**
	 * Método que retorna todos los niveles encontrados
	 * @return
	 */
	public ArrayList<NivelModel> getNiveles() {
		return (ArrayList<NivelModel>) nivelRepository.findAll();
	}
	
	
	/**
	 * Método que retorna el nivel que coincida con el id introducido
	 * EL optional devuelve el objeto o si no lo encuentra nulo
	 * @param id
	 * @return
	 */
	public Optional<NivelModel> getById(Long id){
		return nivelRepository.findById(id);
	}
	
	
	
	
}
