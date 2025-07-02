package com.api.benchfitness.services;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.benchfitness.models.User;
import com.api.benchfitness.repositories.IUserRepository;

@Service
public class UserService {

	@Autowired
	IUserRepository userRepository;

	public UserService(IUserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/**
	 * Método que genera una api key aletoria al usuario
	 * 
	 * @return
	 */
	public static String generateApiKey() {
		SecureRandom secureRandom = new SecureRandom();
		byte[] key = new byte[32];
		secureRandom.nextBytes(key);
		return Base64.getUrlEncoder().withoutPadding().encodeToString(key);
	}

	/**
	 * Método para registrar aun usuario nuevo con su API Key
	 * 
	 * @param owner
	 * @param description
	 * @return
	 */
	public User registerNewUser(String owner, String description) {

		if (userRepository.findByOwner(owner).isPresent()) {
			throw new IllegalArgumentException("Owner with this name/email already exists.");
		} else {
			String newApiKey = generateApiKey();
			User newUser = new User(owner, description, newApiKey);
			return userRepository.save(newUser);
		}
	}

	// Método para validar una API Key (usado por el filtro)
	public Optional<User> validateApiKey(String apiKey) {
		// Solo devuelve usuarios con claves activas
		return userRepository.findByApiKey(apiKey).filter(User::isActive); 
	}

}
