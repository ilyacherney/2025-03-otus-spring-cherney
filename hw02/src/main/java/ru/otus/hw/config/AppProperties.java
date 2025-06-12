package ru.otus.hw.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@PropertySource("classpath:application.properties")
@Component
public class AppProperties implements TestConfig, TestFileNameProvider {

    // todo [ ]: внедрить свойство из application.properties
    @Value("${test.rightAnswersCountToPass}")
    private int rightAnswersCountToPass;

    // todo [ ]: внедрить свойство из application.properties
    @Value("${test.fileName}")
    private String testFileName;
}
