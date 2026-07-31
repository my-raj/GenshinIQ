package com.mytri.genshin_calculator;

import java.util.List;

public class EnkaFlat {
    private String itemType;
    private String equipType;
    private String setNameTextMapHash;
    private List<EnkaStatEntry> reliquarySubstats;
    private EnkaStatEntry reliquaryMainstat;
    private List<EnkaStatEntry> weaponStats;
    private String nameTextMapHash;

    public EnkaFlat() {}

    public String getItemType() {
        return itemType;
    }
    public void setItemType(String itemType) {
        this.itemType = itemType;
    }
    public String getEquipType() {
        return equipType;
    }
    public void setEquipType(String equipType) {
        this.equipType = equipType;
    }
    public String getSetNameTextMapHash() {
        return setNameTextMapHash;
    }
    public void setSetNameTextMapHash(String setNameTextMapHash) {
        this.setNameTextMapHash = setNameTextMapHash;
    }
    public List<EnkaStatEntry> getReliquarySubstats() {
        return reliquarySubstats;
    }
    public void setReliquarySubstats(List<EnkaStatEntry> reliquarySubstats) {
        this.reliquarySubstats = reliquarySubstats;
    }
    public EnkaStatEntry getReliquaryMainstat() {
        return reliquaryMainstat;
    }
    public void setReliquaryMainstat(EnkaStatEntry reliquaryMainstat) {
        this.reliquaryMainstat = reliquaryMainstat;
    }
    public List<EnkaStatEntry> getWeaponStats() {
        return weaponStats;
    }
    public void setWeaponStats(List<EnkaStatEntry> weaponStats) {
        this.weaponStats = weaponStats;
    }
    public String getNameTextMapHash() {
        return nameTextMapHash;
    }
    public void setNameTextMapHash(String nameTextMapHash) {
        this.nameTextMapHash = nameTextMapHash;
    }
}
