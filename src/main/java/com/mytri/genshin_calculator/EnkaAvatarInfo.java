package com.mytri.genshin_calculator;

import java.util.List;
import java.util.Map;

public class EnkaAvatarInfo {
    private Integer avatarId;
    private List<EnkaEquip> equipList;
    private List<Integer> talentIdList;
    private Map<String, Integer> skillLevelMap;

    public EnkaAvatarInfo() {
    }

    public Integer getAvatarId() {
        return avatarId;
    }
    public void setAvatarId(Integer avatarId) {
        this.avatarId = avatarId;
    }
    public List<EnkaEquip> getEquipList() {
        return equipList;
    }
    public void setEquipList(List<EnkaEquip> equipList) {
        this.equipList = equipList;
    }
    public List<Integer> getTalentIdList() {
        return talentIdList;
    }
    public void setTalentIdList(List<Integer> talentIdList) {
        this.talentIdList = talentIdList;
    }
    public Map<String, Integer> getSkillLevelMap() {
        return skillLevelMap;
    }
    public void setSkillLevelMap(Map<String, Integer> skillLevelMap) {
        this.skillLevelMap = skillLevelMap;
    }
}
