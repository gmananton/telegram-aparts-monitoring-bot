package com.gman.telegram.controller;

import com.gman.telegram.client.ApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.gman.telegram.model.flats.FlatsData;

/**
 * Created by Anton Mikhaylov on 04/10/2018.
 */
@RestController
@RequestMapping
public class TestController {

    @Autowired
    private ApiClient client;


    @GetMapping("test")
    public FlatsData getData() {
        return client.getData();
    }
}
