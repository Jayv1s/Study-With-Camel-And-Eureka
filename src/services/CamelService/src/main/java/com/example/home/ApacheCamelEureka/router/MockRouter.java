package com.example.home.ApacheCamelEureka.router;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class MockRouter extends RouteBuilder {

    public static final String URL_DISCOVER_BOOKS = "%s/livros?bridgeEndpoint=true";

    @Autowired
    private DiscoveryClient discoveryClient;

    @Override
    public void configure() throws Exception {
        restConfiguration().component("servlet").bindingMode(RestBindingMode.json);

//        rest("/serviceCall/games").get().produces(MediaType.APPLICATION_JSON_VALUE).route()
//                .serviceCall("games-service/jogos?bridgeEndpoint=true")
//                //Is it becouse http endpoint produces a Stream as the body and once the stream is read, it is no longer available.
//                .convertBodyTo(String.class)
//                .log("Body: ${body}").unmarshal().json();

        rest("/serviceCall/livros").get().produces(MediaType.APPLICATION_JSON_VALUE).route()
                .serviceCall("books-service/livros?bridgeEndpoint=true")
                //Is it becouse http endpoint produces a Stream as the body and once the stream is read, it is no longer available.
                .convertBodyTo(String.class)
                .log("Body: ${body}").unmarshal().json();

        rest("/discoveryCall").get().produces(MediaType.APPLICATION_JSON_VALUE).route()
                .to(String.format(URL_DISCOVER_BOOKS, discoveryClient.getInstances("books-service").get(0).getUri()))
                //Is it becouse http endpoint produces a Stream as the body and once the stream is read, it is no longer available.
                .convertBodyTo(String.class)
                .log("Body: ${body}").unmarshal().json();
    }

}
