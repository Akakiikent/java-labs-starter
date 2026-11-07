package edu.course.lab03;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;


class Lab03Test {


    static class MinusTenDiscount implements DiscountPolicy {
        @Override
        public double apply(double price) {
            return price - 10;
        }
    }



    @Test
    void none_returnsSamePrice() {
        NoDiscount policy = new NoDiscount();
        assertEquals(100.0, policy.apply(100.0), 0.000001);
    }

    @Test
    void none_negativePriceThrows() {
        NoDiscount policy = new NoDiscount();
        assertThrows(IllegalArgumentException.class, () -> policy.apply(-1.0));
    }

    @Test
    void percent_twentyPercentOf100Gives80() {
        PercentDiscount policy = new PercentDiscount(20);
        assertEquals(80.0, policy.apply(100.0), 0.000001);
    }

    @Test
    void percent_zeroPercentKeepsPrice() {
        PercentDiscount policy = new PercentDiscount(0);
        assertEquals(50.0, policy.apply(50.0), 0.000001);
    }

    @Test
    void percent_hundredPercentGivesZero() {
        PercentDiscount policy = new PercentDiscount(100);
        assertEquals(0.0, policy.apply(50.0), 0.000001);
    }

    @Test
    void percent_negativePercentThrows() {
        assertThrows(IllegalArgumentException.class, () -> new PercentDiscount(-1));
    }

    @Test
    void percent_overHundredThrows() {
        assertThrows(IllegalArgumentException.class, () -> new PercentDiscount(101));
    }

    @Test
    void percent_negativePriceThrows() {
        PercentDiscount policy = new PercentDiscount(10);
        assertThrows(IllegalArgumentException.class, () -> policy.apply(-5.0));
    }

    @Test
    void calculator_usesGivenPolicy() {
        PriceCalculator calculator = new PriceCalculator(new NoDiscount());
        assertEquals(100.0, calculator.calculate(100.0), 0.000001);
    }

    @Test
    void calculator_policyCanBeReplaced() {
        PriceCalculator withoutDiscount = new PriceCalculator(new NoDiscount());
        PriceCalculator withDiscount = new PriceCalculator(new PercentDiscount(20));
        assertEquals(100.0, withoutDiscount.calculate(100.0), 0.000001);
        assertEquals(80.0, withDiscount.calculate(100.0), 0.000001);
    }

    @Test
    void calculator_worksWithNewImplementation() {
        PriceCalculator calculator = new PriceCalculator(new MinusTenDiscount());
        assertEquals(90.0, calculator.calculate(100.0), 0.000001);
    }

    @Test
    void calculator_nullPolicyThrows() {
        assertThrows(IllegalArgumentException.class, () -> new PriceCalculator(null));
    }



    @Test
    void task_validTaskIsCreated() {
        ProjectTask task = new ProjectTask("T-1", "Отчет", 5);
        assertEquals("T-1", task.getId());
        assertEquals("Отчет", task.getTitle());
        assertEquals(TaskStatus.NEW, task.getStatus());
        assertEquals(5, task.getEstimatedHours());
    }

    @Test
    void task_blankIdThrows() {
        assertThrows(IllegalArgumentException.class, () -> new ProjectTask("  ", "Отчет", 5));
    }

    @Test
    void task_zeroHoursThrows() {
        assertThrows(IllegalArgumentException.class, () -> new ProjectTask("T-1", "Отчет", 0));
    }

    @Test
    void effort_returnsEstimatedHours() {
        EffortPriorityPolicy policy = new EffortPriorityPolicy();
        ProjectTask task = new ProjectTask("T-1", "Отчет", 7);
        assertEquals(7, policy.calculatePriority(task));
    }

    @Test
    void effort_moreHoursGivesHigherPriority() {
        EffortPriorityPolicy policy = new EffortPriorityPolicy();
        ProjectTask small = new ProjectTask("T-1", "Малая", 2);
        ProjectTask big = new ProjectTask("T-2", "Большая", 10);
        assertEquals(true, policy.calculatePriority(big) > policy.calculatePriority(small));
    }

