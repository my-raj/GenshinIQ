package com.mytri.genshin_calculator;

public class PityResponse {
    private String recommendation;
    private double[] distributions;

    public PityResponse(String recommendation, double[] distributions) {
        this.recommendation = recommendation;
        this.distributions = distributions;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public double[] getDistributions() {
        return distributions;
    }
}
