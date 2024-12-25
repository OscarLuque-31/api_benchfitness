package com.api.benchfitness.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.benchfitness.models.MusculoModel;
import com.api.benchfitness.repositories.IMusculoRepository;

@Service
public class MusculoService {
	
	@Autowired
	IMusculoRepository musculoRepository;
	
	/**
	 * Método que retorna todos los músculos encontrados
	 * @return
	 */
	public ArrayList<MusculoModel> getMusculos() {
		return (ArrayList<MusculoModel>) musculoRepository.findAll();
	}
	
	
	/**
	 * Método que retorna el músculo que coincida con el id introducido
	 * EL optional devuelve el objeto o si no lo encuentra nulo
	 * @param id
	 * @return
	 */
	public Optional<MusculoModel> getById(Long id){
		return musculoRepository.findById(id);
	}
	

}
