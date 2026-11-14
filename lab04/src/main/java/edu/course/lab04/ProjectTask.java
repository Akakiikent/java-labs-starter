package edu.course.lab04;

public class ProjectTask {
    private final String title;
    private final boolean done;

    public ProjectTask(String title, boolean done) {
        this.title = title;
        this.done = done;
    }

    public String getTitle() {
        return title;
    }

    public boolean isDone() {
        return done;
    }

    @Override
    public String toString() {
        return "ProjectTask{title=" + title + ", done=" + done + "}";
    }
}
