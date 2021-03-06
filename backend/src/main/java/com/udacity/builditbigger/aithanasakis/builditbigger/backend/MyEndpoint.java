package com.udacity.builditbigger.aithanasakis.builditbigger.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.udacity.builditbigger.aithanasakis.jokesmith.JokeSmith;

import javax.inject.Named;

/** An endpoint class we are exposing */
@Api(
        name = "myApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.builditbigger.aithanasakis.builditbigger.udacity.com",
                ownerName = "backend.builditbigger.aithanasakis.builditbigger.udacity.com",
                packagePath = ""
        )
)
public class MyEndpoint {

    @ApiMethod(name = "tellJoke")
    public MyBean tellJoke() {
        MyBean response = new MyBean();
        JokeSmith jokeSmith = new JokeSmith();
        response.setData(jokeSmith.getJoke());

        return response;
    }

}
