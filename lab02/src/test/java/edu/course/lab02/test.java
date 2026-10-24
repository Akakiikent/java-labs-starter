

package edu.course.lab02;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class Lab02Test {
    @Test
    void account_withdrawReducesBalance() {
        BankAccount account = new BankAccount(100);
        account.withdraw(30);
        assertEquals(70, account.getBalance());
    }

    @Test
    void account_withdrawMoreThanBalanceThrows() {
        BankAccount account = new BankAccount(100);
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(101));
        // после ошибки баланс не должен измениться
        assertEquals(100, account.getBalance());
    }

    @Test
    void account_depositIncreasesBalance() {
        BankAccount account = new BankAccount(10);
        account.deposit(5);
        assertEquals(15, account.getBalance());
    }

    @Test
    void account_negativeInitialBalanceThrows() {
        assertThrows(IllegalArgumentException.class, () -> new BankAccount(-1));
    }

    @Test
    void account_depositNonPositiveThrows() {
        BankAccount account = new BankAccount(10);
        assertThrows(IllegalArgumentException.class, () -> account.deposit(0));
        assertThrows(IllegalArgumentException.class, () -> account.deposit(-5));
    }

    @Test
    void account_withdrawNonPositiveThrows() {
        BankAccount account = new BankAccount(10);
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(0));
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(-5));
    }


    @Test
    void task_validTaskIsCreated() {
        ProjectTask task = new ProjectTask("T-1", "Написать отчет", 5);
        assertEquals("T-1", task.getId());
        assertEquals("Написать отчет", task.getTitle());
        assertEquals(TaskStatus.NEW, task.getStatus());
        assertEquals(5, task.getEstimatedHours());
        assertFalse(task.isDone());
    }

    @Test
    void task_nullIdThrows() {
        assertThrows(IllegalArgumentException.class, () -> new ProjectTask(null, "Title", 5));
    }

    @Test
    void task_blankIdThrows() {
        assertThrows(IllegalArgumentException.class, () -> new ProjectTask("   ", "Title", 5));
    }

    @Test
    void task_nullTitleThrows() {
        assertThrows(IllegalArgumentException.class, () -> new ProjectTask("T-1", null, 5));
    }

    @Test
    void task_emptyTitleThrows() {
        assertThrows(IllegalArgumentException.class, () -> new ProjectTask("T-1", "", 5));
    }

    @Test
    void task_zeroHoursThrows() {
        assertThrows(IllegalArgumentException.class, () -> new ProjectTask("T-1", "Title", 0));
    }

    @Test
    void task_negativeHoursThrows() {
        assertThrows(IllegalArgumentException.class, () -> new ProjectTask("T-1", "Title", -3));
    }

    @Test
    void task_changeStatusToDoneMakesTaskDone() {
        ProjectTask task = new ProjectTask("T-1", "Title", 5);
        task.changeStatus(TaskStatus.DONE);
        assertEquals(TaskStatus.DONE, task.getStatus());
        assertTrue(task.isDone());
    }

    @Test
    void task_changeStatusToNullThrows() {
        ProjectTask task = new ProjectTask("T-1", "Title", 5);
        assertThrows(IllegalArgumentException.class, () -> task.changeStatus(null));
        assertEquals(TaskStatus.NEW, task.getStatus());
    }

    @Test
    void task_increaseEstimateAddsHours() {
        ProjectTask task = new ProjectTask("T-1", "Title", 5);
        task.increaseEstimate(3);
        assertEquals(8, task.getEstimatedHours());
    }

    @Test
    void task_increaseEstimateWithNonPositiveThrows() {
        ProjectTask task = new ProjectTask("T-1", "Title", 5);
        assertThrows(IllegalArgumentException.class, () -> task.increaseEstimate(0));
        assertThrows(IllegalArgumentException.class, () -> task.increaseEstimate(-2));
        assertEquals(5, task.getEstimatedHours());
    }


    @Test
    void sample_validSampleIsCreated() {
        DataSample sample = new DataSample("S-1", "cat", new double[] {1.0, 2.0, 3.0});
        assertEquals("S-1", sample.getId());
        assertEquals("cat", sample.getLabel());
        assertEquals(SampleStatus.NEW, sample.getStatus());
        assertFalse(sample.isReady());
        assertArrayEquals(new double[] {1.0, 2.0, 3.0}, sample.getFeatures());
    }

    @Test
    void sample_nullIdThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> new DataSample(null, "cat", new double[] {1.0}));
    }

    @Test
    void sample_blankIdThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> new DataSample("  ", "cat", new double[] {1.0}));
    }

    @Test
    void sample_emptyLabelThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> new DataSample("S-1", "", new double[] {1.0}));
    }

    @Test
    void sample_nullFeaturesThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> new DataSample("S-1", "cat", null));
    }

    @Test
    void sample_emptyFeaturesThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> new DataSample("S-1", "cat", new double[0]));
    }

    @Test
    void sample_nanFeatureThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> new DataSample("S-1", "cat", new double[] {1.0, Double.NaN}));
    }

    @Test
    void sample_infiniteFeatureThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> new DataSample("S-1", "cat", new double[] {Double.POSITIVE_INFINITY}));
    }

    @Test
    void sample_changeStatusToReadyMakesSampleReady() {
        DataSample sample = new DataSample("S-1", "cat", new double[] {1.0});
        sample.changeStatus(SampleStatus.READY);
        assertTrue(sample.isReady());
    }

    @Test
    void sample_changeStatusToNullThrows() {
        DataSample sample = new DataSample("S-1", "cat", new double[] {1.0});
        assertThrows(IllegalArgumentException.class, () -> sample.changeStatus(null));
    }

    @Test
    void sample_averageIsCalculatedCorrectly() {
        DataSample sample = new DataSample("S-1", "cat", new double[] {1.0, 2.0, 6.0});
        assertEquals(3.0, sample.averageFeature(), 0.000001);
    }

    @Test
    void sample_constructorCopiesArray() {
        double[] source = {1.0, 2.0};
        DataSample sample = new DataSample("S-1", "cat", source);
        source[0] = 100.0; // меняем исходный массив после создания объекта
        assertArrayEquals(new double[] {1.0, 2.0}, sample.getFeatures());
    }

    @Test
    void sample_getterReturnsCopy() {
        DataSample sample = new DataSample("S-1", "cat", new double[] {1.0, 2.0});
        double[] returned = sample.getFeatures();
        returned[0] = 100.0; // меняем возвращенную копию
        assertArrayEquals(new double[] {1.0, 2.0}, sample.getFeatures());
    }
}
