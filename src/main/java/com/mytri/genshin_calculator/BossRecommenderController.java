package com.mytri.genshin_calculator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/recommend")
@CrossOrigin
public class BossRecommenderController {
    @Autowired
    private TeamGeneratorService teamGeneratorService;
    @Autowired
    private CharacterScoreService characterScoreService;
    @Autowired
    private GenshinCharacterRepository characterRepository;
    @GetMapping("/{bossName}")
    public List<List<GenshinCharacter>> recommendTeams(@PathVariable String bossName) {
        BossInfo bossInfo = BossDataLookup.getBossInfo(bossName);
        if (bossInfo == null) {
            return List.of();
        }
        List<GenshinCharacter> characters = characterRepository.findAll();
        return teamGeneratorService.generateTeams(characters, bossInfo.getWeaknesses(), bossInfo.getImmunities(), bossName);
    }
    @GetMapping("/scores/{bossName}")
    public Map<String, Double> getScores(@PathVariable String bossName) {
        BossInfo bossInfo = BossDataLookup.getBossInfo(bossName);
        if (bossInfo == null) return Map.of();
        List<GenshinCharacter> characters = characterRepository.findAll();
        Map<String, Double> scores = new HashMap<>();
        for (GenshinCharacter character : characters) {
            scores.put(character.getName(), characterScoreService.scoreCharacter(character, bossInfo.getWeaknesses(), bossInfo.getImmunities(), bossName));
        }
        return scores.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .collect(java.util.LinkedHashMap::new, (m, e) -> m.put(e.getKey(), e.getValue()), java.util.LinkedHashMap::putAll);
    }
}
