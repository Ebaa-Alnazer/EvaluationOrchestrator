package com.ebaa.alnazer.problem.movecontainer.generate;

import java.util.Arrays;

import com.ebaa.alnazer.problem.movecontainer.model.Position;
import com.ebaa.alnazer.problem.movecontainer.model.MoveContainerProblem;
import com.ebaa.alnazer.problem.movecontainer.model.Shortcut;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RandomProblemGenerator {
    private int size;
    private int containerCount;
    private int shortcutCount;

    public MoveContainerProblem generateProblem() {
        final int CELLS_COUNT = size * size;
        if (size <= 0) {
            throw new IllegalArgumentException("Problem size should be strictly positive!");
        }

        if (containerCount <= 0 || containerCount >= CELLS_COUNT) {
            throw new IllegalArgumentException("Container count should be a strictly positive value and less than " + CELLS_COUNT);
        }

        final int shortcutUpperLimit = CELLS_COUNT * (CELLS_COUNT - 1);

        if (shortcutCount < 0 || shortcutCount > shortcutUpperLimit) {
            throw new IllegalArgumentException("Shortcut count should be positive and less than " + (shortcutUpperLimit + 1));
        }

        final Position truck = this.randomPosition();
        final Position target = this.randomPosition();
        final Position[] containers = new Position[containerCount];
        boolean isDuplicate;

        for (int i = 0; i < containers.length; i++) {
            containers[i] = this.randomPosition();

            // if the current container position is used before, or if it is the same as the target position, pick a new
            // one
            isDuplicate = this.isDuplicateContainer(containers[i], target, containers);

            while (isDuplicate) {
                containers[i] = this.randomPosition();
                isDuplicate = this.isDuplicateContainer(containers[i], target, containers);
            }
        }

        final Shortcut[] shortcuts = new Shortcut[shortcutCount];

        for (int i = 0; i < shortcuts.length; i++) {
            shortcuts[i] = Shortcut.createRandomShortcut(size, size);
            isDuplicate = this.isDuplicateShortcut(shortcuts[i], shortcuts);

            while (isDuplicate) {
                shortcuts[i] = Shortcut.createRandomShortcut(size, size);
                isDuplicate = this.isDuplicateShortcut(shortcuts[i], shortcuts);
            }
        }

        return MoveContainerProblem
                .builder()
                .size(size)
                .containers(Arrays.asList(containers))
                .target(target)
                .shortcuts(Arrays.asList(shortcuts))
                .truck(truck)
                .build();
    }

    private Position randomPosition() {
        return Position.createRandomPosition(this.size, this.size);
    }

    private boolean isDuplicateContainer(Position toCheck, Position target, Position[] containers) {
        if (size == 1)
            return false;

        if (toCheck.equals(target))
            return true;

        return Arrays
                .stream(containers)
                .filter(toCheck::equals)
                .count() > 1;
    }

    private boolean isDuplicateShortcut(Shortcut shortcut, Shortcut[] shortcuts) {
        return Arrays
                .stream(shortcuts)
                .filter(shortcut::equals)
                .count() > 1;
    }
}
