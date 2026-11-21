package edu.course.lab05;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public final class LoadResult {

    private final List<ProjectTask> tasks;
    private final List<ValidationError> errors;

    public LoadResult(List<ProjectTask> tasks, List<ValidationError> errors) {
        this.tasks = Collections.unmodifiableList(new ArrayList<>(tasks));
        this.errors = Collections.unmodifiableList(new ArrayList<>(errors));
    }

    public List<ProjectTask> getTasks() {
        return tasks;
    }

    public List<ValidationError> getErrors() {
        return errors;
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }
}
