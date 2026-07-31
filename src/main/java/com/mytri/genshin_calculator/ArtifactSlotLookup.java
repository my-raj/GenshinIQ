package com.mytri.genshin_calculator;

import java.util.HashMap;
import java.util.Map;


public class ArtifactSlotLookup {
    private static final Map<String, String> EQUIP_TYPE_TO_SLOT = new HashMap<>();

    static {
        EQUIP_TYPE_TO_SLOT.put("EQUIP_BRACER", "Flower");
        EQUIP_TYPE_TO_SLOT.put("EQUIP_NECKLACE", "Feather");
        EQUIP_TYPE_TO_SLOT.put("EQUIP_SHOES", "Sands");
        EQUIP_TYPE_TO_SLOT.put("EQUIP_RING", "Goblet");
        EQUIP_TYPE_TO_SLOT.put("EQUIP_DRESS", "Circlet");
    }
    public static String getSlot(String equipType) {
        return EQUIP_TYPE_TO_SLOT.get(equipType);
    }
}