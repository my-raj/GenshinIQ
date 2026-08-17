package com.mytri.genshin_calculator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BossFlagLookup {
    private static final Map<String, List<BossFlag>> BOSS_FLAGS = new HashMap<>();

    static{
        BOSS_FLAGS.put("Andrius", List.of(BossFlag.CRYO_AURA));
        BOSS_FLAGS.put("La Signora", List.of(BossFlag.CRYO_AURA, BossFlag.PYRO_AURA));
        BOSS_FLAGS.put("Magatsu Mitake Narukami", List.of(BossFlag.ELECTRO_AURA));
        BOSS_FLAGS.put("The Knave (Arlecchino)", List.of(BossFlag.PYRO_AURA));
        BOSS_FLAGS.put("Lord of Eroded Primal Fire", List.of(BossFlag.PYRO_AURA));

    }

    public static List<BossFlag> getFlags(String bossName) {
        return BOSS_FLAGS.getOrDefault(bossName, List.of());
    }
}
