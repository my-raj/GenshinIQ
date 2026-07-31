package com.mytri.genshin_calculator;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

// A Character object. Creates the object table. Getters and setters for character.
@Entity
@Table(name = "characters")

public class GenshinCharacter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    int avatarId;
    String name;
    int level;
    String constellation;
    String element;
    private Double baseHp;
    private Double baseAtk;
    private Double baseDef;
    private Integer normalAttackLevel;
    private Integer skillLevel;
    private Integer burstLevel;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "weaponId")
    private Weapon weapon;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "characterId")
    @JsonProperty("artifacts")
    private List<Artifact> artifactList;

    public GenshinCharacter() {
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public String getConstellation() {
        return constellation;
    }
    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }
    public Weapon getWeapon() {
        return weapon;
    }
    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }
    public List<Artifact> getArtifactList() {
        return artifactList;
    }
    public void setArtifactList(List<Artifact> artifactList) {
        this.artifactList = artifactList;
    }
    public int getAvatarId() {
        return avatarId;
    }
    public void setAvatarId(int avatarId) {
        this.avatarId = avatarId;
    }
    public String getElement() {
        return element;
    }
    public void setElement(String element) {
        this.element = element;
    }
    public Double getBaseHp() {
        return baseHp;
    }
    public void setBaseHp(Double baseHp) {
        this.baseHp = baseHp;
    }
    public Double getBaseAtk() {
        return baseAtk;
    }
    public void setBaseAtk(Double baseAtk) {
        this.baseAtk = baseAtk;
    }
    public Double getBaseDef() {
        return baseDef;
    }
    public void setBaseDef(Double baseDef) {
        this.baseDef = baseDef;
    }
    public Integer getNormalAttackLevel() {
        return normalAttackLevel;
    }
    public void setNormalAttackLevel(Integer normalAttackLevel) {
        this.normalAttackLevel = normalAttackLevel;
    }
    public Integer getSkillLevel() {
        return skillLevel;
    }
    public void setSkillLevel(Integer skillLevel) {
        this.skillLevel = skillLevel;
    }
    public Integer getBurstLevel() {
        return burstLevel;
    }
    public void setBurstLevel(Integer burstLevel) {
        this.burstLevel = burstLevel;
    }
}
