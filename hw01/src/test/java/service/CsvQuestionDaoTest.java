package service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.util.Assert;
import ru.otus.hw.dao.CsvQuestionDao;
import ru.otus.hw.domain.Question;
import ru.otus.hw.service.TestDataProvider;

import java.io.InputStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class CsvQuestionDaoTest {

    @Mock
    TestDataProvider testDataProvider;

    @InjectMocks
    CsvQuestionDao csvQuestionDao;

    @BeforeEach
    public void initializeMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldFindQuestionsFromExistingSource() {
        String testFileName = "test-questions.csv";
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(testFileName);

        given(testDataProvider.provideTestData()).willReturn(inputStream);

        List<Question> questions = csvQuestionDao.findAll();

        Assert.notEmpty(questions, "There should be at least one question");
    }
}
