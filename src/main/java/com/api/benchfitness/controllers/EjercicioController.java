package com.api.benchfitness.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.benchfitness.models.EjercicioModel;
import com.api.benchfitness.services.EjercicioService;


@RestController
@RequestMapping("/ejercicios")
public class EjercicioController {
	
	@Autowired
	private EjercicioService ejercicioService;
	
	
	@GetMapping(path = "/{id}")
	public Optional<EjercicioModel> getEjercicioById(@PathVariable Long id){
		return this.ejercicioService.getById(id);
	}
	
	
	@GetMapping()
	public List<EjercicioModel> filtroEjercicios(@RequestParam(required = false) String musculo,
												 @RequestParam(required = false) String categoria,
												 @RequestParam(required = false) String nivel){
		if (musculo == null && categoria == null && nivel == null) {
			return ejercicioService.getEjercicios();
		} else {			
			return ejercicioService.getEjerciciosFiltrados(musculo,categoria,nivel);
		}
	}

	
}
