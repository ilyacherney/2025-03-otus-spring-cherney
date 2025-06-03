package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.otus.hw.dao.CsvQuestionDao;
import ru.otus.hw.service.TestDataProvider;

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

    }
}
