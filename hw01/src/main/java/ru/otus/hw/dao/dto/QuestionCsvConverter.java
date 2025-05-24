package ru.otus.hw.dao.dto;

import com.opencsv.CSVReader;
import com.opencsv.bean.AbstractCsvConverter;
import com.opencsv.bean.MappingStrategy;
import com.opencsv.exceptions.*;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QuestionCsvConverter extends AbstractCsvConverter {


    @Override
    public Object convertToRead(String value) {
        AnswerCsvConverter answerCsvConverter = new AnswerCsvConverter();

        var valueArr = value.split(";");

        // todo надо обработать всё, что после ;. полученную строку разбить по | и передать в answerCsvConverter
        var answersPart = valueArr[1];
        String[] answersRaw = answersPart.split("|");
        List<Answer> answers = new ArrayList<>();

        for (int i = 0; i < answersRaw.length; i++) {
            Answer answer = (Answer) answerCsvConverter.convertToRead(answersRaw[i]);
            answers.add(answer);
        }


        return new Question(valueArr[0], answers);
    }
}
