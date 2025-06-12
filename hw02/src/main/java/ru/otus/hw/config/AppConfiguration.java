package ru.otus.hw.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:application.properties")
@Configuration
@ComponentScan("ru.otus.hw")
public class AppConfiguration {

    @Bean
    public AppProperties appProperties(@Value("${test.rightAnswersCountToPass}") int rightAnswersCountToPass,
                                       @Value("${test.fileName}") String testFileName) {
        AppProperties props = new AppProperties();
        props.setRightAnswersCountToPass(rightAnswersCountToPass);
        props.setTestFileName(testFileName);
        return props;
    }
}
