package com.gman.telegram.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Anton Mikhaylov on 11.02.2018.
 * реакция на ответ: корректный, либо нет. Включает стикер и текст
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reaction {
    private String sticker;
    private String text;
}
