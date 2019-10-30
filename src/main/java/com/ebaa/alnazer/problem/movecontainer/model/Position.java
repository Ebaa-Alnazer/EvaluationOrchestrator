
package com.ebaa.alnazer.problem.movecontainer.model;

import com.ebaa.alnazer.random.RandomProvider;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Position {
    private int x;
    private int y;

    public static Position createRandomPosition(final int MAX_X, final int MAX_Y) {
        return Position.builder()
                .x(RandomProvider.randomInt(MAX_X))
                .y(RandomProvider.randomInt(MAX_Y))
                .build();
    }

    @Override
    public String toString() {
        return String.format("(%s,%s)", x, y);
    }
}
