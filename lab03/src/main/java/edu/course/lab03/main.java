package edu.course.lab03;


interface DiscountPolicy {
    double apply(double price);
}


class NoDiscount implements DiscountPolicy {

    @Override
    public double apply(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Цена не может быть отрицательной");
        }
        return price;
    }
}


class PercentDiscount implements DiscountPolicy {

    private final double percent;

    public PercentDiscount(double percent) {
        if (percent < 0 || percent > 100) {
            throw new IllegalArgumentException("Процент должен быть от 0 до 100");
        }
        this.percent = percent;
    }

    @Override
    public double apply(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Цена не может быть отрицательной");
        }
        return price - price * percent / 100;
    }
}


class PriceCalculator {

    private final DiscountPolicy policy;

    public PriceCalculator(DiscountPolicy policy) {
        if (policy == null) {
            throw new IllegalArgumentException("Политика скидки не может быть null");
        }
        this.policy = policy;
    }

    public double calculate(double price) {
        return policy.apply(price);
    }
}



enum TaskStatus {
    NEW,
    IN_PROGRESS,
    DONE
}


class ProjectTask {

    private final String id;
    private final String title;
    private TaskStatus status;
    private final int estimatedHours;

    public ProjectTask(String id, String title, int estimatedHours) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("id не может быть null или пустым");
        }
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("title не может быть null или пустым");
        }
        if (estimatedHours <= 0) {
            throw new IllegalArgumentException("Оценка должна быть больше 0");
        }
        this.id = id;
        this.title = title;
        this.estimatedHours = estimatedHours;
        this.status = TaskStatus.NEW;
    }

    public void changeStatus(TaskStatus newStatus) {
        if (newStatus == null) {
            throw new IllegalArgumentException("Статус не может быть null");
        }
        this.status = newStatus;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public int getEstimatedHours() {
        return estimatedHours;
    }
}


interface PriorityPolicy {
    int calculatePriority(ProjectTask task);
}

class EffortPriorityPolicy implements PriorityPolicy {

    @Override
    public int calculatePriority(ProjectTask task) {
        if (task == null) {
            throw new IllegalArgumentException("Задача не может быть null");
        }
        return task.getEstimatedHours();
    }
}


class StatusPriorityPolicy implements PriorityPolicy {

    @Override
    public int calculatePriority(ProjectTask task) {
        if (task == null) {
            throw new IllegalArgumentException("Задача не может быть null");
        }
        TaskStatus status = task.getStatus();
        if (status == TaskStatus.IN_PROGRESS) {
            return 3;
        }
        if (status == TaskStatus.NEW) {
            return 2;
        }
        return 1; // DONE
    }
}


class TaskPrioritizer {

    private final PriorityPolicy policy;

    public TaskPrioritizer(PriorityPolicy policy) {
        if (policy == null) {
            throw new IllegalArgumentException("Политика приоритета не может быть null");
        }
        this.policy = policy;
    }

    public ProjectTask chooseHigherPriority(ProjectTask first, ProjectTask second) {
        if (first == null || second == null) {
            throw new IllegalArgumentException("Задачи не могут быть null");
        }
        int firstPriority = policy.calculatePriority(first);
        int secondPriority = policy.calculatePriority(second);
        if (secondPriority > firstPriority) {
            return second;
        }
        return first;
    }
}




interface NormalizationStrategy {
    double[] normalize(double[] values);
}


class MinMaxNormalization implements NormalizationStrategy {

    @Override
    public double[] normalize(double[] values) {
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException("values не может быть null или пустым");
        }
        double min = values[0];
        double max = values[0];
        for (int i = 1; i < values.length; i++) {
            if (values[i] < min) {
                min = values[i];
            }
            if (values[i] > max) {
                max = values[i];
            }
        }
        double[] result = new double[values.length];
        if (max == min) {
            return result;
        }
        for (int i = 0; i < values.length; i++) {
            result[i] = (values[i] - min) / (max - min);
        }
        return result;
    }
}


class MeanCenteringNormalization implements NormalizationStrategy {

    @Override
    public double[] normalize(double[] values) {
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException("values не может быть null или пустым");
        }
        double sum = 0;
        for (int i = 0; i < values.length; i++) {
            sum = sum + values[i];
        }
        double mean = sum / values.length;
        double[] result = new double[values.length];
        for (int i = 0; i < values.length; i++) {
            result[i] = values[i] - mean;
        }
        return result;
    }
}


class FeatureProcessor {

    private final NormalizationStrategy strategy;

    public FeatureProcessor(NormalizationStrategy strategy) {
        if (strategy == null) {
            throw new IllegalArgumentException("Стратегия не может быть null");
        }
        this.strategy = strategy;
    }

    public double[] process(double[] vector) {
        return strategy.normalize(vector);
    }
}