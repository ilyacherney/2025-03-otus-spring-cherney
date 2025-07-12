package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final LocalizedIOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        printStartingMessage();

        var questions = questionDao.findAll();
        var testResult = new TestResult(student);

        for (var question: questions) {
            var isAnswerValid = false;

            printQuestionWithAnswers(question);

            int chosenAnswer = ioService.readIntForRange(1, question.answers().size(),
                    ioService.getMessage("TestService.no.such.answer"));

            if (chosenAnswer == 0) {
                isAnswerValid = false;
            } else {
                isAnswerValid = question.answers().get(chosenAnswer - 1).isCorrect();
            }
            testResult.applyAnswer(question, isAnswerValid);
        }
        return testResult;
    }

    private void printStartingMessage() {
        ioService.printLine("");
        ioService.printLineLocalized("TestService.answer.the.questions");
        ioService.printLine("");
    }

    private void printQuestionWithAnswers(Question question) {
        List<Answer> answers = question.answers();

        ioService.printLine(question.text());
        for (int i = 0; i < answers.size(); i++) {
            ioService.printLine((i + 1) + ". " + answers.get(i).text());
        }
    }

}
