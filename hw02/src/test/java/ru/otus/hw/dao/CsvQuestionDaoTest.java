package ru.otus.hw.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.hw.config.AppProperties;
import ru.otus.hw.dao.CsvQuestionDao;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;
import ru.otus.hw.service.TestDataProvider;

import java.io.InputStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class CsvQuestionDaoTest {

    private CsvQuestionDao csvQuestionDao;

    @BeforeEach
    void setUp() {
//        AppProperties props = n
//        csvQuestionDao = new CsvQuestionDao();
    }

    @Test
    void shouldParseQuestionsFromExistingCsvFile() {
        InputStream csvStream = getClass().getClassLoader().getResourceAsStream("test-questions.csv");
        assert csvStream != null : "CSV file not found in test resources";

        when(csvQuestionDao.provideTestData()).thenReturn(csvStream);

        List<Question> result = csvQuestionDao.findAll();

        assertThat(result).isNotEmpty();
        assertThat(result).anyMatch(q -> q.text().contains("life on Mars"));
        assertThat(result).anyMatch(q -> q.text().contains("resources be loaded"));
    }

    @Test
    void shouldThrowExceptionWhenCsvFileDoesNotExist() {
        QuestionReadException exception = Assertions.assertThrows(QuestionReadException.class, () -> {
            when(testDataProvider.provideTestData()).thenThrow(IllegalArgumentException.class);
            csvQuestionDao.findAll();
        });
        assertThat(exception).hasMessageStartingWith("Could not find questions' file");
    }
}
