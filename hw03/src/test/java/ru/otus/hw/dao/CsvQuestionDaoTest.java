package ru.otus.hw.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CsvQuestionDaoTest {

    @InjectMocks
    private CsvQuestionDao csvQuestionDao;

    @Mock
    private TestFileNameProvider testFileNameProvider;

    @Test
    void shouldParseQuestionsFromExistingCsvFile() {
        when(testFileNameProvider.getTestFileName()).thenReturn("test-questions.csv");

        List<Question> result = csvQuestionDao.findAll();

        assertThat(result).isNotEmpty();
        assertThat(result).anyMatch(q -> q.text().contains("life on Mars"));
        assertThat(result).anyMatch(q -> q.text().contains("resources be loaded"));
    }

    @Test
    void shouldThrowExceptionWhenCsvFileDoesNotExist() {
        when(testFileNameProvider.getTestFileName()).thenReturn("non-existing-file-name");

        QuestionReadException exception = Assertions.assertThrows(QuestionReadException.class, () -> {
            csvQuestionDao.findAll();
        });

        assertThat(exception).hasMessageStartingWith("Could not find questions' file");
    }
}
