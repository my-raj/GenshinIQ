package com.mytri.genshin_calculator;

import java.util.HashMap;
import java.util.Map;

public class RoleLookup {
    private static final Map<String, CharacterRoles> ROLES = new HashMap<>();

    static {
        // Healers
        ROLES.put("Jean", CharacterRoles.HEALER);
        ROLES.put("Barbara", CharacterRoles.HEALER);
        ROLES.put("Diona", CharacterRoles.HEALER);
        ROLES.put("Sayu", CharacterRoles.HEALER);
        ROLES.put("Mika", CharacterRoles.HEALER);
        ROLES.put("Kirara", CharacterRoles.SHIELDER);
        ROLES.put("Noelle", CharacterRoles.SHIELDER);

        // Supports
        ROLES.put("Bennett", CharacterRoles.SUPPORT);
        ROLES.put("Sucrose", CharacterRoles.SUPPORT);
        ROLES.put("Gorou", CharacterRoles.SUPPORT);
        ROLES.put("Yun Jin", CharacterRoles.SUPPORT);
        ROLES.put("Charlotte", CharacterRoles.SUPPORT);
        ROLES.put("Chevreuse", CharacterRoles.SUPPORT);
        ROLES.put("Lynette", CharacterRoles.SUPPORT);
        ROLES.put("Lan Yan", CharacterRoles.SUPPORT);
        ROLES.put("Faruzan", CharacterRoles.SUPPORT);

        // Sub DPS
        ROLES.put("Fischl", CharacterRoles.SUB_DPS);
        ROLES.put("Xiangling", CharacterRoles.SUB_DPS);
        ROLES.put("Xingqiu", CharacterRoles.SUB_DPS);
        ROLES.put("Beidou", CharacterRoles.SUB_DPS);
        ROLES.put("Kaeya", CharacterRoles.SUB_DPS);
        ROLES.put("Candace", CharacterRoles.SUB_DPS);
        ROLES.put("Thoma", CharacterRoles.SUB_DPS);
        ROLES.put("Collei", CharacterRoles.SUB_DPS);
        ROLES.put("Amber", CharacterRoles.SUB_DPS);
        ROLES.put("Lisa", CharacterRoles.SUB_DPS);

        // Main DPS
        ROLES.put("Aether", CharacterRoles.MAIN_DPS);
        ROLES.put("Razor", CharacterRoles.MAIN_DPS);
        ROLES.put("Gaming", CharacterRoles.MAIN_DPS);
        ROLES.put("Tighnari", CharacterRoles.MAIN_DPS);
        ROLES.put("Keqing", CharacterRoles.MAIN_DPS);
        ROLES.put("Kaveh", CharacterRoles.MAIN_DPS);
        ROLES.put("Sethos", CharacterRoles.MAIN_DPS);
        ROLES.put("Kachina", CharacterRoles.MAIN_DPS);
        ROLES.put("Dahlia", CharacterRoles.MAIN_DPS);
        ROLES.put("Illuga", CharacterRoles.MAIN_DPS);
        ROLES.put("Xinyan", CharacterRoles.MAIN_DPS);

        // Newer characters
        ROLES.put("Nefer", CharacterRoles.SUPPORT);
        ROLES.put("Ineffa", CharacterRoles.MAIN_DPS);
        ROLES.put("Linnea", CharacterRoles.SUB_DPS);
        ROLES.put("Varka", CharacterRoles.MAIN_DPS);
        ROLES.put("Durin", CharacterRoles.MAIN_DPS);
        ROLES.put("Iansan", CharacterRoles.SUB_DPS);
        ROLES.put("Ifa", CharacterRoles.SUPPORT);
        ROLES.put("Aino", CharacterRoles.HEALER);
        ROLES.put("Jahoda", CharacterRoles.SUPPORT);
        ROLES.put("Shikanoin Heizou", CharacterRoles.MAIN_DPS);
        ROLES.put("Yumemizuki Mizuki", CharacterRoles.SUPPORT);
        ROLES.put("Dehya", CharacterRoles.SHIELDER);
    }

    public static CharacterRoles getRole(String characterName) {
        return ROLES.getOrDefault(characterName, CharacterRoles.MAIN_DPS);
    }
}
