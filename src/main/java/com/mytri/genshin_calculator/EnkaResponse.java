package com.mytri.genshin_calculator;

import java.util.List;

public class EnkaResponse {
    private List<EnkaAvatarInfo> avatarInfoList;
    private EnkaPlayerInfo playerInfo;
    private Integer ttl;

    public EnkaResponse() {
    }

    public void setAvatarInfoList(List<EnkaAvatarInfo> avatarInfoList) {
        this.avatarInfoList = avatarInfoList;
    }
    public List<EnkaAvatarInfo> getAvatarInfoList() {
        return avatarInfoList;
    }
    public void setPlayerInfo(EnkaPlayerInfo playerInfo) {
        this.playerInfo = playerInfo;
    }
    public EnkaPlayerInfo getPlayerInfo() {
        return playerInfo;
    }
    public void setTtl(Integer ttl) {
        this.ttl = ttl;
    }
    public Integer getTtl() {
        return ttl;
    }
}
