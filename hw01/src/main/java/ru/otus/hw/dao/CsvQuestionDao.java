package ru.otus.hw.dao;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.*;
import com.opencsv.exceptions.CsvException;
import lombok.RequiredArgsConstructor;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;
import ru.otus.hw.util.FileResourcesUtils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {
    private final TestFileNameProvider fileNameProvider;

    @CsvBindAndSplitByName(elementType = Question.class)
    private List<Question> questions;

    @Override
    public List<Question> findAll() {

        // todo Использовать CsvToBean
        //  https://opencsv.sourceforge.net/#collection_based_bean_fields_one_to_many_mappings
        FileResourcesUtils utils = new FileResourcesUtils();
        CSVReader c = new CSVReaderBuilder(new InputStreamReader(utils.getFileFromResourceAsStream(fileNameProvider.getTestFileName())))
                .withCSVParser(new CSVParserBuilder()
                        .withSeparator(';')
                        .build())
                .withSkipLines(1)
                .build();

        CsvToBean<Question> csvToBean = new CsvToBeanBuilder(c).withType(Question.class).withMappingStrategy(new M)build();

        List<Question> questionList;


        questionList = csvToBean.parse();

        // todo Использовать QuestionReadException
            //  Про ресурсы: https://mkyong.com/java/java-read-a-file-from-resources-folder/

            // todo убрать хардкод

        return List.of(new Question("Как дела?", List.of(new Answer("Супер", true))));
    }
}
