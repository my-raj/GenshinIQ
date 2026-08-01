package com.mytri.genshin_calculator;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Service
public class TalentMultiplierService {
    Map<String, Map<String, Map<String, List<Double>>>> talentData;
    @PostConstruct
    public void init() {
        try {
            InputStream is = getClass().getResourceAsStream("/static/talent_multipliers.json");
            tools.jackson.databind.ObjectMapper mapper = new tools.jackson.databind.ObjectMapper();
            talentData = mapper.readValue(is, new tools.jackson.core.type.TypeReference<
                    Map<String, Map<String, Map<String, List<Double>>>>>(){});
        } catch (Exception e) {
            System.out.println("Failed to load talent_multipliers.json: " + e.getMessage());
        }
    }

    public double getMultiplier(int avatarId, String talentType, int talentLevel) {
        String avatarToString = String.valueOf(avatarId);
        if (talentData == null || !talentData.containsKey(avatarToString)) {
            return 0.0;
        }
        Map<String, Map<String, List<Double>>> characterTalents = talentData.get(avatarToString);
        if (characterTalents == null || !characterTalents.containsKey(talentType)) {
            return 0.0;
        }
        Map<String, List<Double>> talentLevels = characterTalents.get(talentType);
        List<Double> params = talentLevels.get(String.valueOf(talentLevel));
        if (params == null || params.isEmpty()) {
            return 0.0;
        }
        return params.get(0);
    }
}
