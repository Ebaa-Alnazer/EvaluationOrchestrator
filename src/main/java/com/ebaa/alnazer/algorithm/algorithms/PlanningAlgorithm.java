package com.ebaa.alnazer.algorithm.algorithms;

public abstract class PlanningAlgorithm {
    public abstract String getName();

    public abstract String[] getRequiredInputs();

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(getName());

        if (getRequiredInputs() != null && getRequiredInputs().length > 0) {
            for (String param : getRequiredInputs()) {
                builder.append(String.format("-%s", param));
            }
        }

        return builder.toString();
    }
}
