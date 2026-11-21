package edu.course.lab05;

import java.util.Objects;


public final class ProjectTask {

    private final String id;
    private final String title;
    private final String category;
    private final int estimatedHours;
    private final TaskStatus status;

    public ProjectTask(String id, String title, String category, int estimatedHours, TaskStatus status) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("id must not be blank");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("title must not be blank");
        }
        if (category == null || category.isBlank()) {
            throw new IllegalArgumentException("category must not be blank");
        }
        if (estimatedHours <= 0) {
            throw new IllegalArgumentException("estimatedHours must be positive");
        }
        if (status == null) {
            throw new IllegalArgumentException("status must not be null");
        }
        this.id = id;
        this.title = title;
        this.category = category;
        this.estimatedHours = estimatedHours;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public int getEstimatedHours() {
        return estimatedHours;
    }

    public TaskStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectTask)) {
            return false;
        }
        ProjectTask other = (ProjectTask) o;
        return estimatedHours == other.estimatedHours
                && id.equals(other.id)
                && title.equals(other.title)
                && category.equals(other.category)
                && status == other.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, category, estimatedHours, status);
    }

    @Override
    public String toString() {
        return "ProjectTask{id='" + id + "', title='" + title + "', category='" + category
                + "', estimatedHours=" + estimatedHours + ", status=" + status + "}";
    }
}
