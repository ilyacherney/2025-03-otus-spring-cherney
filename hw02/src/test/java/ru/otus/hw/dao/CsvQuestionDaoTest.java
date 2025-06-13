package ru.otus.hw.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import ru.otus.hw.config.AppProperties;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.io.InputStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class CsvQuestionDaoTest {

    private AppProperties props;
    private CsvQuestionDao csvQuestionDao;

    @BeforeEach
    void setUp() {
        this.props = new AppProperties();
        this.csvQuestionDao = new CsvQuestionDao(props);
    }

    @Test
    void shouldParseQuestionsFromExistingCsvFile() {
        props.setTestFileName("test-questions.csv");

        List<Question> result = csvQuestionDao.findAll();

        assertThat(result).isNotEmpty();
        assertThat(result).anyMatch(q -> q.text().contains("life on Mars"));
        assertThat(result).anyMatch(q -> q.text().contains("resources be loaded"));
    }

    @Test
    void shouldThrowExceptionWhenCsvFileDoesNotExist() {
        props.setTestFileName("non-existing.csv");

        QuestionReadException exception = Assertions.assertThrows(QuestionReadException.class, () -> {
            csvQuestionDao.findAll();
        });

        assertThat(exception).hasMessageStartingWith("Could not find questions' file");
    }
}
