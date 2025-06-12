package ru.otus.hw.config;


import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class AppProperties implements TestConfig, TestFileNameProvider {
    private int rightAnswersCountToPass;
    private String testFileName;
}
