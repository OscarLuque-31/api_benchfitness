package com.api.benchfitness.dto; // O tu paquete de DTOs

public class UserRegisterRequest {
	
	private String owner;
	private String description;

	// Getters y Setters
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
}