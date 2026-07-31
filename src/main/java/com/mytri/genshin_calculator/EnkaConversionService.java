package com.mytri.genshin_calculator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class EnkaConversionService {

    @Autowired
    private LocDataService locDataService;
    @Autowired
    private ArtifactNameService artifactNameService;
    @Autowired
    private CharacterElementService characterElementService;
    @Autowired
    private CharacterStatsService characterStatsService;
    @Autowired
    private SkillIdService skillIdService;

    public GenshinCharacter convertToCharacter(EnkaAvatarInfo avatarInfo, List<EnkaShowAvatarInfo> showAvatars) {
        GenshinCharacter character = new GenshinCharacter();
        List<Artifact> artifacts = new ArrayList<>();
        character.setAvatarId(avatarInfo.getAvatarId());
        if (avatarInfo.getTalentIdList() == null || avatarInfo.getTalentIdList().isEmpty()) {
            character.setConstellation("C0");
        } else {
            character.setConstellation("C"+avatarInfo.getTalentIdList().size());
        }
        for (EnkaShowAvatarInfo enkaShowAvatarInfo : showAvatars) {
            if (enkaShowAvatarInfo.getAvatarId().equals(avatarInfo.getAvatarId())) {
                character.setLevel(enkaShowAvatarInfo.getLevel());
                break;
            }
        }
        for(EnkaEquip enkaEquip : avatarInfo.getEquipList()) {
            if (enkaEquip.getFlat().getItemType().equals("ITEM_RELIQUARY")) {
                Artifact artifact = new Artifact();
                String slot = ArtifactSlotLookup.getSlot(enkaEquip.getFlat().getEquipType());
                String setName = locDataService.getName(enkaEquip.getFlat().getSetNameTextMapHash());
                artifact.setSlot(slot);
                artifact.setLevel(enkaEquip.getReliquary().getLevel() - 1);
                artifact.setName(artifactNameService.getPieceName(setName, slot));
                artifact.setMainStatType(enkaEquip.getFlat().getReliquaryMainstat().getAppendPropId());
                artifact.setMainStatValue(enkaEquip.getFlat().getReliquaryMainstat().getStatValue());
                List<ArtifactSubstat> artifactSubstats = new ArrayList<>();
                for (EnkaStatEntry substat: enkaEquip.getFlat().getReliquarySubstats()) {
                    ArtifactSubstat artifactSubstat = new ArtifactSubstat();
                    artifactSubstat.setStatType(substat.getAppendPropId());
                    artifactSubstat.setStatValue(substat.getStatValue());
                    artifactSubstats.add(artifactSubstat);
                }
                artifact.setSubstats(artifactSubstats);
                artifacts.add(artifact);
            } else if(enkaEquip.getFlat().getItemType().equals("ITEM_WEAPON")) {
                Weapon weapon = new Weapon();
                weapon.setName(locDataService.getName(enkaEquip.getFlat().getNameTextMapHash()));
                weapon.setLevel(enkaEquip.getWeapon().getLevel());
                weapon.setBaseAttack(enkaEquip.getFlat().getWeaponStats().getFirst().getStatValue());
                if (enkaEquip.getFlat().getWeaponStats().size() > 1) {
                    weapon.setSecondaryStatType(enkaEquip.getFlat().getWeaponStats().get(1).getAppendPropId());
                    weapon.setSecondaryStatValue(enkaEquip.getFlat().getWeaponStats().get(1).getStatValue());
                }
                character.setWeapon(weapon);
            }
        }
        String characterName = AvatarIdLookup.getName(avatarInfo.getAvatarId());
        character.setName(characterName);
        character.setElement(characterElementService.getElement(characterName));

        character.setArtifactList(artifacts);

        GenshinDbStatsResponse stats = characterStatsService.getStats(characterName, character.getLevel());
        if (stats != null) {
            character.setBaseHp(stats.getHp());
            character.setBaseAtk(stats.getAttack());
            character.setBaseDef(stats.getDefense());
        }

        CharacterSkillIds skillIds = skillIdService.getSkillIds(avatarInfo.getAvatarId());
        Map<String, Integer> skillLevelMap = avatarInfo.getSkillLevelMap();
        if (skillIds != null && skillLevelMap != null) {
            character.setNormalAttackLevel(skillLevelMap.get(skillIds.getNormalAttack()));
            character.setSkillLevel(skillLevelMap.get(skillIds.getSkill()));
            character.setBurstLevel(skillLevelMap.get(skillIds.getBurst()));
        }

        return character;
    }

    public List<GenshinCharacter> convertAllCharacters(EnkaResponse enkaResponse) {
        List<GenshinCharacter> characters = new ArrayList<>();
        for(EnkaAvatarInfo enkaAvatarInfo : enkaResponse.getAvatarInfoList()) {
            characters.add(convertToCharacter(enkaAvatarInfo, enkaResponse.getPlayerInfo().getShowAvatarInfoList()));
        }
        return characters;
    }

}