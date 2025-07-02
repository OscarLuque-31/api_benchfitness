package com.api.benchfitness.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/health")
public class HealthController {

    @GetMapping
    public String checkHealth() {
        // Este método simplemente devuelve un texto para confirmar que la API está funcionando.
        return "API is up and running!";
    }
}