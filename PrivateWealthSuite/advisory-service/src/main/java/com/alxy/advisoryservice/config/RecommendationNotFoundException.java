package com.alxy.advisoryservice.config;

public class RecommendationNotFoundException extends RuntimeException {
    public RecommendationNotFoundException(String recommendationId) {
        super("Recommendation not found with ID: " + recommendationId);
    }
}