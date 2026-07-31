package com.mytri.genshin_calculator;

import jakarta.persistence.*;

import java.util.List;

// An Artifact object. Creates the object table. Getters and setters for artifact.
@Entity
@Table(name = "artifacts")

public class Artifact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String name;
    Integer level;
    String slot;
    String mainStatType;
    double mainStatValue;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "artifactId")
    private List<ArtifactSubstat> substats;
    @Column(name = "characterId", insertable = false, updatable = false)
    Integer characterId;
    public Artifact() {
    }
    public Artifact(String name, Integer level, String slot, Integer characterId) {
        this.name = name;
        this.level = level;
        this.slot = slot;
        this.characterId = characterId;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getLevel() {
        return level;
    }
    public void setLevel(Integer level) {
        this.level = level;
    }
    public String getSlot() {
        return slot;
    }
    public void setSlot(String slot) {
        this.slot = slot;
    }
    public Integer getCharacterId() {
        return characterId;
    }
    public void setCharacterId(Integer characterId) {
        this.characterId = characterId;
    }
    public String getMainStatType() {
        return mainStatType;
    }
    public void setMainStatType(String mainStatType) {
        this.mainStatType = mainStatType;
    }
    public double getMainStatValue() {
        return mainStatValue;
    }
    public void setMainStatValue(double mainStatValue) {
        this.mainStatValue = mainStatValue;
    }
    public List<ArtifactSubstat> getSubstats() {
        return substats;
    }
    public void setSubstats(List<ArtifactSubstat> substats) {
        this.substats = substats;
    }
}
