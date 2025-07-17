package ru.otus.hw.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TestServiceImplTest  {

    @Mock
    QuestionDao questionDao;

    @Mock
    LocalizedIOService localizedIOService;

    @Mock
    IOService ioService;

    @InjectMocks
    TestServiceImpl testServiceImpl;

    @Test
    public void shouldPrintReceivedQuestions() {

        List<Question> questions = List.of(
                new Question("How many planets are in the Solar System?",
                List.of(
                        new Answer("There are 8 planet", true),
                        new Answer("There are 9 planets", false))));
        Student student = new Student("Ilya", "Cherney");

        given(questionDao.findAll()).willReturn(questions);
        given(localizedIOService.getMessage("TestService.no.such.answer")).willReturn("Error message");
        given(localizedIOService.readIntForRange(1, 2, "Error message")).willReturn(2);

        testServiceImpl.executeTestFor(student);

        verify(localizedIOService).printLine("1. There are 8 planet");
        verify(localizedIOService).printLine("2. There are 9 planets");
    }
}
