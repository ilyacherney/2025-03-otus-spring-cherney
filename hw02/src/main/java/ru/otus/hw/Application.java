package ru.otus.hw;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.otus.hw.config.AppConfiguration;
import ru.otus.hw.service.TestRunnerService;

public class Application {
    public static void main(String[] args) {
        // todo:
        //  - [x] Контекст описывается с помощью Java + Annotation-based конфигурации
        //  - [x] Необходимо адаптировать/перенести юнит-тест сервиса тестирования,
        //  - [ ] а также написать интеграционный тест дао, читающего вопросы.
        //        Под интеграционностью тут понимается интеграция с файловой системой.
        //        В остальном, это должен быть юнит-тест (без контекста и с моками зависимостей)
        //  - [x] Тесты из заготовки должны проходить

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfiguration.class);

        var testRunnerService = context.getBean(TestRunnerService.class);
        testRunnerService.run();
    }
}