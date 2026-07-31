package com.mytri.genshin_calculator;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class ArtifactNameService {
    Map<String, GenshinDBArtifactResponse> cache = new HashMap<>();
    private RestTemplate restTemplate = new RestTemplate();

    public String getPieceName(String setName, String slot) {
        if (setName == null) return null;
        if (!cache.containsKey(setName)) {
            try {
                GenshinDBArtifactResponse response = restTemplate.getForObject(
                        "https://genshin-db-api.vercel.app/api/v5/artifacts?query=" + setName.replace(" ", "+"),
                        GenshinDBArtifactResponse.class
                );
                if (response != null) cache.put(setName, response);
            } catch (Exception e) {
                System.out.println("Failed to fetch artifact names for: " + setName);
                return null;
            }
        }
        GenshinDBArtifactResponse cached = cache.get(setName);
        if (cached == null) return null;
        switch (slot) {
            case "Flower":
                return cached.getFlower() != null ? cached.getFlower().getName() : null;
            case "Feather":
                return cached.getPlume() != null ? cached.getPlume().getName() : null;
            case "Sands":
                return cached.getSands() != null ? cached.getSands().getName() : null;
            case "Goblet":
                return cached.getGoblet() != null ? cached.getGoblet().getName() : null;
            case "Circlet":
                return cached.getCirclet() != null ? cached.getCirclet().getName() : null;
            default:
                return null;
        }
    }

}
