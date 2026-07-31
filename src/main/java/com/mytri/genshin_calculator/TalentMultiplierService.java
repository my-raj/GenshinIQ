package com.mytri.genshin_calculator;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class TalentMultiplierService {
    @PostConstruct
    public void init() {
        try {
            InputStream is = getClass().getResourceAsStream("/static/talent_multipliers.json");
            // parse it with ObjectMapper here
        } catch (Exception e) {
            System.out.println("Failed to load talent_multipliers.json: " + e.getMessage());
        }
    }

    public double getMultiplier(int avatarId, String talentType, int talentLevel) {
        return 0.0; // fill in later
    }
}
