package com.gman.telegram.bot;

import com.gman.telegram.keyboard.KBBuilder;
import com.gman.telegram.model.Question;
import com.gman.telegram.model.Reaction;
import com.gman.telegram.quest.AnswerRegistry;
import com.gman.telegram.quest.AnswerValidator;
import com.gman.telegram.quest.QuestionProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

import static com.gman.telegram.data.BotTextTemplate.HAPPY_BIRTHDAY;
import static com.gman.telegram.data.BotTextTemplate.HELLO_MSG;
import static com.gman.telegram.data.BotTextTemplate.UNKNOWN_MSG;
import static com.gman.telegram.data.Gifs.GIF_CORGI;
import static com.gman.telegram.data.Gifs.GIF_GIRL_CAKE;
import static com.gman.telegram.data.Gifs.GIF_SAMOYED;
import static com.gman.telegram.data.Gifs.GIF_SHEEP;
import static com.gman.telegram.data.Pictures.PUSHEEN_CAKE;
import static com.gman.telegram.data.Stickers.STICKER_CAT_UNICORN;
import static com.gman.telegram.data.UserTextTemplate.COMMAND_START;
import static com.gman.telegram.data.UserTextTemplate.CONTINUE_MSG;
import static com.gman.telegram.data.UserTextTemplate.START_QUEST_MSG;
import static com.gman.telegram.data.UserTextTemplate.TRY_AGAIN_MSG;

/**
 * Created by Anton Mikhaylov on 09.02.2018.
 */

@Slf4j
@Component
public class CongratsBot extends TelegramLongPollingBot {

    @Value("${bot.username}")
    private String BOT_NAME;

    @Value("${bot.token}")
    private String TOKEN;

//    @Value("${chat.id.anuta}")
    @Value("${chat.id.anton}")
    private String CHAT_ID;

    @Autowired
    private QuestionProvider provider;

    @Autowired
    private AnswerRegistry answerRegistry;

    @Autowired
    private KBBuilder keyboardBuilder;

    @Autowired
    private AnswerValidator validator;


    @PostConstruct
    public void startMessaging() throws Exception {
        log.info("Bot {} initialized. Token: {}", BOT_NAME, TOKEN);
//        execute(getStartMessage());
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            process(update);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void process(Update update) throws TelegramApiException {

        log.info("Got message: {} from chat_id={}", update.getMessage(), CHAT_ID);

        List<Question> questions = provider.getQuestions();

        Message msg = update.getMessage();
        String text = msg.getText();

        if (!validator.isAnswerSupported(text, questions)) {
            log.info("User sent unknown command");
            execute(textMessage(UNKNOWN_MSG, keyboardBuilder.startKB()));
            return;
        }

        if (COMMAND_START.equalsIgnoreCase(text)) {
            log.info("User sent /start command");
            execute(getStartMessage());
            provider.init();
            return;
        }

        if (START_QUEST_MSG.equals(text)) {
            log.info("User starts over");
            provider.init();
            execute(photoQuestion(provider.getNonAnsweredQuestion().get()));
            return;
        }

        if (TRY_AGAIN_MSG.equals(text) || CONTINUE_MSG.equals(text)) {
            if (provider.getNonAnsweredQuestion().isPresent()) {
                Question question = provider.getNonAnsweredQuestion().get();
                sendQuestion(question);
            } else {
                sendFinalMessage();
            }

            return;
        }

        if (validator.isAnswerSupported(text, questions)) {
            reactToAnswer(text);
            return;
        }

    }



    private void sendQuestion(Question question) throws TelegramApiException {

        log.info("Sending question id={} to chat_id={}", question.getId(), CHAT_ID);

        Question.Type type = question.getType();
        String media = question.getPictureId();
        String text = question.getText();

        switch (type) {
            case GIF:
                execute(gifMessage(media, text, keyboardBuilder.keyboard(question)));
                break;
            case PHOTO:
                execute(photoMessage(media, text, keyboardBuilder.keyboard(question)));
                break;
            case STICKER:
                execute(stickerMessage(media));
                break;
            default:
                execute(textMessage(text, null));
        }
    }

    public void sendFinalMessage() throws TelegramApiException {
        execute(gifMessage(GIF_SAMOYED, null, null));
        execute(gifMessage(GIF_SHEEP, null, null));
        execute(gifMessage(GIF_CORGI, null, null));
        execute(stickerMessage(STICKER_CAT_UNICORN));
        execute(textMessage(HAPPY_BIRTHDAY, null));
        execute(gifMessage(GIF_GIRL_CAKE, null, null));
    }

    private void reactToAnswer(String text) throws TelegramApiException {

        Map<Integer, Boolean> questState = provider.getQuestState();

        //найти первый вопрос, на который еще не ответили
        int nonAnsweredId = provider.findFirstNonAnsweredId();

        Reaction reaction = answerRegistry.findAnswerByText(text).getReaction();
        execute(stickerMessage(reaction.getSticker()));

        if (provider.answerIsCorrect(text)) {
            questState.put(nonAnsweredId, true);
            execute(textMessage(reaction.getText(), keyboardBuilder.continueKB()));
        } else {
            execute(textMessage(reaction.getText(), keyboardBuilder.tryAgainKB()));
        }
    }


    private SendMessage textMessage(String text, ReplyKeyboardMarkup keyboard) {
        SendMessage msg = new SendMessage();
        msg.setChatId(CHAT_ID);
        msg.setParseMode("HTML");
        msg.setReplyMarkup(keyboard);
        msg.setText(text);
        return msg;
    }

    private SendPhoto photoQuestion(Question question) {
        return photoMessage(question.getPictureId(), question.getText(), keyboardBuilder.keyboard(question));
    }

    private SendPhoto photoMessage(String pictureID, String text, ReplyKeyboardMarkup keyboard) {
        SendPhoto msg = new SendPhoto();
        msg.setChatId(CHAT_ID);
        msg.setPhoto(pictureID);
        msg.setCaption(text);
        msg.setReplyMarkup(keyboard);
        return msg;
    }

    private SendSticker stickerMessage(String pictureId) {
        SendSticker msg = new SendSticker();
        msg.setChatId(CHAT_ID);
        msg.setSticker(pictureId);
        return msg;
    }

    private SendDocument gifMessage(String pictureId, String caption, ReplyKeyboardMarkup keyboard) {
        SendDocument msg = new SendDocument();
        msg.setChatId(CHAT_ID);
        msg.setDocument(pictureId);
        msg.setReplyMarkup(keyboard);
        msg.setCaption(caption);
        return msg;
    }

    private SendPhoto getStartMessage() {
        return photoMessage(PUSHEEN_CAKE, HELLO_MSG, keyboardBuilder.startKB());
    }

    @Override
    public String getBotToken() {
        return TOKEN;
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

}
