package com.lalikum.getdrunkforless.model;

import com.orm.SugarRecord;

public class Options extends SugarRecord {

    private String userName;
    private String unitType;
    private String currency;

    public Options() {
    }

    public Options(String userName, String unitType, String currency) {
        this.userName = userName;
        this.unitType = unitType;
        this.currency = currency;
    }

    public String getUserName() {
        return userName;
    }

    public String getUnitType() {
        return unitType;
    }

    public String getCurrency() {
        return currency;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
