package com.ebaa.alnazer.problem.movecontainer.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Shortcut {
    private Position start;
    private Position end;

    /**
     * Guarantees start != end
     * @param MAX_X the maximum value (inclusive) for the x axis
     * @param MAX_Y the maximum value (inclusive) for the y axis
     * @return a random shortcut within the specified bounds
     */
    public static Shortcut createRandomShortcut(final int MAX_X, final int MAX_Y) {
        Position start = Position.createRandomPosition(MAX_X, MAX_Y);
        Position end = Position.createRandomPosition(MAX_X, MAX_Y);

        while (MAX_X * MAX_Y > 1 && end.equals(start)) {
            end = Position.createRandomPosition(MAX_X, MAX_Y);
        }

        return Shortcut.builder()
                .start(start)
                .end(end)
                .build();
    }

    public String toString() {
        return  String.format("%s -> %s", start, end);
    }

}
