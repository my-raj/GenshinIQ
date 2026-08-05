package com.mytri.genshin_calculator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterScoreService {
    @Autowired
    private SkillIdService skillIdService;
    @Autowired
    private TalentMultiplierService talentMultiplierService;

    public double scoreCharacter(GenshinCharacter character, List<String> bossWeaknesses, List<String> bossImmunities) {
        double baseAtk = character.getBaseAtk() != null ? character.getBaseAtk() : 0.0;
        double baseWeaponAtk = (character.getWeapon() != null && character.getWeapon().getBaseAttack() != null)
                ? character.getWeapon().getBaseAttack() : 0.0;
        double totalBaseAtk = baseAtk + baseWeaponAtk;
        double atkBonus = 0.0;
        double flatAtkBonus = 0.0;
        double critRate = 0.05;
        double critDmg = 0.5;
        double elementalMastery = 0.0;
        for (Artifact artifact : character.getArtifactList()) {
            for (ArtifactSubstat substat : artifact.getSubstats()) {
                if (substat.getStatType().equals("FIGHT_PROP_ATTACK_PERCENT")) {
                    atkBonus += substat.getStatValue() * 0.01;
                }
                if (substat.getStatType().equals("FIGHT_PROP_ATTACK")) {
                    flatAtkBonus += substat.getStatValue();
                }
                if (substat.getStatType().equals("FIGHT_PROP_CRITICAL")) {
                    critRate += substat.getStatValue() * 0.01;
                }
                if (substat.getStatType().equals("FIGHT_PROP_CRITICAL_HURT")) {
                    critDmg += substat.getStatValue() * 0.01;
                }
                if (substat.getStatType().equals("FIGHT_PROP_ELEMENT_MASTERY")) {
                    elementalMastery += substat.getStatValue();
                }
            }
        }
        double totalAtk = totalBaseAtk * (1 + atkBonus) + flatAtkBonus;
        critRate = Math.min(critRate, 1.0);
        double critMultiplier = 1 + critRate * critDmg;
        int normalAttackLevel = character.getNormalAttackLevel() != null ? character.getNormalAttackLevel() : 1;
        double talentMultiplier = talentMultiplierService.getMultiplier(character.getAvatarId(), "normalAttack", normalAttackLevel);
        String element = character.getElement();
        double elementMultiplier = 1;
        if (element != null && bossImmunities.contains(element)) {
            elementMultiplier = 0.5;
        } else if (element != null && bossWeaknesses.contains(element)) {
            elementMultiplier = 1.5;
        }
        return totalAtk * critMultiplier * talentMultiplier * elementMultiplier;
    }

    public double scoreTeam(List<GenshinCharacter> team, List<String> bossWeaknesses, List<String> bossImmunities) {

    }

}
