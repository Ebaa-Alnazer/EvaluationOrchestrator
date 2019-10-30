package com.ebaa.alnazer.problem.movecontainer.model;

import java.util.List;

import com.ebaa.alnazer.problem.Problem;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MoveContainerProblem implements Problem {
    private int size;
    private Position truck;
    private Position target;
    private List<Position> containers;
    private List<Shortcut> shortcuts;

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("\nsize: %s\n", size));
        builder.append(String.format("truck position: %s\n", truck));
        builder.append(String.format("target position: %s\n", target));
        builder.append("Container Positions\n");
        builder.append("*******************\n");
        containers.forEach(c-> builder.append(c.toString()).append("\n"));
        builder.append("Shortcuts\n");
        builder.append("*********\n");
        shortcuts.forEach(s-> builder.append(s.toString()).append("\n"));

        return builder.toString();
    }
}
