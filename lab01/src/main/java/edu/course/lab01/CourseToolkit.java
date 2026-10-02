package edu.course.lab01;

/**
 * Небольшие методы для первой лабораторной работы.
 */
public final class CourseToolkit {

    private CourseToolkit() {
        // Утилитарный класс не должен иметь экземпляров.
    }

    /**
     * Возвращает true, если число четное.
     */
    public static boolean isEven(int number) {
        return number % 2 == 0;
    }

    public static boolean isPrime(int number){
        if (number < 2){
            return false;

        }
        for (int i = 2; i*i <= number; i++){
            if (number%i == 0){
                return false;
            }
        }
        return  true;
    }




}
