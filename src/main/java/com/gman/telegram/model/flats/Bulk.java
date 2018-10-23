package com.gman.telegram.model.flats;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anton Mikhaylov on 03/10/2018.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Bulk {

    private String id;
    private List<Section> sections = new ArrayList<>();

    public Section getSectionById(String id) {

        return sections.stream().filter(s -> s.getId().equals(id)).findFirst().get();
    }
}
