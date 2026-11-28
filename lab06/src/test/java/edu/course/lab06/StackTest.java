package edu.course.lab06;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

class StackTest {

    @Test
    void newStackIsEmpty() {
        Stack<String> stack = new Stack<>();
        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());
    }

    @Test
    void pushMakesStackNotEmpty() {
        Stack<String> stack = new Stack<>();
        stack.push("a");
        assertFalse(stack.isEmpty());
        assertEquals(1, stack.size());
    }

    @Test
    void peekReturnsLastAndKeepsIt() {
        Stack<String> stack = new Stack<>();
        stack.push("a");
        stack.push("b");
        assertEquals("b", stack.peek());
        assertEquals("b", stack.peek()); // второй peek даёт то же самое
        assertEquals(2, stack.size());
    }

    @Test
    void popReturnsInReverseOrder() {
        Stack<String> stack = new Stack<>();
        stack.push("a");
        stack.push("b");
        stack.push("c");
        assertEquals("c", stack.pop());
        assertEquals("b", stack.pop());
        assertEquals("a", stack.pop());
        assertTrue(stack.isEmpty());
    }

    @Test
    void worksWithIntegers() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        int top = stack.pop(); // без приведений
        assertEquals(2, top);
        assertEquals(1, stack.peek());
    }

    @Test
    void worksWithProjectTasks() {
        Stack<ProjectTask> stack = new Stack<>();
        ProjectTask task = new ProjectTask(1, "Сделать вход", "TODO", "backend", 3);
        stack.push(task);
        assertEquals(task, stack.pop());
    }

    @Test
    void popOnEmptyStackThrows() {
        Stack<String> stack = new Stack<>();
        assertThrows(NoSuchElementException.class, () -> stack.pop());
    }

    @Test
    void peekOnEmptyStackThrows() {
        Stack<String> stack = new Stack<>();
        assertThrows(NoSuchElementException.class, () -> stack.peek());
    }

    @Test
    void popAfterEmptyingThrows() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.pop();
        assertThrows(NoSuchElementException.class, () -> stack.pop());
    }

    @Test
    void pushNullIsForbidden() {
        Stack<String> stack = new Stack<>();
        assertThrows(NullPointerException.class, () -> stack.push(null));
        assertTrue(stack.isEmpty());
    }
}
