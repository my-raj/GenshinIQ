package com.mytri.genshin_calculator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TeamGeneratorService {
    @Autowired
    private CharacterScoreService characterScoreService;
    public List<List<GenshinCharacter>> generateTeams(List<GenshinCharacter> allCharacters, List<String> bossWeaknesses, List<String> bossImmunities) {
        Map<GenshinCharacter, Double> scores = new HashMap<>();
        for (GenshinCharacter character : allCharacters) {
            double score = characterScoreService.scoreCharacter(character, bossWeaknesses, bossImmunities);
            scores.put(character, score);
        }
        allCharacters.sort((a, b) -> Double.compare(scores.get(b), scores.get(a)));
        return List.of();
    }
}
