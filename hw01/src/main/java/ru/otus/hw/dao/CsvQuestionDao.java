package ru.otus.hw.dao;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.MappingStrategy;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import lombok.RequiredArgsConstructor;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;
import ru.otus.hw.service.TestDataProvider;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {

    private final TestDataProvider testDataProvider;

    @Override
    public List<Question> findAll() {
        CsvToBean csvToBean = getCsvToBean();
        List<QuestionDto> questionDtoList = csvToBean.parse();
        List<Question> questions = convertFromQuestionDtoList(questionDtoList);
        return questions;
    }

    private CSVReader getCsvReader() {
        CSVReader csvReader;
        try {
            csvReader = new CSVReaderBuilder(new InputStreamReader(testDataProvider.provideTestData()))
                    .withCSVParser(new CSVParserBuilder()
                            .withSeparator(';')
                            .build())
                    .withSkipLines(1)
                    .build();
        } catch (IllegalArgumentException e) {
            throw new QuestionReadException("Could not find questions' file: " , e);
        }
        return csvReader;
    }

    private MappingStrategy<QuestionDto> getMappingStrategy() {
        MappingStrategy<QuestionDto> mappingStrategy = new ColumnPositionMappingStrategy<>();
        mappingStrategy.setType(QuestionDto.class);
        return mappingStrategy;
    }

    private CsvToBean getCsvToBean() {
        CsvToBean<QuestionDto> csvToBean = new CsvToBeanBuilder(getCsvReader())
                .withType(QuestionDto.class)
                .withMappingStrategy(getMappingStrategy())
                .build();
        return csvToBean;
    }

    private List<Question> convertFromQuestionDtoList(List<QuestionDto> questionDtoList) {
        List<Question> questions = new ArrayList<>();
        for (QuestionDto questionDto : questionDtoList) {
            Question question = questionDto.toDomainObject();
            questions.add(question);
        }
        return questions;
    }
}
