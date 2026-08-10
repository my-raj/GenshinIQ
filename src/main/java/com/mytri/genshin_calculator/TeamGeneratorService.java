package com.mytri.genshin_calculator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TeamGeneratorService {
    @Autowired
    private CharacterScoreService characterScoreService;
    public List<List<GenshinCharacter>> generateTeams(List<GenshinCharacter> allCharacters, List<String> bossWeaknesses, List<String> bossImmunities) {
        Map<GenshinCharacter, Double> scores = new HashMap<>();
        List<GenshinCharacter> mainDpsList = new ArrayList<>();
        List<GenshinCharacter> healerList = new ArrayList<>();
        List<GenshinCharacter> supportList = new ArrayList<>();
        List<List<GenshinCharacter>> teams = new ArrayList<>();
        for (GenshinCharacter character : allCharacters) {
            double score = characterScoreService.scoreCharacter(character, bossWeaknesses, bossImmunities);
            scores.put(character, score);
            if (RoleLookup.getRole(character.getName()) == CharacterRoles.MAIN_DPS) {
                mainDpsList.add(character);
            } else if (RoleLookup.getRole(character.getName()) == CharacterRoles.HEALER ||
                    RoleLookup.getRole(character.getName()) == CharacterRoles.SHIELDER) {
                healerList.add(character);
            } else {
                supportList.add(character);
            }
        }
        allCharacters.sort((a, b) -> Double.compare(scores.get(b), scores.get(a)));
        mainDpsList.sort((a, b) -> Double.compare(scores.get(b), scores.get(a)));
        healerList.sort((a, b) -> Double.compare(scores.get(b), scores.get(a)));
        supportList.sort((a, b) -> Double.compare(scores.get(b), scores.get(a)));

        int supportIndex = 0;
        for (int i = 0; i < 3; i++) {
            List<GenshinCharacter> team = new ArrayList<>();
            if (i >= mainDpsList.size()) break;
            team.add(mainDpsList.get(i));

            if (i < healerList.size()) team.add(healerList.get(i));
            else if (!healerList.isEmpty()) team.add(healerList.get(0));

            int k = 0;
            int startIndex = supportIndex;
            for (int j = startIndex; j < supportList.size(); j++) {
                if (k == 2) break;
                GenshinCharacter character = supportList.get(j);
                if (!team.contains(character)) {
                    team.add(character);
                    k++;
                    supportIndex = j + 1;
                }
            }
            teams.add(team);
        }
        return teams;
    }
}
