package edu.course.lab04;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.HashMap;
import org.junit.jupiter.api.Test;


public class BookTest {

    @Test
    void valueFoundByAnotherInstanceWithSameIsbn() {
        HashMap<Book, String> map = new HashMap<>();
        Book first = new Book("111", "Java для начинающих");
        Book second = new Book("111", "Другое название"); // другой объект, тот же ISBN

        map.put(first, "полка 1");

        assertEquals("полка 1", map.get(second));
    }

    @Test
    void booksWithDifferentIsbnAreNotEqual() {
        Book a = new Book("111", "Книга");
        Book b = new Book("222", "Книга");

        assertNotEquals(a, b);
    }

    @Test
    void equalBooksHaveSameHashCode() {
        Book a = new Book("111", "Название 1");
        Book b = new Book("111", "Название 2");

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void equalsWithNullAndOtherTypeReturnsFalse() {
        Book book = new Book("111", "Книга");

        assertFalse(book.equals(null));
        assertFalse(book.equals("111")); // строка - другой тип
    }
}
