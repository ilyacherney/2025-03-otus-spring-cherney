//package ru.otus.hw.service;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import ru.otus.hw.dao.QuestionDao;
//import ru.otus.hw.domain.Answer;
//import ru.otus.hw.domain.Question;
//import ru.otus.hw.domain.Student;
//import ru.otus.hw.service.IOService;
//import ru.otus.hw.service.TestServiceImpl;
//
//import java.util.List;
//
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.verify;
//
//public class TestServiceImplTest  {
//
//    @Mock
//    QuestionDao questionDao;
//
//    @Mock
//    IOService ioService;
//
//    @InjectMocks
//    TestServiceImpl testServiceImpl;
//
//    @BeforeEach
//    public void initializeMocks() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void shouldPrintReceivedQuestions() {
//        List<Question> questions = List.of(
//                new Question("How many planets are in the Solar System?",
//                List.of(
//                        new Answer("There are 8 planet", true),
//                        new Answer("There are 9 planets", false))));
//        Student student = new Student("Ilya", "Cherney");
//
//        given(questionDao.findAll()).willReturn(questions);
//
//        testServiceImpl.executeTestFor(student);
//
//        verify(ioService).printLine(questions.toString());
//    }
//
//}
