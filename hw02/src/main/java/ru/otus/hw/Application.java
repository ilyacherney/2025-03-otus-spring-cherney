package ru.otus.hw;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.otus.hw.service.TestRunnerService;

public class Application {
    public static void main(String[] args) {

        // todo [ ]: Создать контекст на основе Annotation/Java конфигурирования
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.refresh();
        var testRunnerService = context.getBean(TestRunnerService.class);
//        testRunnerService.run();

        //  todo [ ]: добавить вопросы в questions.csv
    }
}