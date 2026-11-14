package edu.course.lab04;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class IssueRegistryTest {


    @Test
    void newRegistryIsEmpty() {
        IssueRegistry registry = new IssueRegistry();

        assertEquals(0, registry.size());
        assertFalse(registry.containsKey(new IssueId("APP", 1)));
        assertNull(registry.get(new IssueId("APP", 1)));
    }

    @Test
    void putAndGet() {
        IssueRegistry registry = new IssueRegistry();
        IssueId id = new IssueId("APP", 1);
        ProjectTask task = new ProjectTask("Сделать вход", false);

        registry.put(id, task);

        assertEquals(1, registry.size());
        assertTrue(registry.containsKey(id));
        assertEquals(task, registry.get(id));
    }

    @Test
    void removeReturnsRemovedValue() {
        IssueRegistry registry = new IssueRegistry();
        IssueId id = new IssueId("APP", 1);
        ProjectTask task = new ProjectTask("Сделать вход", false);
        registry.put(id, task);

        ProjectTask removed = registry.remove(id);

        assertEquals(task, removed);
        assertEquals(0, registry.size());
        assertFalse(registry.containsKey(id));
        assertNull(registry.get(id));
    }

    @Test
    void removeMissingKeyReturnsNull() {
        IssueRegistry registry = new IssueRegistry();

        assertNull(registry.remove(new IssueId("APP", 99)));
    }


    @Test
    void equalKeysFindSameValue() {
        IssueRegistry registry = new IssueRegistry();
        ProjectTask task = new ProjectTask("Баг в форме", false);

        registry.put(new IssueId("APP", 7), task);

        IssueId anotherKey = new IssueId("APP", 7); // новый объект, но логически равный
        assertEquals(task, registry.get(anotherKey));
        assertTrue(registry.containsKey(anotherKey));
    }

    @Test
    void putWithEqualKeyReplacesValueAndKeepsSize() {
        IssueRegistry registry = new IssueRegistry();
        ProjectTask oldTask = new ProjectTask("Старая", false);
        ProjectTask newTask = new ProjectTask("Новая", true);

        registry.put(new IssueId("APP", 3), oldTask);
        registry.put(new IssueId("APP", 3), newTask);

        assertEquals(1, registry.size());
        assertEquals(newTask, registry.get(new IssueId("APP", 3)));
    }
}
