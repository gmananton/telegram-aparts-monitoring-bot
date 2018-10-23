package com.gman.telegram.keyboard;

import com.gman.telegram.data.UserTextTemplate;
import com.gman.telegram.model.Question;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anton Mikhaylov on 10.02.2018.
 */

@Component
public class KBBuilder {

    public ReplyKeyboardMarkup keyboard(Question question) {

        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        List<KeyboardRow> rows = new ArrayList<>(4);

        question.getAnswers().forEach(
                answer -> {
                    KeyboardRow row = new KeyboardRow();
                    row.add(answer.getText());
                    rows.add(row);
                });

        markup.setKeyboard(rows);
        return markup;
    }

    public ReplyKeyboardMarkup startKB() {
        return SingleRowKB(UserTextTemplate.START_QUEST_MSG);
    }

    public ReplyKeyboardMarkup tryAgainKB() {
        return SingleRowKB(UserTextTemplate.TRY_AGAIN_MSG);
    }

    public ReplyKeyboardMarkup continueKB() {
        return SingleRowKB(UserTextTemplate.CONTINUE_MSG);
    }

    private ReplyKeyboardMarkup SingleRowKB(String buttonText) {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        markup.setResizeKeyboard(true);
        markup.setOneTimeKeyboard(true);
        List<KeyboardRow> rows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add(buttonText);
        rows.add(row);
        markup.setKeyboard(rows);
        return markup;
    }
}
