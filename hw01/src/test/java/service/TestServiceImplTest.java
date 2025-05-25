package service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.service.TestService;
import org.mockito.*;
import ru.otus.hw.service.TestServiceImpl;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class TestServiceImplTest  {

    @Mock
    QuestionDao questionDao;

    @InjectMocks
    TestServiceImpl testServiceImpl;

    @Test
    public void shouldPrintReceivedQuestions() {
        List<Question> questions = List.of(
                new Question("How many planets are in the Solar System?",
                List.of(
                        new Answer("There are 8 planet", true),
                        new Answer("There are 9 planets", false))));

        given(questionDao.findAll()).willReturn(questions);

        testServiceImpl.executeTest();

//        verify(testServiceImpl).
    }

}
