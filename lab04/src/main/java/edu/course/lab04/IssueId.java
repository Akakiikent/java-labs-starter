package edu.course.lab04;

import java.util.Objects;


public class IssueId {
    private final String projectCode;
    private final int number;

    public IssueId(String projectCode, int number) {
        this.projectCode = projectCode;
        this.number = number;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IssueId other = (IssueId) o;
        return number == other.number && Objects.equals(projectCode, other.projectCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectCode, number);
    }

    @Override
    public String toString() {
        return projectCode + "-" + number;
    }
}