    @Test
    void effort_nullTaskThrows() {
        EffortPriorityPolicy policy = new EffortPriorityPolicy();
        assertThrows(IllegalArgumentException.class, () -> policy.calculatePriority(null));
    }

    @Test
    void status_inProgressHigherThanNew() {
        StatusPriorityPolicy policy = new StatusPriorityPolicy();
        ProjectTask newTask = new ProjectTask("T-1", "Новая", 5);
        ProjectTask working = new ProjectTask("T-2", "В работе", 5);
        working.changeStatus(TaskStatus.IN_PROGRESS);
        assertEquals(true, policy.calculatePriority(working) > policy.calculatePriority(newTask));
    }

    @Test
    void status_newHigherThanDone() {
        StatusPriorityPolicy policy = new StatusPriorityPolicy();
        ProjectTask newTask = new ProjectTask("T-1", "Новая", 5);
        ProjectTask doneTask = new ProjectTask("T-2", "Готовая", 5);
        doneTask.changeStatus(TaskStatus.DONE);
        assertEquals(true, policy.calculatePriority(newTask) > policy.calculatePriority(doneTask));
    }

    @Test
    void status_nullTaskThrows() {
        StatusPriorityPolicy policy = new StatusPriorityPolicy();
        assertThrows(IllegalArgumentException.class, () -> policy.calculatePriority(null));
    }

    @Test
    void prioritizer_effortChoosesTaskWithMoreHours() {
        TaskPrioritizer prioritizer = new TaskPrioritizer(new EffortPriorityPolicy());
        ProjectTask small = new ProjectTask("T-1", "Малая", 2);
        ProjectTask big = new ProjectTask("T-2", "Большая", 10);
        assertSame(big, prioritizer.chooseHigherPriority(small, big));
    }

    @Test
    void prioritizer_statusChoosesTaskInProgress() {
        TaskPrioritizer prioritizer = new TaskPrioritizer(new StatusPriorityPolicy());
        ProjectTask newTask = new ProjectTask("T-1", "Новая", 5);
        ProjectTask working = new ProjectTask("T-2", "В работе", 5);
        working.changeStatus(TaskStatus.IN_PROGRESS);
        assertSame(working, prioritizer.chooseHigherPriority(newTask, working));
    }

    @Test
    void prioritizer_policyCanBeReplaced() {
        ProjectTask a = new ProjectTask("A", "Старая большая", 10);
        a.changeStatus(TaskStatus.DONE);
        ProjectTask b = new ProjectTask("B", "Текущая маленькая", 3);
        b.changeStatus(TaskStatus.IN_PROGRESS);

        TaskPrioritizer byEffort = new TaskPrioritizer(new EffortPriorityPolicy());
        TaskPrioritizer byStatus = new TaskPrioritizer(new StatusPriorityPolicy());

        assertSame(a, byEffort.chooseHigherPriority(a, b));
        assertSame(b, byStatus.chooseHigherPriority(a, b));
    }

    @Test
    void prioritizer_tieReturnsFirstTask() {
        TaskPrioritizer prioritizer = new TaskPrioritizer(new EffortPriorityPolicy());
        ProjectTask first = new ProjectTask("T-1", "Первая", 5);
        ProjectTask second = new ProjectTask("T-2", "Вторая", 5);
        assertSame(first, prioritizer.chooseHigherPriority(first, second));
    }

    @Test
    void prioritizer_nullPolicyThrows() {
        assertThrows(IllegalArgumentException.class, () -> new TaskPrioritizer(null));
    }

    @Test
    void prioritizer_nullTaskThrows() {
        TaskPrioritizer prioritizer = new TaskPrioritizer(new EffortPriorityPolicy());
        ProjectTask task = new ProjectTask("T-1", "Задача", 5);
        assertThrows(IllegalArgumentException.class, () -> prioritizer.chooseHigherPriority(task, null));
    }

