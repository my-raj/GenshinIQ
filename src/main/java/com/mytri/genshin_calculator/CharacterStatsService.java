package com.mytri.genshin_calculator;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class CharacterStatsService {
    Map<String, GenshinDbStatsResponse> cache = new HashMap<>();
    RestTemplate restTemplate = new RestTemplate();

    public GenshinDbStatsResponse getStats(String characterName, int level) {
        if (cache.containsKey(characterName + "_" + level)) {
            return cache.get(characterName + "_" + level);
        }
        try {
            GenshinDbStatsResponse response = restTemplate.getForObject(
                    "https://genshin-db-api.vercel.app/api/v5/stats?folder=characters&query=" + characterName + "&level=" + level + "&queryLanguages=english&resultLanguage=english",
                    GenshinDbStatsResponse.class
            );
            if (response != null && response.getAttack() != null) {
                cache.put(characterName + "_" + level, response);
                return response;
            }
        } catch (Exception e) {
            System.out.println("Failed to fetch element for: " + characterName);
        }
        return null;
    }
}
