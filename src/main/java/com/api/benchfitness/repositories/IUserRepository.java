package com.api.benchfitness.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.benchfitness.models.User;

public interface IUserRepository extends JpaRepository<User, Long> {
	
	// Método para buscar por API Key
	Optional<User> findByApiKey(String apiKey);
	// Método opcional para buscar por owner
	Optional<User> findByOwner(String owner);
}
