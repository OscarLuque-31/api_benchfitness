package com.api.benchfitness.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "api_key", unique = true, nullable = false)
	private String apiKey;

	@Column(name = "owner")
	private String owner;

	@Column(name = "description")
	private String description;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Column(name = "is_active")
	private boolean isActive;

	// Constructor por defecto
	public User() {
		this.createdAt = LocalDateTime.now();
		this.isActive = true;
	}

	// Constructor con campos obligatorios para creación
	public User(String owner, String description, String apiKey) {
		this(); // Llama al constructor por defecto para setear createdAt e isActive
		this.owner = owner;
		this.description = description;
		this.apiKey = apiKey;
	}

	// Getters y Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean active) {
		isActive = active;
	}
}