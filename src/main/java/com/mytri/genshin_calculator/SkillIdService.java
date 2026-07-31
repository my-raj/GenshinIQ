package com.mytri.genshin_calculator;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class SkillIdService {
    Map<String, CharacterSkillIds> skillIdMap = new HashMap<>();
    @PostConstruct
    public void init() {
        try {
            InputStream is = getClass().getResourceAsStream("/static/skill_ids.json");
            tools.jackson.databind.ObjectMapper mapper = new tools.jackson.databind.ObjectMapper();
            skillIdMap = mapper.readValue(is,
                    mapper.getTypeFactory().constructMapType(
                            java.util.HashMap.class,
                            String.class,
                            CharacterSkillIds.class
                    )
            );
            System.out.println("Loaded skill IDs for " + skillIdMap.size() + " characters");
        } catch (Exception e) {
            System.out.println("Failed to load skill_ids.json: " + e.getMessage());
        }
    }
    public CharacterSkillIds getSkillIds(int avatarId) {
        return skillIdMap.get(String.valueOf(avatarId));
    }
}
