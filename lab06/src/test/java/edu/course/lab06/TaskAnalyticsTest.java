package edu.course.lab06;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;

class TaskAnalyticsTest {

    private List<ProjectTask> makeTasks() {
        List<ProjectTask> tasks = new ArrayList<>();
        tasks.add(new ProjectTask(3, "Логин", "DONE", "backend", 5));
        tasks.add(new ProjectTask(1, "Форма", "TODO", "frontend", 2));
        tasks.add(new ProjectTask(2, "База", "DONE", "backend", 5));
        tasks.add(new ProjectTask(4, "Тесты", "IN_PROGRESS", "qa", 1));
        tasks.add(new ProjectTask(5, "Стили", "TODO", "frontend", 8));
        return tasks;
    }

    @Test
    void countByStatus() {
        Map<String, Integer> counts = TaskAnalytics.countByStatus(makeTasks());
        assertEquals(3, counts.size());
        assertEquals(2, counts.get("DONE"));
        assertEquals(2, counts.get("TODO"));
        assertEquals(1, counts.get("IN_PROGRESS"));
    }

    @Test
    void uniqueCategoriesHaveNoDuplicates() {
        Set<String> categories = TaskAnalytics.uniqueCategories(makeTasks());
        assertEquals(3, categories.size());
        assertTrue(categories.contains("backend"));
        assertTrue(categories.contains("frontend"));
        assertTrue(categories.contains("qa"));
    }

    @Test
    void sortedByEffortThenId() {
        List<ProjectTask> sorted = TaskAnalytics.sortedByEffortAndId(makeTasks());
        // трудоёмкость 1, 2, 5, 5, 8; у двух "пятёрок" порядок по id: 2, потом 3
        int[] expectedIds = {4, 1, 2, 3, 5};
        for (int i = 0; i < expectedIds.length; i++) {
            assertEquals(expectedIds[i], sorted.get(i).getId());
        }
    }

    @Test
    void sortingDoesNotChangeOriginalList() {
        List<ProjectTask> original = makeTasks();
        List<ProjectTask> sorted = TaskAnalytics.sortedByEffortAndId(original);
        assertEquals(3, original.get(0).getId()); // исходный порядок остался
        assertEquals(1, original.get(1).getId());
        assertEquals(5, original.size());
        assertTrue(original != sorted); // это другой список
    }

    @Test
    void emptyListGivesEmptyResults() {
        List<ProjectTask> empty = new ArrayList<>();
        assertTrue(TaskAnalytics.countByStatus(empty).isEmpty());
        assertTrue(TaskAnalytics.uniqueCategories(empty).isEmpty());
        assertTrue(TaskAnalytics.sortedByEffortAndId(empty).isEmpty());
    }

    @Test
    void largeEffortValuesAreComparedCorrectly() {
        // при сортировке вычитанием здесь было бы переполнение int
        List<ProjectTask> tasks = new ArrayList<>();
        tasks.add(new ProjectTask(1, "A", "TODO", "x", Integer.MAX_VALUE));
        tasks.add(new ProjectTask(2, "B", "TODO", "x", -5));
        List<ProjectTask> sorted = TaskAnalytics.sortedByEffortAndId(tasks);
        assertEquals(2, sorted.get(0).getId());
        assertEquals(1, sorted.get(1).getId());
    }

    @Test
    void tasksWithSameIdAreEqual() {
        ProjectTask a = new ProjectTask(1, "A", "TODO", "x", 1);
        ProjectTask b = new ProjectTask(1, "Другое название", "DONE", "y", 9);
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }
}
