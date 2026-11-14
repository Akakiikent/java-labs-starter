package edu.course.lab04;

import java.util.HashMap;

public class IssueRegistry {
    private final HashMap<IssueId, ProjectTask> map = new HashMap<>();

    public void put(IssueId id, ProjectTask task) {
        map.put(id, task);
    }

    public ProjectTask get(IssueId id) {
        return map.get(id);
    }

    public ProjectTask remove(IssueId id) {
        return map.remove(id);
    }

    public boolean containsKey(IssueId id) {
        return map.containsKey(id);
    }

    public int size() {
        return map.size();
    }
}
