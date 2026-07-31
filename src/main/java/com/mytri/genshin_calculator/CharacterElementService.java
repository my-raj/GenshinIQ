package com.mytri.genshin_calculator;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class CharacterElementService {
    Map<String, String> cache = new HashMap<>();
    RestTemplate restTemplate = new RestTemplate();

    public String getElement(String characterName) {
        if (cache.containsKey(characterName)) {
            return cache.get(characterName);
        }
        try {
            GenshinDBCharacterResponse response = restTemplate.getForObject(
                    "https://genshin-db-api.vercel.app/api/v5/characters?query=" + characterName.replace(" ", "+") + "&queryLanguages=english&resultLanguage=english",
                    GenshinDBCharacterResponse.class
            );
            System.out.println("Element for " + characterName + ": " + (response != null ? response.getElementText() : "null response"));
            if (response != null && response.getElementText() != null) {
                cache.put(characterName, response.getElementText());
                return response.getElementText();
            }
        } catch (Exception e) {
            System.out.println("Failed to fetch element for: " + characterName);
        }
        return null;
    }
}
