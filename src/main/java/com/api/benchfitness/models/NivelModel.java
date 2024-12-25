package com.api.benchfitness.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "niveles")
public class NivelModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_nivel;

	@Column(name = "nombre")
	private String nombre;

	public Long getId_nivel() {
		return id_nivel;
	}

	public void setId_nivel(Long id_nivel) {
		this.id_nivel = id_nivel;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
