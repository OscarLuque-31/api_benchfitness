package com.api.benchfitness.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Ejercicios")
public class EjercicioModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_ejercicio;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "categoria")
	private String categoria;

	@Column(name = "nivel")
	private String nivel;

	@Column(name = "equipamiento")
	private String equipamiento;

	@Column(name = "tipo_fuerza")
	private String tipo_fuerza;

	@Column(name = "musculo_principal")
	private String musculo_principal;

	@Column(name = "musculo_secundario")
	private String musculo_secundario;

	@Column(name = "instrucciones")
	private String instrucciones;

	@Column(name = "url_image")
	private String url_image;

	// Getter and Setters
	public Long getId_ejercicio() {
		return id_ejercicio;
	}

	public void setId_ejercicio(Long id_ejercicio) {
		this.id_ejercicio = id_ejercicio;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEquipamiento() {
		return equipamiento;
	}

	public void setEquipamiento(String equipamiento) {
		this.equipamiento = equipamiento;
	}

	public String getTipo_fuerza() {
		return tipo_fuerza;
	}

	public void setTipo_fuerza(String tipo_fuerza) {
		this.tipo_fuerza = tipo_fuerza;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

	public String getMusculo_principal() {
		return musculo_principal;
	}

	public void setMusculo_principal(String musculo_principal) {
		this.musculo_principal = musculo_principal;
	}

	public String getMusculo_secundario() {
		return musculo_secundario;
	}

	public void setMusculo_secundario(String musculo_secundario) {
		this.musculo_secundario = musculo_secundario;
	}

	public String getInstrucciones() {
		return instrucciones;
	}

	public void setInstrucciones(String instrucciones) {
		this.instrucciones = instrucciones;
	}

	public String getUrl_image() {
		return url_image;
	}

	public void setUrl_image(String url_image) {
		this.url_image = url_image;
	}

}
