package ru.otus.hw.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppProperties implements TestConfig, TestFileNameProvider {
    private int rightAnswersCountToPass;

    private String testFileName;
}
