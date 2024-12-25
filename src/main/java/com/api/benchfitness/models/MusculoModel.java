package com.api.benchfitness.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "musculos")
public class MusculoModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_musculo;
	
	@Column(name = "nombre")
	private String nombre;

	public Long getId_musculo() {
		return id_musculo;
	}

	public void setId_musculo(Long id_musculo) {
		this.id_musculo = id_musculo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	

}
