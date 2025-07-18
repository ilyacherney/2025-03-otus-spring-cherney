package ru.otus.hw;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.otus.hw.config.AppConfiguration;
import ru.otus.hw.service.TestRunnerService;

public class Application {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfiguration.class);

        var testRunnerService = context.getBean(TestRunnerService.class);
        testRunnerService.run();
    }
}