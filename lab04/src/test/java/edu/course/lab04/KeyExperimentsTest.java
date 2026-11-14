package edu.course.lab04;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.HashMap;
import org.junit.jupiter.api.Test;

public class KeyExperimentsTest {

    @Test
    void collidingKeysKeepDifferentValues() {
        CollidingKey a = new CollidingKey("a");
        CollidingKey b = new CollidingKey("b");

        assertEquals(a.hashCode(), b.hashCode()); // хеши одинаковые
        assertNotEquals(a, b);                    // но ключи разные

        HashMap<CollidingKey, String> map = new HashMap<>();
        map.put(a, "значение A");
        map.put(b, "значение B");

        assertEquals(2, map.size());
        assertEquals("значение A", map.get(a));
        assertEquals("значение B", map.get(b));
    }

    @Test
    void mutatingKeyAfterPutBreaksLookup() {
        HashMap<MutableKey, String> map = new HashMap<>();
        MutableKey key = new MutableKey("A");
        map.put(key, "значение");

        assertEquals("значение", map.get(key)); // пока ключ не менялся, все работает

        key.setName("Z");

        assertNull(map.get(key));
        assertFalse(map.containsKey(key));
        assertNull(map.get(new MutableKey("A")));
        assertEquals(1, map.size());
    }
}
