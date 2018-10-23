package com.gman.telegram.data;

import static com.gman.telegram.data.Emojis.CAT_WRY_SMILE;
import static com.gman.telegram.data.Emojis.WINKING_FACE;

/**
 * Created by Anton Mikhaylov on 11.02.2018.
 */
public class UserTextTemplate {
    public static final String COMMAND_START = "/start";
    public static final String START_QUEST_MSG = "Начать!" + WINKING_FACE;
    public static final String TRY_AGAIN_MSG = "Повторить";
    public static final String CONTINUE_MSG = "Продолжить!" + CAT_WRY_SMILE;
    public static final String ENTER_CODE = "Ввести код" + CAT_WRY_SMILE;
}
