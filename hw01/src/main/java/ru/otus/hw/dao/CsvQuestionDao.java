package ru.otus.hw.dao;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.*;
import com.opencsv.exceptions.CsvException;
import lombok.RequiredArgsConstructor;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.dto.QuestionDto;
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
        // todo [DONE] Использовать CsvToBean
        //  https://opencsv.sourceforge.net/#collection_based_bean_fields_one_to_many_mappings

        // todo [DONE] Использовать QuestionReadException

        FileResourcesUtils utils = new FileResourcesUtils();
        CSVReader csvReader;
        try {
            // Готовим ридер
            csvReader = new CSVReaderBuilder(new InputStreamReader(utils.getFileFromResourceAsStream(fileNameProvider.getTestFileName())))
                    .withCSVParser(new CSVParserBuilder()
                            .withSeparator(';')
                            .build())
                    .withSkipLines(1)
                    .build();
        } catch (IllegalArgumentException e) {
            throw new QuestionReadException("Could not find questions' file with name " + fileNameProvider.getTestFileName(), e);
        }

        // Готовим стратегию
        MappingStrategy<QuestionDto> mappingStrategy = new ColumnPositionMappingStrategy<>();
        mappingStrategy.setType(QuestionDto.class);

        // Готовим CvToBean
        CsvToBean<QuestionDto> csvToBean = new CsvToBeanBuilder(csvReader)
                .withType(QuestionDto.class)
                .withMappingStrategy(mappingStrategy)
                .build();

        List<QuestionDto> questionList;


        questionList = csvToBean.parse();


        // todo [DONE]: перевести QuestionDto в Question
        List<Question> questions = new ArrayList<>();
        for (QuestionDto questionDto : questionList) {
            Question question = questionDto.toDomainObject();
            questions.add(question);
        }


        //  todo [DONE]: Про ресурсы: https://mkyong.com/java/java-read-a-file-from-resources-folder/


        // todo [DONE]: убрать хардкод
        return questions;
    }
}
