package com.ebaa.alnazer.algorithm.algorithms;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UAShop extends PlanningAlgorithm {

    private double resource;
    private RiskAttitude riskAttitude;
    public static final String NAME = "uashop";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String[] getRequiredInputs() {
        return new String[] {
                String.valueOf(resource),
                String.valueOf(riskAttitude.getValue())
        };
    }
}
