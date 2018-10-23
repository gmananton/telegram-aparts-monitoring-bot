package com.gman.telegram.model.flats;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * Created by Anton Mikhaylov on 03/10/2018.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Floor {

    private List<Flat> flats;
}
