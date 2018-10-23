package com.gman.telegram.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by Anton Mikhaylov on 10.02.2018.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Question {
    private Integer id;
    private String text;
    private String pictureId;
    private List<Answer> answers;
    private Type type;

    public enum Type {
        PHOTO,STICKER,GIF,TEXT
    }
}
