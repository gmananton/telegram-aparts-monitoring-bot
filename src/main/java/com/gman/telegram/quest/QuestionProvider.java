package com.gman.telegram.quest;

import com.gman.telegram.data.Pictures;
import com.gman.telegram.model.Answer;
import com.gman.telegram.model.Question;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.gman.telegram.data.Gifs.GIF_ENGINE;
import static com.gman.telegram.model.Question.Type.GIF;
import static com.gman.telegram.model.Question.Type.PHOTO;
import static com.gman.telegram.model.Question.Type.TEXT;

/**
 * Created by Anton Mikhaylov on 10.02.2018.
 */
@Slf4j
@Component
public class QuestionProvider {

    @Autowired
    private AnswerRegistry answerRegistry;

    /**
     * связка id вопроса и состояния "решен/не решен"
     * по команде /start происходит сброс состояния
     * */
    @Getter
    private Map<Integer, Boolean> questState = new LinkedHashMap<>();

    @Getter
    private List<Question> questions;

    @PostConstruct
    public void init() {
        questions = createAll();
        for (Question q : questions) {
            // Collectors.toMap - почему-то не работет
            questState.put(q.getId(), false);
        }
        log.info("QuestionProvider initialized");
    }

    public List<Question> createAll() {
        return Arrays.asList(q1(), q2(), q3(), q4(), q5(), q6(), q7(), q8());
    }


    public boolean answerIsCorrect(String answer) {
        return questions.stream()
                .map(Question::getAnswers)
                .flatMap(List::stream)
                .filter(Answer::isCorrect)
                .map(Answer::getText)
                .collect(Collectors.toList())
                .contains(answer);
    }

    // Находит первый id вопроса, на который еще не ответили
    public int findFirstNonAnsweredId() {
        Optional<Map.Entry<Integer, Boolean>> opt = questState.entrySet().stream()
                .filter(entry -> entry.getValue().equals(false))
                .findFirst();

        return opt.isPresent() ? opt.get().getKey() : -1;
    }


    public Optional<Question> getNonAnsweredQuestion() {
        return questions.stream()
                .filter(question -> question.getId().equals(findFirstNonAnsweredId()))
                .findFirst();
    }

    public Question q1() {
        return Question.builder()
                .id(1)
                .type(PHOTO)
                .text("Что тут изображено?")
                .pictureId(Pictures.CORGI)
                .answers(answerRegistry.get(1))
                .build();
    }

    public Question q2() {
        return Question.builder()
                .id(2)
                .type(TEXT)
                .text("Вот первая загадка:\n" +
                        "<i>Она</i> защищает тебя от солнца и ветра во время катания.\n" +
                        "Попробуй найти <i>её</i> и внутри ты обнаружишь первую подсказку с кодом, " +
                        "который надо будет ввести в поле ответа.")
                .answers(answerRegistry.get(2))
                .build();
    }

    public Question q3() {
        return Question.builder()
                .id(3)
                .type(PHOTO)
                .text("Что это за вершина?")
                .pictureId(Pictures.EVEREST)
                .answers(answerRegistry.get(3))
                .build();
    }

    public Question q4() {
        return Question.builder()
                .id(4)
                .type(TEXT)
                .text("Отлично! Я вижу, ты отгадала загадку. А вот следующая подсказка:\n" +
                        "Благодаря <i>им</i> мы познакомились")
                .answers(answerRegistry.get(4))
                .build();
    }


    public Question q5() {
        return Question.builder()
                .id(5)
                .type(GIF)
                .text("Какой это тип двигателя?")
                .pictureId(GIF_ENGINE)
                .answers(answerRegistry.get(5))
                .build();
    }

    public Question q6() {
        return Question.builder()
                .id(6)
                .type(TEXT)
                .text("<i>Она</i> периодически дребезжит, но без <i>неё</i> не приготовить вкуснейший стейк")
                .answers(answerRegistry.get(6))
                .build();
    }


    public Question q7() {
        return Question.builder()
                .id(7)
                .type(PHOTO)
                .text("Что тут произошло?")
                .pictureId(Pictures.SNOWBOARD_FAIL)
                .answers(answerRegistry.get(7))
                .build();
    }

    public Question q8() {
        return Question.builder()
                .id(8)
                .type(TEXT)
                .text("Вот финальная загадка.\n" +
                        "Это твой постоянный спутник во время путешествий и катания")
                .answers(answerRegistry.get(8))
                .build();
    }
}
