package ru.otus.hw;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.hw.service.TestRunnerService;

public class Application {
    public static void main(String[] args) {
        // todo [DONE] Прописать бины в spring-context.xml и создать контекст на его основе
        // todo [DONE]: Добавить свои вопросы в questions.txt
        // todo [DONE]: Пропустить строку в questions.txt
        // todo: Необходимо написать юнит-тест сервиса тестирования
        // todo: Все зависимости должны быть настроены в IoC контейнере
        // todo: причесать всё
        // todo: Проверка checkstyle должна проходить успешно
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        var testRunnerService = context.getBean(TestRunnerService.class);
        testRunnerService.run();

    }
}