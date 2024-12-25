package com.api.benchfitness.controllers;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.benchfitness.models.EjercicioModel;
import com.api.benchfitness.models.NivelModel;
import com.api.benchfitness.services.EjercicioService;
import com.api.benchfitness.services.NivelService;


@RestController
@RequestMapping("/niveles")
public class NivelController {
	
	@Autowired
	private NivelService nivelService;
	
	
	@GetMapping
	public ArrayList<NivelModel> getNiveles(){
		return this.nivelService.getNiveles();
	}
	
	
	@GetMapping(path = "/{id}")
	public Optional<NivelModel> getNivelById(@PathVariable Long id){
		return this.nivelService.getById(id);
	}
	
	
	

	
}
