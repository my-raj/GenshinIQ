package com.mytri.genshin_calculator;

import java.util.List;

public class EnkaPlayerInfo {
    private List<EnkaShowAvatarInfo> showAvatarInfoList;

    public EnkaPlayerInfo() {}

    public List<EnkaShowAvatarInfo> getShowAvatarInfoList() {
        return showAvatarInfoList;
    }
    public void setShowAvatarInfoList(List<EnkaShowAvatarInfo> enkaShowAvatarInfoList) {
        this.showAvatarInfoList = enkaShowAvatarInfoList;
    }
}
