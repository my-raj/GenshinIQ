package com.mytri.genshin_calculator;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class LocDataService {

    private Map<String, String> locMap;

    private static final Map<String, String> FALLBACK_NAMES = new HashMap<>();
    static {
        FALLBACK_NAMES.put("1562600667", "Viridescent Venerer");
        FALLBACK_NAMES.put("894629371", "Aubade of Morningstar and Moon");
        FALLBACK_NAMES.put("3721836931", "A Day Carved from Rising Winds");
        FALLBACK_NAMES.put("625305019", "Silken Moon's Serenade");
        FALLBACK_NAMES.put("735056283", "Favonius Greatsword");
        FALLBACK_NAMES.put("2664628619", "Dragon's Bane");
        FALLBACK_NAMES.put("357888027", "Moon Weaver's Dawn");
        FALLBACK_NAMES.put("2556914171", "The Stringless");
        FALLBACK_NAMES.put("1321135155", "Lion's Roar");
    }

    @PostConstruct
    public void init() {
        RestTemplate restTemplate = new RestTemplate();
        String json = restTemplate.getForObject(
                "https://raw.githubusercontent.com/EnkaNetwork/API-docs/master/store/loc.json",
                String.class
        );
        tools.jackson.databind.ObjectMapper mapper = new tools.jackson.databind.ObjectMapper();
        try {
            Map<String, Map<String, String>> fullMap = mapper.readValue(json,
                    mapper.getTypeFactory().constructMapType(Map.class, String.class,
                            mapper.getTypeFactory().constructMapType(Map.class, String.class, String.class).getRawClass()));
            locMap = fullMap.get("en");
        } catch (Exception e) {
            System.out.println("Failed to load loc.json: " + e.getMessage());
        }
    }

    public String getName(String hash) {
        if (hash == null) return null;
        if (locMap != null) {
            String name = locMap.get(hash);
            if (name != null) return name;
        }
        return FALLBACK_NAMES.get(hash);
    }
}