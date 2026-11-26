package edu.course.lab06;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class BoxTest {

    @Test
    void boxOfString() {
        Box<String> box = new Box<>("привет");
        assertEquals("привет", box.get());
        box.set("пока");
        assertEquals("пока", box.get());
    }

    @Test
    void boxOfInteger() {
        Box<Integer> box = new Box<>(5);
        int number = box.get(); // приведение не нужно, компилятор знает тип
        assertEquals(5, number);
        box.set(10);
        assertEquals(10, box.get());
    }


}
