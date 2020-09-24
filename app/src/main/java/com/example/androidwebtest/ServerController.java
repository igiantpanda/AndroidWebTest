package com.example.androidwebtest;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import java.util.Map;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.env.Environment;
import io.micronaut.context.env.PropertySource;
import io.micronaut.core.util.CollectionUtils;
import io.micronaut.runtime.Micronaut;

public class ServerController extends HandlerThread {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Context mContext;
    private Handler mHandler;
    private ApplicationContext mServerContext;

    private Runnable mRunStart = new Runnable() {
        @Override
        public void run() {
            try {
                startWebServerInternal();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    };

    private Runnable mRunStop = new Runnable() {
        @Override
        public void run() {
            try {
                stopWebServerInternal();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    };

    public ServerController(Context context) {
        super(TAG);
        start();
        mHandler = new Handler(getLooper());
        mContext = context;
    }

    private PropertySource getPropertySource() {
        Map<String, Object> configMap = CollectionUtils.mapOf(
                "micronaut.server.host", NetUtils.getWifiIp(mContext),
//                "micronaut.server.host", "0.0.0.0",
                "micronaut.server.port", 8080);

        return PropertySource.of(Environment.ANDROID, configMap);
    }

    public void startWebServer() {
        mHandler.post(mRunStart);
    }

    public void stopWebServer() {
        mHandler.post(mRunStop);
    }

    public void startWebServerInternal() throws Throwable {
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

    public void stopWebServerInternal() throws Throwable {
        Log.d(TAG, "stopWebServer");
        if (null != mServerContext && mServerContext.isRunning()) {
            mServerContext.stop();
            mServerContext = null;
            Log.d(TAG, "stopWebServer end");
        }
    }
}
