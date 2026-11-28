package edu.course.lab06;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;


public class TaskAnalytics {

    public static Map<String, Integer> countByStatus(List<ProjectTask> tasks) {
        // TreeMap: ключ (статус) уникален и вывод идёт по алфавиту
        Map<String, Integer> result = new TreeMap<>();
        for (ProjectTask task : tasks) {
            String status = task.getStatus();
            if (result.containsKey(status)) {
                result.put(status, result.get(status) + 1);
            } else {
                result.put(status, 1);
            }
        }
        return result;
    }

    public static Set<String> uniqueCategories(List<ProjectTask> tasks) {
        Set<String> result = new TreeSet<>();
        for (ProjectTask task : tasks) {
            result.add(task.getCategory());
        }
        return result;
    }

    public static List<ProjectTask> sortedByEffortAndId(List<ProjectTask> tasks) {
        List<ProjectTask> copy = new ArrayList<>(tasks);
        copy.sort(new Comparator<ProjectTask>() {
            @Override
            public int compare(ProjectTask a, ProjectTask b) {
                int byEffort = Integer.compare(a.getEffort(), b.getEffort());
                if (byEffort != 0) {
                    return byEffort;
                }
                return Integer.compare(a.getId(), b.getId());
            }
        });
        return copy;
    }
}
