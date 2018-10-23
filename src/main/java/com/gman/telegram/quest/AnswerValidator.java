package com.gman.telegram.quest;

import com.gman.telegram.data.UserTextTemplate;
import com.gman.telegram.model.Answer;
import com.gman.telegram.model.Question;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Anton Mikhaylov on 11.02.2018.
 * Проверяет, является ли пришедший ответ одним из предопределенных
 * (т.е. пришедшим в результате нажатия пользователем на кнопку с вариантом)
 */

@Component
public class AnswerValidator {

    private final List<String> COMMANDS = Arrays.asList(
            UserTextTemplate.COMMAND_START,
            UserTextTemplate.START_QUEST_MSG,
            UserTextTemplate.TRY_AGAIN_MSG,
            UserTextTemplate.CONTINUE_MSG,
            UserTextTemplate.ENTER_CODE
    );

    public boolean isAnswerSupported(String userAnswer, List<Question> questions) {
        return isCommandSupported(userAnswer) ||
                questions.stream()
                        .map(Question::getAnswers)
                        .flatMap(List::stream)
                        .map(Answer::getText)
                        .anyMatch(userAnswer::equals);
    }

    public boolean isCommandSupported(String userCmd) {
        return COMMANDS.contains(userCmd);
    }
}
