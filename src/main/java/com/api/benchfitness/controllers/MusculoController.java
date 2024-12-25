package com.api.benchfitness.controllers;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.benchfitness.models.MusculoModel;
import com.api.benchfitness.services.MusculoService;

@RestController
@RequestMapping("/musculos")
public class MusculoController {

	@Autowired
	private MusculoService musculoService;
	
	@GetMapping
	public ArrayList<MusculoModel> getMusculos(){
		return this.musculoService.getMusculos();
	}
	
	@GetMapping(path = "/{id}")
	public Optional<MusculoModel> getMusculoById(@PathVariable Long id){
		return this.musculoService.getById(id);
	}
	
	
}
