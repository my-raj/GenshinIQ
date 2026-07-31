package com.mytri.genshin_calculator;

import jakarta.persistence.*;

@Entity
@Table(name = "artifact_substats")
public class ArtifactSubstat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String statType;
    double statValue;

    @Column(name = "artifactId", insertable = false, updatable = false)
    Integer artifactId;

    public ArtifactSubstat() {
    }

    public int getId() {
        return id;
    }
    public String getStatType() {
        return statType;
    }
    public void setStatType(String statType) {
        this.statType = statType;
    }
    public double getStatValue() {
        return statValue;
    }
    public void setStatValue(double statValue) {
        this.statValue = statValue;
    }
    public Integer getArtifactId() {
        return artifactId;
    }
    public void setArtifactId(Integer artifactId) {
        this.artifactId = artifactId;
    }
}