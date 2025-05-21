package ru.otus.hw.dao;

import com.opencsv.bean.CsvToBean;
import lombok.RequiredArgsConstructor;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {
    private final TestFileNameProvider fileNameProvider;

    @Override
    public List<Question> findAll() {
        // todo Использовать CsvToBean
        //  https://opencsv.sourceforge.net/#collection_based_bean_fields_one_to_many_mappings

        // todo Использовать QuestionReadException
        //  Про ресурсы: https://mkyong.com/java/java-read-a-file-from-resources-folder/

        // todo убрать хардкод
        return List.of(new Question("Как дела?", List.of(new Answer("Супер", true))));
    }
}
