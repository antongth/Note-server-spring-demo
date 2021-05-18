package net.tack.school.notes.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class BaseURL {

    @Value("${server.port}")
    private int port;

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public String HiWorld() {
        return String.format("Hallow world! \n the api is follows below: \n localhost:%s/api/...", port);
    }

    @RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
    public String HiWorld1() {
        return String.format("Hallow world! \n the api is follows below: \n localhost:%s/api/...", port);
    }
}
