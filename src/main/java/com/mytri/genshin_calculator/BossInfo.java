package com.mytri.genshin_calculator;

import java.util.List;

public class BossInfo {
    private List<String> weaknesses;
    private List<String> immunities;

    public BossInfo(List<String> weaknesses, List<String> immunities) {
        this.weaknesses = weaknesses;
        this.immunities = immunities;
    }

    public List<String> getImmunities() {
        return immunities;
    }
    public void setImmunities(List<String> immunities) {
        this.immunities = immunities;
    }
    public List<String> getWeaknesses() {
        return weaknesses;
    }
    public void setWeaknesses(List<String> weaknesses) {
        this.weaknesses = weaknesses;
    }
}
