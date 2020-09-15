package com.example.androidwebtest;

import android.util.Log;

import java.util.Map;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.env.Environment;
import io.micronaut.context.env.PropertySource;
import io.micronaut.core.util.CollectionUtils;
import io.micronaut.runtime.Micronaut;

public class ServerController {
    private static final String TAG = MainActivity.class.getSimpleName();
    private ApplicationContext mServerContext;

    private PropertySource getPropertySource() {
        Map<String, Object> configMap = CollectionUtils.mapOf(
                "micronaut.server.host", "10.96.26.223",
                "micronaut.server.port", 8080);

        return PropertySource.of(Environment.ANDROID, configMap);
    }

    public void startWebServer() {
        Log.d(TAG, "startWebServer");
        if (null != mServerContext && mServerContext.isRunning()) {
            Log.d(TAG, "startWebServer already running");
            return;
        }

        mServerContext = Micronaut.build(Environment.ANDROID)
                .mainClass(this.getClass())
                .propertySources(getPropertySource())
                .start();

        Log.v(TAG, "host:" + mServerContext.getProperty("micronaut.server.host", String.class));
        Log.v(TAG, "port:" + mServerContext.getProperty("micronaut.server.port", String.class));
    }

    public void stopWebServer() {
        Log.d(TAG, "stopWebServer");
        if (null != mServerContext && mServerContext.isRunning()) {
            mServerContext.stop();
            Log.d(TAG, "stopWebServer end");
        }
    }
}