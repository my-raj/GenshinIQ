package com.mytri.genshin_calculator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BossDataLookup {
    private static final Map<String, BossInfo> BOSSES = new HashMap<>();

    static {
        BOSSES.put("Stormterror Dvalin", new BossInfo(
                List.of(),
                List.of("Frozen", "Stun", "Petrification")
        ));
        BOSSES.put("Andrius", new BossInfo(
                List.of("Pyro", "Electro"),
                List.of("Anemo", "Cryo")
        ));
        BOSSES.put("Childe", new BossInfo(
                List.of("Pyro", "Dendro"),
                List.of("Hydro")
        ));
        BOSSES.put("Childe", new BossInfo(
                List.of("Pyro", "Dendro"),
                List.of("Hydro")
        ));
    }

    public static BossInfo getBossInfo(String bossName) {
        return BOSSES.get(bossName);
    }
}
