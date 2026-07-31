package com.mytri.genshin_calculator;

import com.fasterxml.jackson.annotation.JsonAlias;

public class EnkaStatEntry {
    @JsonAlias("mainPropId")
    private String appendPropId;
    private Double statValue;

    public EnkaStatEntry() {}

    public String getAppendPropId() {
        return appendPropId;
    }
    public void setAppendPropId(String appendPropId) {
        this.appendPropId = appendPropId;
    }
    public Double getStatValue() {
        return statValue;
    }
    public void setStatValue(Double statValue) {
        this.statValue = statValue;
    }
}
