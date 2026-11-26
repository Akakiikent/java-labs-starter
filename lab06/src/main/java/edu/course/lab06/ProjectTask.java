package edu.course.lab06;


public class ProjectTask {

    private final int id;
    private final String title;
    private final String status;
    private final String category;
    private final int effort;

    public ProjectTask(int id, String title, String status, String category, int effort) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.category = category;
        this.effort = effort;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getStatus() {
        return status;
    }

    public String getCategory() {
        return category;
    }

    public int getEffort() {
        return effort;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProjectTask other = (ProjectTask) o;
        return id == other.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProjectTask{id=" + id + ", status=" + status + ", category=" + category
                + ", effort=" + effort + "}";
    }
}
