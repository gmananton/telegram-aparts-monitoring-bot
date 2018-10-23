package com.gman.telegram.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;

import javax.annotation.PostConstruct;

/**
 * Created by Anton Mikhaylov on 09.02.2018.
 */

@Slf4j
@Component
public class Initializer {

    @Autowired
    private LongPollingBot bot;

    @PostConstruct
    public void init() {

        TelegramBotsApi api  = new TelegramBotsApi();
        log.info("API initialized");

//        try {
//            api.registerBot(bot);
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }

        log.info("Bot registered: {}", bot.getBotUsername());
    }
}
