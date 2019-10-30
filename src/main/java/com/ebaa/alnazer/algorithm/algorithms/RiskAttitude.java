package com.ebaa.alnazer.algorithm.algorithms;

import java.util.Arrays;

public enum RiskAttitude {
    RISK_AVERSE(0),
    RISK_SEEKING(1),
    RISK_NEUTRAL(2),
    RISK_AWARE(3);

    private int value;

    public int getValue() {
        return this.value;
    }

    private RiskAttitude(int value) {
        this.value = value;
    }

    public static RiskAttitude getRiskAttitudeByValue(int value) {
        for(RiskAttitude riskAttitude : RiskAttitude.values()) {
            if(riskAttitude.value == value)
                return riskAttitude;
        }
        throw new IllegalArgumentException("The value specified does not correspond to a known risk attitude");
    }
}
