package edu.course.lab05;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class TaskFileLoaderTest {

    private static final String HEADER = "id;title;category;estimatedHours;status";

    @TempDir
    Path tempDir;

    private final TaskFileLoader loader = new TaskFileLoader();

    private Path writeFile(String... lines) throws Exception {
        Path file = tempDir.resolve("tasks.csv");
        Files.write(file, List.of(lines), StandardCharsets.UTF_8);
        return file;
    }

    @Test
    void loadsValidFile() throws Exception {
        Path file = writeFile(
                HEADER,
                "T-101;Prepare release;backend;8;READY",
                "T-102;Write docs;docs;3;DONE");

        LoadResult result = loader.load(file);

        assertFalse(result.hasErrors());
        assertEquals(2, result.getTasks().size());
        ProjectTask first = result.getTasks().get(0);
        assertEquals("T-101", first.getId());
        assertEquals("Prepare release", first.getTitle());
        assertEquals("backend", first.getCategory());
        assertEquals(8, first.getEstimatedHours());
        assertEquals(TaskStatus.READY, first.getStatus());
    }

    @Test
    void readsCyrillicText() throws Exception {
        Path file = writeFile(
                HEADER,
                "T-201;Подготовить релиз;бэкенд;5;NEW");

        LoadResult result = loader.load(file);

        assertFalse(result.hasErrors());
        assertEquals("Подготовить релиз", result.getTasks().get(0).getTitle());
        assertEquals("бэкенд", result.getTasks().get(0).getCategory());
    }

    @Test
    void emptyFieldIsReportedWithLineAndFieldName() throws Exception {
        Path file = writeFile(
                HEADER,
                "T-101;;backend;8;READY");

        LoadResult result = loader.load(file);

        assertTrue(result.getTasks().isEmpty());
        assertEquals(1, result.getErrors().size());
        ValidationError error = result.getErrors().get(0);
        assertEquals(2, error.getLineNumber());
        assertEquals("title", error.getField());
    }

    @Test
    void invalidNumberIsReported() throws Exception {
        Path file = writeFile(
                HEADER,
                "T-101;Prepare release;backend;eight;READY");

        LoadResult result = loader.load(file);

        assertTrue(result.getTasks().isEmpty());
        assertEquals(1, result.getErrors().size());
        assertEquals("estimatedHours", result.getErrors().get(0).getField());
        assertEquals(2, result.getErrors().get(0).getLineNumber());
    }

    @Test
    void nonPositiveHoursAreRejected() throws Exception {
        Path file = writeFile(
                HEADER,
                "T-101;Prepare release;backend;0;READY",
                "T-102;Other;backend;-5;READY");

        LoadResult result = loader.load(file);

        assertTrue(result.getTasks().isEmpty());
        assertEquals(2, result.getErrors().size());
        assertEquals("estimatedHours", result.getErrors().get(0).getField());
        assertEquals("estimatedHours", result.getErrors().get(1).getField());
    }

    @Test
    void unknownStatusIsReported() throws Exception {
        Path file = writeFile(
                HEADER,
                "T-101;Prepare release;backend;8;ARCHIVED");

        LoadResult result = loader.load(file);

        assertTrue(result.getTasks().isEmpty());
        assertEquals(1, result.getErrors().size());
        assertEquals("status", result.getErrors().get(0).getField());
    }

    @Test
    void wrongNumberOfColumnsIsReported() throws Exception {
        Path file = writeFile(
                HEADER,
                "T-101;Prepare release;backend;8",
                "T-102;Prepare release;backend;8;READY;extra");

        LoadResult result = loader.load(file);

        assertTrue(result.getTasks().isEmpty());
        assertEquals(2, result.getErrors().size());
        assertEquals("columns", result.getErrors().get(0).getField());
        assertEquals(2, result.getErrors().get(0).getLineNumber());
        assertEquals("columns", result.getErrors().get(1).getField());
        assertEquals(3, result.getErrors().get(1).getLineNumber());
    }

    @Test
    void emptyLastFieldIsNotLostBySplit() throws Exception {
        // в строке 5 полей, последнее пустое: ошибка должна быть про status, а не про число столбцов
        Path file = writeFile(
                HEADER,
                "T-101;Prepare release;backend;8;");

        LoadResult result = loader.load(file);

        assertEquals(1, result.getErrors().size());
        assertEquals("status", result.getErrors().get(0).getField());
    }

    @Test
    void validAndInvalidLinesAreProcessedTogether() throws Exception {
        Path file = writeFile(
                HEADER,
                "T-101;Prepare release;backend;8;READY",
                "T-102;Broken;backend;abc;READY",
                "",
                "T-103;Write docs;docs;3;DONE",
                "T-104;Unknown status;docs;2;WHATEVER",
                "T-105;Last one;qa;1;NEW");

        LoadResult result = loader.load(file);

        assertEquals(3, result.getTasks().size());
        assertEquals("T-101", result.getTasks().get(0).getId());
        assertEquals("T-103", result.getTasks().get(1).getId());
        assertEquals("T-105", result.getTasks().get(2).getId());

        assertEquals(2, result.getErrors().size());
        assertEquals(3, result.getErrors().get(0).getLineNumber());
        assertEquals(6, result.getErrors().get(1).getLineNumber());
    }

    @Test
    void blankLinesAreSkipped() throws Exception {
        Path file = writeFile(
                HEADER,
                "",
                "T-101;Prepare release;backend;8;READY",
                "   ");

        LoadResult result = loader.load(file);

        assertFalse(result.hasErrors());
        assertEquals(1, result.getTasks().size());
    }

    @Test
    void missingFileThrowsDataLoadingExceptionWithCause() {
        Path missing = tempDir.resolve("no-such-file.csv");

        DataLoadingException exception =
                assertThrows(DataLoadingException.class, () -> loader.load(missing));

        assertTrue(exception.getMessage().contains("no-such-file.csv"));
        assertInstanceOf(NoSuchFileException.class, exception.getCause());
    }

    @Test
    void directoryInsteadOfFileThrowsDataLoadingException() {
        DataLoadingException exception =
                assertThrows(DataLoadingException.class, () -> loader.load(tempDir));

        assertInstanceOf(IOException.class, exception.getCause());
    }

    @Test
    void invalidHeaderIsSourceError() throws Exception {
        Path file = writeFile(
                "id;name;category;hours;status",
                "T-101;Prepare release;backend;8;READY");

        assertThrows(DataLoadingException.class, () -> loader.load(file));
    }

    @Test
    void emptyFileIsSourceError() throws Exception {
        Path file = tempDir.resolve("empty.csv");
        Files.write(file, new byte[0]);

        assertThrows(DataLoadingException.class, () -> loader.load(file));
    }

    @Test
    void fileWithOnlyHeaderGivesEmptyResult() throws Exception {
        Path file = writeFile(HEADER);

        LoadResult result = loader.load(file);

        assertTrue(result.getTasks().isEmpty());
        assertFalse(result.hasErrors());
    }

    @Test
    void utf8BomBeforeHeaderIsAccepted() throws Exception {
        Path file = tempDir.resolve("bom.csv");
        String content = "﻿" + HEADER + "\nT-101;Prepare release;backend;8;READY\n";
        Files.write(file, content.getBytes(StandardCharsets.UTF_8));

        LoadResult result = loader.load(file);

        assertFalse(result.hasErrors());
        assertEquals(1, result.getTasks().size());
    }

    @Test
    void invalidUtf8BytesAreSourceError() throws Exception {
        Path file = tempDir.resolve("broken.csv");
        Files.write(file, new byte[] {(byte) 0xFF, (byte) 0xFE, (byte) 0xFD});

        DataLoadingException exception =
                assertThrows(DataLoadingException.class, () -> loader.load(file));

        assertInstanceOf(IOException.class, exception.getCause());
    }

    @Test
    void resultListsCannotBeModified() throws Exception {
        Path file = writeFile(HEADER, "T-101;Prepare release;backend;8;READY");
        LoadResult result = loader.load(file);

        ProjectTask task = result.getTasks().get(0);

        assertThrows(UnsupportedOperationException.class, () -> result.getTasks().add(task));
        assertThrows(UnsupportedOperationException.class, () -> result.getErrors().clear());
        assertSame(task, result.getTasks().get(0));
    }
}
