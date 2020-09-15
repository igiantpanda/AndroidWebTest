package com.example.androidwebtest;

import android.util.Log;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;

@Controller
public class HelloController {
    private static final String TAG = HelloController.class.getSimpleName();

    @Get(uri = "/hello/{name}")
    public String hello(String name) {
        Log.d(TAG, "hello:" + name);
        return "Hello " + name + "!";
    }


    @Post(uri = "/heartbeat")
    public String heartbeat() {
        Log.d(TAG, "heartbeat");
        return "heartbeat";
    }
}
