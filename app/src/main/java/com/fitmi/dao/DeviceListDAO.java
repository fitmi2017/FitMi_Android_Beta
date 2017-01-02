package com.fitmi.dao;

/**
 * Created by harmanpreet on 28/12/16.
 */
public class DeviceListDAO {
    private String deviceName;
    private String syncType;

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getSyncType() {
        return syncType;
    }

    public void setSyncType(String syncType) {
        this.syncType = syncType;
    }
}
