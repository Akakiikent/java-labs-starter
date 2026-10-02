package edu.course.lab01;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CourseToolkitTest {

    @Test
    void returnsTrueForEvenNumber() {
        boolean result = CourseToolkit.isEven(8);

        assertTrue(result);
    }

    @Test
    void returnsFalseForOddNumber() {
        boolean result = CourseToolkit.isEven(7);

        assertFalse(result);
    }
    @Test
    void isEven_shouldReturnTrueForZero() {
        assertTrue(CourseToolkit.isEven(0));
    }


    @Test
    void isPrime_false1(){
        assertFalse(CourseToolkit.isPrime(1));
    }


    @Test
    void IsPrime_true2(){
        assertTrue(CourseToolkit.isPrime(2));
    }
    @Test
    void isPrime_false4(){
        assertFalse(CourseToolkit.isPrime(4));
    }
    
    @Test
    void Isprime_false49(){
        assertFalse(CourseToolkit.isPrime(49));
    }
}
