package edu.course.lab01;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;


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
    void IsPrime_false0(){
        assertFalse(CourseToolkit.isPrime(0));
    }
    @Test
    void isPrime_falsemin5(){
        assertFalse(CourseToolkit.isPrime(-5));
    }
    @Test
    void Isprime_true2(){
        assertTrue(CourseToolkit.isPrime(2));
    }

    @Test
    void isPrime_true7(){
        assertTrue(CourseToolkit.isPrime(7));
    }
    @Test
    void isPrime_false4(){
        assertFalse(CourseToolkit.isPrime(4));
    }

    @Test
    void Isprime_false49(){
        assertFalse(CourseToolkit.isPrime(49));
    }

    @Test
    void isPallindrome_trueshalash(){
        assertTrue(CourseToolkit.isPalindrome("шалаш"));
    }
    @Test
    void isPallindrome_falsejaba(){
        assertFalse(CourseToolkit.isPalindrome("жаба"));
    }
    @Test
    void isPallindrome_falseAa(){
        assertFalse(CourseToolkit.isPalindrome("Aaaa"));
    }
    @Test
    void isPallindrome_falsemalomesta(){
        assertFalse(CourseToolkit.isPalindrome("ап"));
    }
    @Test
    void isPallindrome_truepustoy(){
        assertTrue(CourseToolkit.isPalindrome(""));
    }
    @Test
    void isPallindrome_throw0(){
        assertThrows(IllegalArgumentException.class, () -> CourseToolkit.isPalindrome(null));
        
    }
}
