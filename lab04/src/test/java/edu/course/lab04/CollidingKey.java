package edu.course.lab04;

import java.util.Objects;

public class CollidingKey {
    private final String name;

    public CollidingKey(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CollidingKey other = (CollidingKey) o;
        return Objects.equals(name, other.name);
    }

    @Override
    public int hashCode() {
        return 42;
    }
}
