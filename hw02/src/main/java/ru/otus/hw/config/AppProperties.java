package ru.otus.hw.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class AppProperties implements TestConfig, TestFileNameProvider {

    // todo [ ]: внедрить свойство из application.properties
    private int rightAnswersCountToPass;

    // todo [ ]: внедрить свойство из application.properties
    private String testFileName;
}
