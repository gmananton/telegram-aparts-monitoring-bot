package com.gman.telegram.model.flats;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

/**
 * Created by Anton Mikhaylov on 03/10/2018.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Section {
    private String id;
    private Map<String, Floor> floors = new HashMap<>();

    public List<Flat> getFlatsByFloor(String floor) {

        return floors.get(floor).getFlats();
    }

    public Flat getFlatById(int id) {

        List<Flat> flats = floors.values()
                .stream()
                .flatMap(floor -> floor.getFlats().stream())
                .collect(toList());

        return flats
                .stream()
                .filter(f -> f.getId() == id)
                .findFirst().get();
    }
}
