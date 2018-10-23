package com.gman.telegram.client;

import com.gman.telegram.model.flats.FlatsData;
import feign.Headers;
import feign.RequestLine;

/**
 * Created by Anton Mikhaylov on 04/10/2018.
 */
public interface ApiClient {

    @Headers({"Content-Type: application/json"})
    @RequestLine("GET /v1/bulk/chessplan?new=1&block_id=158&types=1,2")
    FlatsData getData();

}
