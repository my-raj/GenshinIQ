package com.mytri.genshin_calculator;

public class GenshinDBArtifactResponse {
    private GenshinDBArtifactPiece flower;
    private GenshinDBArtifactPiece plume;
    private GenshinDBArtifactPiece sands;
    private GenshinDBArtifactPiece goblet;
    private GenshinDBArtifactPiece circlet;

    public GenshinDBArtifactResponse() {}

    public GenshinDBArtifactPiece getFlower() {
        return flower;
    }
    public void setFlower(GenshinDBArtifactPiece flower) {
        this.flower = flower;
    }
    public GenshinDBArtifactPiece getPlume() {
        return plume;
    }
    public void setPlume(GenshinDBArtifactPiece plume) {
        this.plume = plume;
    }
    public GenshinDBArtifactPiece getSands() {
        return sands;
    }
    public void setSands(GenshinDBArtifactPiece sands) {
        this.sands = sands;
    }
    public GenshinDBArtifactPiece getGoblet() {
        return goblet;
    }
    public void setGoblet(GenshinDBArtifactPiece goblet) {
        this.goblet = goblet;
    }
    public GenshinDBArtifactPiece getCirclet() {
        return circlet;
    }
    public void setCirclet(GenshinDBArtifactPiece circlet) {
        this.circlet = circlet;
    }

}