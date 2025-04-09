package com.api.benchfitness.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.benchfitness.models.EjercicioModel;
import com.api.benchfitness.repositories.IEjercicioRepository;

@Service
public class EjercicioService {


	@Autowired
	IEjercicioRepository ejercicioRepository;

	/**
	 * Método que retorna todos los ejercicios encontrados
	 * @return
	 */
	public ArrayList<EjercicioModel> getEjercicios() {
		return (ArrayList<EjercicioModel>) ejercicioRepository.findAll();
	}


	/**
	 * Método que retorna el ejercicio que coincida con el id introducido
	 * EL optional devuelve el objeto o si no lo encuentra nulo
	 * @param id
	 * @return
	 */
	public Optional<EjercicioModel> getById(Long id){
		return ejercicioRepository.findById(id);
	}


	/**
	 * Método que retorna los ejercicios que coincida con el filtro introducido
	 * @param musculo
	 * @param categoria
	 * @param nivel
	 * @return lista de los ejercicios encontrados con los filtros
	 */
	public List<EjercicioModel> getEjerciciosFiltrados(String musculo, String categoria, String nivel) {

		List<EjercicioModel> ejercicios = ejercicioRepository.findAll();
		List<EjercicioModel> ejerciciosFiltrados = new ArrayList<>();	

		for (EjercicioModel ejercicio : ejercicios) {

			boolean coincide = true;

			if (musculo != null && !musculo.isEmpty() && !ejercicio.getMusculo_principal().equalsIgnoreCase(musculo)) {
				coincide = false;
			}

			if (categoria != null && !categoria.isEmpty() && !ejercicio.getCategoria().equalsIgnoreCase(categoria)) {
				coincide = false;
			}

			if (nivel != null && !nivel.isEmpty() && !ejercicio.getNivel().equalsIgnoreCase(nivel)) {
				coincide = false;
			}

			if (coincide) {
				ejerciciosFiltrados.add(ejercicio);
			}


		}
		return ejerciciosFiltrados;
	}


	/**
	 * Método que retorna los ejercicios que coincida con el nombre
	 * @param nombre
	 * @return lista de los ejercicios encontrados con el nombre
	 */
	public List<EjercicioModel> getEjerciciosFiltrados(String nombre) {

		List<EjercicioModel> ejercicios = ejercicioRepository.findAll();
		List<EjercicioModel> ejerciciosFiltrados = new ArrayList<>();	

		for (EjercicioModel ejercicio : ejercicios) {

			if (nombre != null && ejercicio.getNombre().startsWith(nombre)) {				
				ejerciciosFiltrados.add(ejercicio);
			}

		}
		
		return ejerciciosFiltrados;
	}




}
