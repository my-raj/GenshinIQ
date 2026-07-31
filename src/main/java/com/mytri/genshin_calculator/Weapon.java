package com.mytri.genshin_calculator;

import jakarta.persistence.*;

@Entity
@Table(name = "weapons")
public class Weapon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String name;
    int level;
    int refinement;
    double baseAttack;
    String secondaryStatType;
    double secondaryStatValue;

    public Weapon() {
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
    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public int getRefinement() {
        return refinement;
    }
    public void setRefinement(int refinement) {
        this.refinement = refinement;
    }
    public Double getBaseAttack() {
        return baseAttack;
    }
    public void setBaseAttack(double baseAttack) {
        this.baseAttack = baseAttack;
    }
    public String getSecondaryStatType() {
        return secondaryStatType;
    }
    public void setSecondaryStatType(String secondaryStatType) {
        this.secondaryStatType = secondaryStatType;
    }
    public double getSecondaryStatValue() {
        return secondaryStatValue;
    }
    public void setSecondaryStatValue(double secondaryStatValue) {
        this.secondaryStatValue = secondaryStatValue;
    }
}