    @Test
    void prioritizer_doesNotChangeTasks() {
        TaskPrioritizer prioritizer = new TaskPrioritizer(new StatusPriorityPolicy());
        ProjectTask first = new ProjectTask("T-1", "Первая", 5);
        ProjectTask second = new ProjectTask("T-2", "Вторая", 8);
        second.changeStatus(TaskStatus.IN_PROGRESS);
        prioritizer.chooseHigherPriority(first, second);
        assertEquals(TaskStatus.NEW, first.getStatus());
        assertEquals(5, first.getEstimatedHours());
        assertEquals(TaskStatus.IN_PROGRESS, second.getStatus());
        assertEquals(8, second.getEstimatedHours());
    }



    @Test
    void minmax_scalesToZeroOne() {
        MinMaxNormalization strategy = new MinMaxNormalization();
        double[] result = strategy.normalize(new double[] {2.0, 4.0, 6.0});
        assertArrayEquals(new double[] {0.0, 0.5, 1.0}, result, 0.000001);
    }

    @Test
    void minmax_equalValuesGiveZeros() {
        MinMaxNormalization strategy = new MinMaxNormalization();
        double[] result = strategy.normalize(new double[] {5.0, 5.0, 5.0});
        assertArrayEquals(new double[] {0.0, 0.0, 0.0}, result, 0.000001);
    }

    @Test
    void minmax_emptyArrayThrows() {
        MinMaxNormalization strategy = new MinMaxNormalization();
        assertThrows(IllegalArgumentException.class, () -> strategy.normalize(new double[0]));
    }

    @Test
    void minmax_doesNotChangeInput() {
        MinMaxNormalization strategy = new MinMaxNormalization();
        double[] input = {2.0, 4.0, 6.0};
        strategy.normalize(input);
        assertArrayEquals(new double[] {2.0, 4.0, 6.0}, input, 0.000001);
    }

    @Test
    void centering_subtractsMean() {
        MeanCenteringNormalization strategy = new MeanCenteringNormalization();
        double[] result = strategy.normalize(new double[] {1.0, 2.0, 6.0});
        assertArrayEquals(new double[] {-2.0, -1.0, 3.0}, result, 0.000001);
    }

    @Test
    void centering_nullArrayThrows() {
        MeanCenteringNormalization strategy = new MeanCenteringNormalization();
        assertThrows(IllegalArgumentException.class, () -> strategy.normalize(null));
    }

    @Test
    void centering_doesNotChangeInput() {
        MeanCenteringNormalization strategy = new MeanCenteringNormalization();
        double[] input = {1.0, 2.0, 6.0};
        strategy.normalize(input);
        assertArrayEquals(new double[] {1.0, 2.0, 6.0}, input, 0.000001);
    }

    @Test
    void processor_usesMinMax() {
        FeatureProcessor processor = new FeatureProcessor(new MinMaxNormalization());
        double[] result = processor.process(new double[] {2.0, 4.0, 6.0});
        assertArrayEquals(new double[] {0.0, 0.5, 1.0}, result, 0.000001);
    }

    @Test
    void processor_usesCentering() {
        FeatureProcessor processor = new FeatureProcessor(new MeanCenteringNormalization());
        double[] result = processor.process(new double[] {1.0, 2.0, 6.0});
        assertArrayEquals(new double[] {-2.0, -1.0, 3.0}, result, 0.000001);
    }

    @Test
    void processor_strategyCanBeReplaced() {
        double[] vector = {2.0, 4.0, 6.0};
        FeatureProcessor minMax = new FeatureProcessor(new MinMaxNormalization());
        FeatureProcessor centering = new FeatureProcessor(new MeanCenteringNormalization());
        assertArrayEquals(new double[] {0.0, 0.5, 1.0}, minMax.process(vector), 0.000001);
        assertArrayEquals(new double[] {-2.0, 0.0, 2.0}, centering.process(vector), 0.000001);
    }

    @Test
    void processor_nullStrategyThrows() {
        assertThrows(IllegalArgumentException.class, () -> new FeatureProcessor(null));
    }

    @Test
    void processor_returnsNewArray() {
        FeatureProcessor processor = new FeatureProcessor(new MeanCenteringNormalization());
        double[] input = {1.0, 2.0, 3.0};
        double[] result = processor.process(input);
        assertNotSame(input, result);
    }
}