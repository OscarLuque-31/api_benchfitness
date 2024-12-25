package com.api.benchfitness.controllers;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.benchfitness.models.CategoriaModel;
import com.api.benchfitness.services.CategoriaService;



@RestController
@RequestMapping("/categorias")
public class CategoriaController {

	@Autowired
	private CategoriaService categoriaService;
	
	@GetMapping
	public ArrayList<CategoriaModel> getCategorias(){
		return this.categoriaService.getCategorias();
	}
	
	@GetMapping(path = "/{id}")
	public Optional<CategoriaModel> getCategoriaById(@PathVariable Long id){
		return this.categoriaService.getById(id);
	}
	
	
}
