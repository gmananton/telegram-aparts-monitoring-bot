package com.gman.telegram.data;

import static com.gman.telegram.data.Emojis.*;

/**
 * Created by Anton Mikhaylov on 10.02.2018.
 * https://apps.timwhitlock.info/emoji/tables/unicode
 */
public class BotTextTemplate {

    public static final String HELLO_MSG =
            "Привет! Я поздравленческий кот!" + SMILING_CAT +
            "\nДавай сыграем в небольшую игру. За каждый верный ответ ты получишь подсказку, " +
            "которая приблизит тебя к победе." +
            "\nА в конце тебя ожидает сюрприз!" + GRINNING_CAT + PRESENT;

    public static final String UNKNOWN_MSG = "Извини, я не могу разобрать твой ответ... " + CONFUSED_FACE + "\nПопробуй еще раз или нажми /start, чтобы начать заново";
    public static final String TRY_AGAIN = "Упс, что-то не то..." + GRIMACING_FACE + "\nПопробуй еще раз";
    public static final String CORRECT_ANSWER = "Отлично!" + SMILING_CAT;

    public static final String HAPPY_BIRTHDAY = "С Днем Рождения, Котище!" + PRESENT + CAKE + PARTY_POPPER +
            "\nПусть этот подарок поможет тебе как в работе, так и в творчестве!" + COMPUTER +
            "\nЯ тебя очень сильно люблю!" + CAT_KISSING + CAT_HEARTS;

}
