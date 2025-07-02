package com.api.benchfitness.dto;


public class UserRegistrationResponse {

    private String owner;
    private String apiKey;

    public UserRegistrationResponse() {

    }

    public UserRegistrationResponse(String owner, String apiKey) {
        this.owner = owner;
        this.apiKey = apiKey;
    }

    // Getters y Setters
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public String toString() {
        return "UserRegistrationResponse{" +
               "owner='" + owner + '\'' +
               ", apiKey='" + apiKey + '\'' +
               '}';
    }
}