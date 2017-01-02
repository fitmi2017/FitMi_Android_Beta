package com.fitmi.utils;

/**
 * Created by harmanpreet on 17/12/16.
 */
public interface interFragmentScaleCommunicator {

    public void connectDevice();
    public void connectDevice(boolean isFromRepeatingCheck);
    public void tare();
    public void changeUnits(String units);
    public String getWeight();
    public boolean isScaleConnected();
}
