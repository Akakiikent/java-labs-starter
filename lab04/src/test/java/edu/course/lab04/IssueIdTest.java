package edu.course.lab04;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class IssueIdTest {

    @Test
    void idsWithSameFieldsAreEqualAndHaveSameHashCode() {
        IssueId a = new IssueId("APP", 7);
        IssueId b = new IssueId("APP", 7);

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void idsWithDifferentFieldsAreNotEqual() {
        IssueId base = new IssueId("APP", 7);

        assertNotEquals(base, new IssueId("APP", 8));
        assertNotEquals(base, new IssueId("WEB", 7));
    }


    @Test
    void equalsWithNullAndOtherTypeReturnsFalse() {
        IssueId id = new IssueId("APP", 1);

        assertFalse(id.equals(null));
        assertFalse(id.equals("APP-1"));
        assertFalse(id.equals(new Book("111", "Книга")));
    }
}
