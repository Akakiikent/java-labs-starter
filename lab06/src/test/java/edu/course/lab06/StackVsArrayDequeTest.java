package edu.course.lab06;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayDeque;
import java.util.Deque;
import org.junit.jupiter.api.Test;

class StackVsArrayDequeTest {

    @Test
    void sameOrderAsArrayDeque() {
        Stack<Integer> mine = new Stack<>();
        Deque<Integer> standard = new ArrayDeque<>();
        for (int i = 1; i <= 5; i++) {
            mine.push(i);
            standard.push(i);
        }
        assertEquals(standard.peek(), mine.peek());
        while (!mine.isEmpty()) {
            assertEquals(standard.pop(), mine.pop());
        }
        assertEquals(0, standard.size());
    }
}
