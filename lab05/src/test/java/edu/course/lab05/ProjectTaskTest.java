package edu.course.lab05;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ProjectTaskTest {

    @Test
    void createsValidTask() {
        ProjectTask task = new ProjectTask("T-1", "Title", "backend", 5, TaskStatus.READY);

        assertEquals("T-1", task.getId());
        assertEquals(5, task.getEstimatedHours());
    }

    @Test
    void rejectsBlankId() {
        assertThrows(IllegalArgumentException.class,
                () -> new ProjectTask(" ", "Title", "backend", 5, TaskStatus.READY));
    }

    @Test
    void rejectsNonPositiveHours() {
        assertThrows(IllegalArgumentException.class,
                () -> new ProjectTask("T-1", "Title", "backend", 0, TaskStatus.READY));
    }

    @Test
    void rejectsNullStatus() {
        assertThrows(IllegalArgumentException.class,
                () -> new ProjectTask("T-1", "Title", "backend", 5, null));
    }

    @Test
    void equalTasksHaveEqualHashCodes() {
        ProjectTask a = new ProjectTask("T-1", "Title", "backend", 5, TaskStatus.READY);
        ProjectTask b = new ProjectTask("T-1", "Title", "backend", 5, TaskStatus.READY);

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }
}
