package com.gman.telegram.quest;

import com.gman.telegram.model.Answer;
import com.gman.telegram.model.Reaction;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.gman.telegram.data.BotTextTemplate.TRY_AGAIN;
import static com.gman.telegram.data.Emojis.CAT_KISSING;
import static com.gman.telegram.data.Stickers.*;

/**
 * Created by Anton Mikhaylov on 11.02.2018.
 */

@Component
public class AnswerRegistry {

    // Связка номера вопроса и ответов
    private final Map<Integer, List<Answer>> registry = new HashMap<>();

    public List<Answer> get(int questNumber) {
        return Collections.unmodifiableList(registry.get(questNumber));
    }

    public Answer findAnswerByText(String text) {
        return registry.values().stream()
                .flatMap(List::stream)
                .filter(answer -> answer.getText().equalsIgnoreCase(text))
                .findFirst()
                .get();
    }

    @PostConstruct
    public void init() {

        registry.put(1, Arrays.asList(
                new Answer("Корги", false, incorrectReaction()),
                new Answer("Боченя", true, new Reaction(STICKER_HUGS, "Бочеееня!") ),
                new Answer("Пиченя", false, incorrectReaction()),
                new Answer("Тирства", false, incorrectReaction())));

        registry.put(2, Arrays.asList(
                new Answer("12345", true, new Reaction(STICKER_CAT_SHERLOCK, "Да ты прям Шерлок!"))));

        registry.put(3, Arrays.asList(
                new Answer("Эверест", true, new Reaction(STICKER_CAT_BOOK, "Он самый!")),
                new Answer("Монблан", false, incorrectReaction()),
                new Answer("Маттерхорн", false, incorrectReaction()),
                new Answer("Сорочаны", false, incorrectReaction())));

        registry.put(4, Arrays.asList(
                new Answer("777", true, new Reaction(STICKER_CAT_ROLLER, "Вжух! И это верный ответ!\n" +
                        "Ага, значит, ты обнаружила первый сюрприз :) Но пока не открывай его - это еще не всё."))));

        registry.put(5, Arrays.asList(
                new Answer("Рядная 4-ка", false, incorrectReaction()),
                new Answer("Оппозитный", false, incorrectReaction()),
                new Answer("Роторный", false, incorrectReaction()),
                new Answer("V6", true, new Reaction(STICKER_CAT_PIANO, "Логический кот!"))));

        registry.put(6, Arrays.asList(
                new Answer("Alpindustria", true, new Reaction(STICKER_MOOSE,
                        "Привет от мамочки с папочкой" + CAT_KISSING + "\nВозьми себе клёвый эквип =)"))));

        registry.put(7, Arrays.asList(
                new Answer("Немножко кото-кот, чо =)", false, incorrectReaction()),
                new Answer("Смотри как умею!", false, incorrectReaction()),
                new Answer("Так и было задумано", true, new Reaction(STICKER_BEAR_NINJA, "Канеш! Всё четенько!")),
                new Answer("Это паудер, детка!", false, incorrectReaction())));

        registry.put(8, Arrays.asList(
                new Answer("130", true, new Reaction(STICKER_CAT_BACKPACK, "ТА-ДАМ! Молодец, возьми с полки пирожок =)"))));
    }


    private Reaction incorrectReaction() {
        return new Reaction(STICKER_CAT_LAPTOP, TRY_AGAIN);
    }


}
