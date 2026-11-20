package edu.course.lab05;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;


class FilePreparationTest {

    @TempDir
    Path tempDir;

    private Path createNumbersFile() throws IOException {
        Path file = tempDir.resolve("numbers.txt");
        Files.write(file, List.of("10", "bad", "20"), StandardCharsets.UTF_8);
        return file;
    }

    @Test
    void readsAllThreeLines() throws IOException {
        Path file = createNumbersFile();

        List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);

        assertEquals(List.of("10", "bad", "20"), lines);
    }

    @Test
    void badLineCausesNumberFormatException() throws IOException {
        Path file = createNumbersFile();
        List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);

        assertThrows(NumberFormatException.class, () -> {
            List<Integer> numbers = new ArrayList<>();
            for (String line : lines) {
                numbers.add(Integer.parseInt(line));
            }
        });
    }

    @Test
    void goodLinesAreParsedBeforeBadOne() throws IOException {
        Path file = createNumbersFile();
        List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);
        List<Integer> numbers = new ArrayList<>();

        try {
            for (String line : lines) {
                numbers.add(Integer.parseInt(line));
            }
        } catch (NumberFormatException e) {
            // ожидаемо: на строке "bad" цикл остановился
        }

        assertEquals(List.of(10), numbers);
    }
}
