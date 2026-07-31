package com.mytri.genshin_calculator;

public class GenshinDbStatsResponse {
    private Double hp;
    private Double attack;
    private Double defense;

    public Double getAttack() {
        return attack;
    }
    public Double getDefense() {
        return defense;
    }
    public Double getHp() {
        return hp;
    }
    public void setHp(Double hp) {
        this.hp = hp;
    }
    public void setAttack(Double attack) {
        this.attack = attack;
    }
    public void setDefense(Double defense) {
        this.defense = defense;
    }
}
