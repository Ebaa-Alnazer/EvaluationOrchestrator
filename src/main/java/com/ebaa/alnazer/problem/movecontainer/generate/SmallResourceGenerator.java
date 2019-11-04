package com.ebaa.alnazer.problem.movecontainer.generate;

import com.ebaa.alnazer.problem.movecontainer.model.MoveContainerProblem;
import com.ebaa.alnazer.problem.movecontainer.model.Position;
import com.ebaa.alnazer.problem.movecontainer.model.Shortcut;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Builder
@Data
public class SmallResourceGenerator {
    private int size;
    private boolean hasShortcut;

    public MoveContainerProblem generateProblem() {
        if (size <= 3) {
            throw new IllegalArgumentException("Problem size must be > 3");
        }

        Position truck = Position.builder().x(0).y(1).build();
        Position container = Position.builder().x(0).y(1).build();
        Position target = Position.builder().x(0).y(0).build();
        List<Shortcut> shortcuts = new ArrayList<>();
        if (hasShortcut) {
            shortcuts.add(Shortcut
                    .builder()
                    .start(Position
                            .builder()
                            .x(size - 1).y(0)
                            .build())
                    .end(target)
                    .build());
        }

        return MoveContainerProblem
                .builder()
                .size(size)
                .containers(Collections.singletonList(container))
                .shortcuts(shortcuts)
                .truck(truck)
                .target(target)
                .build();
    }
}
