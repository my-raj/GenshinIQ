package com.mytri.genshin_calculator;

public class EnkaEquip {
    private EnkaReliquary reliquary;
    private EnkaWeaponData weapon;
    private EnkaFlat flat;

    public EnkaEquip() {
    }

    public EnkaReliquary getReliquary() {
        return reliquary;
    }
    public void setReliquary(EnkaReliquary reliquary) {
        this.reliquary = reliquary;
    }
    public EnkaWeaponData getWeapon() {
        return weapon;
    }
    public void setWeapon(EnkaWeaponData weapon) {
        this.weapon = weapon;
    }
    public EnkaFlat getFlat() {
        return flat;
    }
    public void setFlat(EnkaFlat flat) {
        this.flat = flat;
    }
}
