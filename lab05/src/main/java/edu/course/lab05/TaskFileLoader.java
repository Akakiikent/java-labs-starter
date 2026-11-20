package edu.course.lab05;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public final class TaskFileLoader {

    private static final String HEADER = "id;title;category;estimatedHours;status";
    private static final String DELIMITER = ";";
    private static final int COLUMNS = 5;

    public LoadResult load(Path path) {
        List<String> lines = readLines(path);
        checkHeader(path, lines);

        List<ProjectTask> tasks = new ArrayList<>();
        List<ValidationError> errors = new ArrayList<>();

        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.isBlank()) {
                continue; // пустые строки пропускаем
            }
            parseLine(i + 1, line, tasks, errors);
        }
        return new LoadResult(tasks, errors);
    }

    private List<String> readLines(Path path) {
        try {
            return Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (IOException e) {

            throw new DataLoadingException("Cannot read " + path, e);
        }
    }

    private void checkHeader(Path path, List<String> lines) {
        if (lines.isEmpty()) {
            throw new DataLoadingException("File is empty, header expected: " + path);
        }
        String header = lines.get(0);
        if (header.startsWith("﻿")) {
            header = header.substring(1);
        }
        if (!HEADER.equals(header.trim())) {
            throw new DataLoadingException("Unexpected header in " + path + ": '" + header + "'");
        }
    }

    private void parseLine(int lineNumber, String line, List<ProjectTask> tasks, List<ValidationError> errors) {
        String[] parts = line.split(DELIMITER, -1);
        if (parts.length != COLUMNS) {
            errors.add(new ValidationError(lineNumber, "columns",
                    "expected " + COLUMNS + " fields but found " + parts.length));
            return;
        }

        String id = parts[0].trim();
        String title = parts[1].trim();
        String category = parts[2].trim();
        String hoursText = parts[3].trim();
        String statusText = parts[4].trim();

        if (id.isEmpty()) {
            errors.add(new ValidationError(lineNumber, "id", "must not be empty"));
            return;
        }
        if (title.isEmpty()) {
            errors.add(new ValidationError(lineNumber, "title", "must not be empty"));
            return;
        }
        if (category.isEmpty()) {
            errors.add(new ValidationError(lineNumber, "category", "must not be empty"));
            return;
        }
        if (hoursText.isEmpty()) {
            errors.add(new ValidationError(lineNumber, "estimatedHours", "must not be empty"));
            return;
        }

        int hours;
        try {
            hours = Integer.parseInt(hoursText);
        } catch (NumberFormatException e) {
            errors.add(new ValidationError(lineNumber, "estimatedHours", "not a number: '" + hoursText + "'"));
            return;
        }
        if (hours <= 0) {
            errors.add(new ValidationError(lineNumber, "estimatedHours", "must be positive: " + hours));
            return;
        }

        if (statusText.isEmpty()) {
            errors.add(new ValidationError(lineNumber, "status", "must not be empty"));
            return;
        }
        TaskStatus status;
        try {
            status = TaskStatus.valueOf(statusText);
        } catch (IllegalArgumentException e) {
            errors.add(new ValidationError(lineNumber, "status", "unknown status: '" + statusText + "'"));
            return;
        }


        tasks.add(new ProjectTask(id, title, category, hours, status));
    }
}
