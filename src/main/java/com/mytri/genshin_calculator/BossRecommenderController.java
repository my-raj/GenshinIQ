package com.mytri.genshin_calculator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recommend")
@CrossOrigin
public class BossRecommenderController {
    @Autowired
    private TeamGeneratorService teamGeneratorService;
    @Autowired
    private GenshinCharacterRepository characterRepository;
    @GetMapping("/{bossName}")
    public List<List<GenshinCharacter>> recommendTeams(@PathVariable String bossName) {
        BossInfo bossInfo = BossDataLookup.getBossInfo(bossName);
        if (bossInfo == null) {
            return List.of();
        }
        List<GenshinCharacter> characters = characterRepository.findAll();
        return teamGeneratorService.generateTeams(characters, bossInfo.getWeaknesses(), bossInfo.getImmunities());
    }
}
