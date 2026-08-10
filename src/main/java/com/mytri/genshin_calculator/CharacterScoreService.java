package com.mytri.genshin_calculator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        double hpBonus = 0.0;
        double flatHpBonus = 0.0;
        double defBonus = 0.0;
        double flatDefBonus = 0.0;
        double elementalMastery = calculateEM(character);
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
                if (substat.getStatType().equals("FIGHT_PROP_HP_PERCENT")) {
                    hpBonus += substat.getStatValue() * 0.01;
                }
                if (substat.getStatType().equals("FIGHT_PROP_HP")) {
                    flatHpBonus += substat.getStatValue();
                }
                if (substat.getStatType().equals("FIGHT_PROP_DEFENSE_PERCENT")) {
                    defBonus += substat.getStatValue() * 0.01;
                }
                if (substat.getStatType().equals("FIGHT_PROP_DEFENSE")) {
                    flatDefBonus += substat.getStatValue();
                }
            }
        }
        double totalAtk = totalBaseAtk * (1 + atkBonus) + flatAtkBonus;
        double totalHp = (character.getBaseHp() != null ? character.getBaseHp() : 0.0) * (1 + hpBonus) + flatHpBonus;
        double totalDef = (character.getBaseDef() != null ? character.getBaseDef() : 0.0) * (1 + defBonus) + flatDefBonus;
        critRate = Math.min(critRate, 1.0);
        double critMultiplier = 1 + critRate * critDmg;
        int normalAttackLevel = character.getNormalAttackLevel() != null ? character.getNormalAttackLevel() : 1;
        double talentMultiplier = talentMultiplierService.getMultiplier(character.getAvatarId(), "normalAttack", normalAttackLevel);
        if (talentMultiplier == 0.0) talentMultiplier = 0.5;
        String element = character.getElement();
        double elementMultiplier = 1;
        if (element != null && bossImmunities.contains(element)) {
            elementMultiplier = 0.5;
        } else if (element != null && bossWeaknesses.contains(element)) {
            elementMultiplier = 1.5;
        }
        CharacterRoles role = RoleLookup.getRole(character.getName());
        double roleMultiplier = 1.0;
        if (role == CharacterRoles.SUPPORT) roleMultiplier = 1.5;
        else if (role == CharacterRoles.HEALER) roleMultiplier = 1.4;
        else if (role == CharacterRoles.SHIELDER) roleMultiplier = 1.3;
        else if (role == CharacterRoles.SUB_DPS) roleMultiplier = 1.2;

        List<ScalingStat> scalingStats = ScalingStatLookup.getScalingStats(character.getName());
        double bestScore = 0.0;
        for (ScalingStat stat : scalingStats) {
            double baseScore;
            if (stat == ScalingStat.HP) baseScore = totalHp;
            else if (stat == ScalingStat.DEF) baseScore = totalDef;
            else if (stat == ScalingStat.EM) baseScore = elementalMastery * 10;
            else baseScore = totalAtk;
            double score = baseScore * critMultiplier * talentMultiplier * elementMultiplier * roleMultiplier;
            if (score > bestScore) bestScore = score;
        }
        return bestScore;
    }

    public double scoreTeam(List<GenshinCharacter> team, List<String> bossWeaknesses, List<String> bossImmunities) {
        double baseTeamScore = 0.0;
        Set<String> elements = new HashSet<>();
        double maxEM = 0.0;
        double emBonus = 0.0;
        List<Double> allEM = new ArrayList<>();
        for (GenshinCharacter character : team) {
            baseTeamScore += scoreCharacter(character, bossWeaknesses, bossImmunities);
            elements.add(character.getElement());
            allEM.add(calculateEM(character));
        }
        maxEM = allEM.stream().max(Double::compareTo).orElse(0.0);
        if ((elements.contains("Hydro") && elements.contains("Pyro")) || (elements.contains("Cryo") && elements.contains("Pyro"))) {
            emBonus = 2.78 * maxEM / (maxEM + 1400);
        } else if ((elements.contains("Hydro") && elements.contains("Dendro") && elements.contains("Electro")) ||
                    (elements.contains("Hydro") && elements.contains("Dendro")) ||
                    (elements.contains("Pyro") && elements.contains("Electro"))) {
            emBonus = 5 * maxEM / (maxEM + 1200);
        } else if (elements.contains("Dendro") && elements.contains("Electro")) {
            emBonus = 5 * maxEM / (maxEM + 1200);
        }
        return baseTeamScore * (1 + emBonus);
    }

    private double calculateEM(GenshinCharacter character) {
        double em = 0.0;
        for (Artifact artifact : character.getArtifactList()) {
            for (ArtifactSubstat substat : artifact.getSubstats()) {
                if (substat.getStatType().equals("FIGHT_PROP_ELEMENT_MASTERY")) {
                    em += substat.getStatValue();
                }
            }
        }
        return em;
    }

}
