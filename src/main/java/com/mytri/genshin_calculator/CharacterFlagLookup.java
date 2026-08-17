package com.mytri.genshin_calculator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CharacterFlagLookup {
    private static final Map<String, List<CharacterFlag>> FLAGS = new HashMap<>();

    static {
        // Hydro self-appliers (bad against Cryo bosses)
        FLAGS.put("Barbara", List.of(CharacterFlag.SELF_APPLIES_HYDRO));
        FLAGS.put("Candace", List.of(CharacterFlag.SELF_APPLIES_HYDRO));

        // Cryo self-appliers (bad against Pyro bosses)
        FLAGS.put("Diona", List.of(CharacterFlag.SELF_APPLIES_CRYO));

        // Pyro self-appliers
        FLAGS.put("Bennett", List.of(CharacterFlag.SELF_APPLIES_PYRO));
        FLAGS.put("Xinyan", List.of(CharacterFlag.SELF_APPLIES_PYRO));

        // Electro self-appliers
        FLAGS.put("Beidou", List.of(CharacterFlag.SELF_APPLIES_ELECTRO));
    }

    public static List<CharacterFlag> getFlags(String characterName) {
        return FLAGS.getOrDefault(characterName, List.of());
    }

}
