package com.gman.telegram.model.flats;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Created by Anton Mikhaylov on 03/10/2018.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Flat {

    private int id;
    private String status;
    private long price;
    private String area;
    private String rooms;
}
