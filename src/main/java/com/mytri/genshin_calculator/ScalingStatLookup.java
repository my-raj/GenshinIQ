package com.mytri.genshin_calculator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScalingStatLookup {
    private static final Map<String, List<ScalingStat>> SCALING = new HashMap<>();

    static {
        // Single ATK scaling
        SCALING.put("Jean", List.of(ScalingStat.ATK));
        SCALING.put("Bennett", List.of(ScalingStat.ATK));
        SCALING.put("Razor", List.of(ScalingStat.ATK));
        SCALING.put("Fischl", List.of(ScalingStat.ATK));
        SCALING.put("Keqing", List.of(ScalingStat.ATK));
        SCALING.put("Tighnari", List.of(ScalingStat.ATK));
        SCALING.put("Xiangling", List.of(ScalingStat.ATK));
        SCALING.put("Xingqiu", List.of(ScalingStat.ATK));
        SCALING.put("Beidou", List.of(ScalingStat.ATK));
        SCALING.put("Kaeya", List.of(ScalingStat.ATK));
        SCALING.put("Amber", List.of(ScalingStat.ATK));
        SCALING.put("Lisa", List.of(ScalingStat.ATK));
        SCALING.put("Aether", List.of(ScalingStat.ATK));
        SCALING.put("Gaming", List.of(ScalingStat.ATK));
        SCALING.put("Kaveh", List.of(ScalingStat.ATK));
        SCALING.put("Xinyan", List.of(ScalingStat.ATK));
        SCALING.put("Collei", List.of(ScalingStat.ATK));
        SCALING.put("Sayu", List.of(ScalingStat.ATK));
        SCALING.put("Thoma", List.of(ScalingStat.ATK));
        SCALING.put("Varka", List.of(ScalingStat.ATK));
        SCALING.put("Durin", List.of(ScalingStat.ATK));
        SCALING.put("Ineffa", List.of(ScalingStat.ATK));
        SCALING.put("Iansan", List.of(ScalingStat.ATK));
        SCALING.put("Ororon", List.of(ScalingStat.ATK));
        SCALING.put("Aino", List.of(ScalingStat.ATK));
        SCALING.put("Jahoda", List.of(ScalingStat.ATK));
        SCALING.put("Shikanoin Heizou", List.of(ScalingStat.ATK));
        SCALING.put("Charlotte", List.of(ScalingStat.ATK));
        SCALING.put("Lan Yan", List.of(ScalingStat.ATK));
        SCALING.put("Lynette", List.of(ScalingStat.ATK));

        // Single HP scaling
        SCALING.put("Barbara", List.of(ScalingStat.HP));
        SCALING.put("Diona", List.of(ScalingStat.HP));
        SCALING.put("Mika", List.of(ScalingStat.HP));
        SCALING.put("Kirara", List.of(ScalingStat.HP));
        SCALING.put("Candace", List.of(ScalingStat.HP));
        SCALING.put("Chevreuse", List.of(ScalingStat.HP));

        // Single DEF scaling
        SCALING.put("Noelle", List.of(ScalingStat.DEF));
        SCALING.put("Gorou", List.of(ScalingStat.DEF));
        SCALING.put("Yun Jin", List.of(ScalingStat.DEF));
        SCALING.put("Kachina", List.of(ScalingStat.DEF));
        SCALING.put("Linnea", List.of(ScalingStat.DEF));

        // Single EM scaling
        SCALING.put("Sucrose", List.of(ScalingStat.EM));
        SCALING.put("Nefer", List.of(ScalingStat.EM));
        SCALING.put("Yumemizuki Mizuki", List.of(ScalingStat.EM));

        // Split scaling
        SCALING.put("Sethos", List.of(ScalingStat.ATK, ScalingStat.EM));
        SCALING.put("Illuga", List.of(ScalingStat.DEF, ScalingStat.EM));
        SCALING.put("Dahlia", List.of(ScalingStat.ATK, ScalingStat.HP));
        SCALING.put("Ifa", List.of(ScalingStat.EM, ScalingStat.ATK));
        SCALING.put("Dehya", List.of(ScalingStat.ATK, ScalingStat.HP));
    }

    public static List<ScalingStat> getScalingStats(String characterName) {
        return SCALING.getOrDefault(characterName, List.of(ScalingStat.ATK));
    }
}