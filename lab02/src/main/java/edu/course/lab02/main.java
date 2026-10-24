

package edu.course.lab02;

class BankAccount {

    private int balance;

    public BankAccount(int initialBalance) {
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Начальный баланс не может быть отрицательным");
        }
        this.balance = initialBalance;
    }

    public void deposit(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Сумма пополнения должна быть положительной");
        }
        balance = balance + amount;
    }

    public void withdraw(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Сумма списания должна быть положительной");
        }
        if (amount > balance) {
            throw new IllegalArgumentException("Недостаточно средств на счете");
        }
        balance = balance - amount;
    }


    public int getBalance() {
        return balance;
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
    private int estimatedHours;

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
        this.status = TaskStatus.NEW; // новая задача всегда начинается со статуса NEW
    }

    public void changeStatus(TaskStatus newStatus) {
        if (newStatus == null) {
            throw new IllegalArgumentException("Статус не может быть null");
        }
        this.status = newStatus;
    }

    public boolean isDone() {
        return status == TaskStatus.DONE;
    }

    public void increaseEstimate(int hours) {
        if (hours <= 0) {
            throw new IllegalArgumentException("Нужно добавить положительное число часов");
        }
        estimatedHours = estimatedHours + hours;
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


enum SampleStatus {
    NEW,
    READY,
    REJECTED
}



class DataSample {

    private final String id;
    private final String label;
    private SampleStatus status;
    private final double[] features;

    public DataSample(String id, String label, double[] features) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("id не может быть null или пустым");
        }
        if (label == null || label.trim().isEmpty()) {
            throw new IllegalArgumentException("label не может быть null или пустым");
        }
        if (features == null || features.length == 0) {
            throw new IllegalArgumentException("features не может быть null или пустым");
        }
        for (int i = 0; i < features.length; i++) {
            if (Double.isNaN(features[i]) || Double.isInfinite(features[i])) {
                throw new IllegalArgumentException("Признак с индексом " + i + " не является числом");
            }
        }
        this.id = id;
        this.label = label;
        this.status = SampleStatus.NEW;

        // Копируем массив, чтобы внешний код не мог изменить наш внутренний.
        this.features = new double[features.length];
        for (int i = 0; i < features.length; i++) {
            this.features[i] = features[i];
        }
    }

    public void changeStatus(SampleStatus newStatus) {
        if (newStatus == null) {
            throw new IllegalArgumentException("Статус не может быть null");
        }
        this.status = newStatus;
    }

    public boolean isReady() {
        return status == SampleStatus.READY;
    }

    public double averageFeature() {
        double sum = 0;
        for (int i = 0; i < features.length; i++) {
            sum = sum + features[i];
        }
        return sum / features.length;
    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public SampleStatus getStatus() {
        return status;
    }


    public double[] getFeatures() {
        double[] copy = new double[features.length];
         for (int i = 0; i < features.length; i++) {
            copy[i] = features[i];
        }
        return copy;
    }
}
