package com.foolox.base.constant.asset;

import lombok.Data;

/**
 * comment: 资产类型
 *
 * @author: lipengfei
 * @date: 31/07/2019
 */
public enum AssetType {
    //金币
    COIN(1),
    //钻石
    DIAMOND(2),
    //房卡
    CARD(3),
    ;
    int value;

    AssetType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

    public static AssetType nameOf(String name) {
        for (AssetType assetType : AssetType.values()) {
            if (null != name && name.toLowerCase().equals(assetType.toString())) {
                return assetType;
            }
        }
        return null;
    }
}
