package com.mytri.genshin_calculator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BossDataLookup {
    private static final Map<String, BossInfo> BOSSES = new HashMap<>();

    static {
        BOSSES.put("Stormterror Dvalin", new BossInfo(
                List.of(), // weak
                List.of() // immune
        ));
        BOSSES.put("Andrius", new BossInfo(
                List.of("Pyro", "Electro"),
                List.of("Anemo", "Cryo")
        ));
        BOSSES.put("Childe", new BossInfo(
                List.of("Pyro", "Dendro"),
                List.of("Hydro", "Electro")
        ));
        BOSSES.put("Azhdaha", new BossInfo(
                List.of(),
                List.of("Geo")
        ));
        BOSSES.put("La Signora", new BossInfo(
                List.of("Pyro", "Hydro", "Electro"),
                List.of("Cryo", "Pyro")
        ));
        BOSSES.put("Magatsu Mitake Narukami", new BossInfo(
                List.of("Cryo", "Pyro", "Electro"),
                List.of("Electro")
        ));
        BOSSES.put("Everlasting Lord of Arcane Wisdom", new BossInfo(
                List.of("Pyro", "Cryo", "Dendro"),
                List.of("Electro", "Hydro")
        ));
        BOSSES.put("Guardian of Apep's Oasis", new BossInfo(
                List.of("Pyro", "Electro", "Hydro"),
                List.of("Dendro")
        ));
        BOSSES.put("All-Devouring Narwhal", new BossInfo(
                List.of(),
                List.of("Hydro")
        ));
        BOSSES.put("The Knave (Arlecchino)", new BossInfo(
                List.of("Hydro", "Cryo"),
                List.of("Pyro")
        ));
        BOSSES.put("Lord of Eroded Primal Fire", new BossInfo(
                List.of("Hydro", "Electro"),
                List.of("Pyro")
        ));
        BOSSES.put("The Game Before the Gate", new BossInfo(
                List.of("Electro", "Cryo", "Dendro"),
                List.of()
        ));
        BOSSES.put("Heretic of the False Moon", new BossInfo(
                List.of("Anemo", "Geo"),
                List.of()
        ));
        BOSSES.put("Exalted Master of the Heretical Path", new BossInfo(
                List.of("Pyro", "Electro", "Cryo"),
                List.of()
        ));
    }

    public static BossInfo getBossInfo(String bossName) {
        return BOSSES.get(bossName);
    }
}
