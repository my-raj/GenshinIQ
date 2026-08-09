package com.mytri.genshin_calculator;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
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
        try {
            InputStream is = getClass().getResourceAsStream("/static/loc.json");
            tools.jackson.databind.ObjectMapper mapper = new tools.jackson.databind.ObjectMapper();
            Map<String, Map<String, String>> fullMap = mapper.readValue(is,
                    mapper.getTypeFactory().constructMapType(Map.class, String.class,
                            mapper.getTypeFactory().constructMapType(Map.class, String.class, String.class).getRawClass()));
            locMap = fullMap.get("en");
            System.out.println("Loaded loc.json successfully");
        } catch (Exception e) {
            System.out.println("Failed to load loc.json: " + e.getMessage());
            locMap = null;
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