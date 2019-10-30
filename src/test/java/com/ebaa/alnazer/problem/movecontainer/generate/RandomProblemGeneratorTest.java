package com.ebaa.alnazer.problem.movecontainer.generate;

import java.util.HashSet;
import java.util.List;

import com.ebaa.alnazer.problem.movecontainer.model.Position;
import com.ebaa.alnazer.problem.movecontainer.model.MoveContainerProblem;
import com.ebaa.alnazer.problem.movecontainer.model.Shortcut;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RandomProblemGeneratorTest {
    private static final Logger log = LoggerFactory.getLogger(RandomProblemGeneratorTest.class);

    @Test
    public void testGenerateRandomProblem() {
        final int size = 2;
        final int containerCount = 1;
        final int shortcutCount = 12;
        RandomProblemGenerator generator = RandomProblemGenerator
                .builder()
                .size(size)
                .containerCount(containerCount)
                .shortcutCount(shortcutCount)
                .build();
        MoveContainerProblem problem = generator.generateProblem();
        assertNotNull(problem);
        assertNotNull(problem.getTruck());
        assertNotNull(problem.getTarget());
        List<Position> containers = problem.getContainers();
        assertNotNull(containers);
        assertEquals(containerCount, containers.size());
        // no duplicates
        assertEquals(containers.size(), new HashSet<>(containers).size());
        assertFalse(containers.contains(problem.getTarget()));
        List<Shortcut> shortcuts = problem.getShortcuts();
        assertNotNull(shortcuts);
        assertEquals(shortcuts.size(), new HashSet<>(shortcuts).size());
        log.debug(problem.toString());
    }
}