package com.example.androidwebtest;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

public class NetUtils {
    private static final String TAG = NetUtils.class.getSimpleName();

    private static String int2Ip(int ip) {
        return (ip & 0xFF) + "." + ((ip >> 8) & 0xFF) + "." + ((ip >> 16) & 0xFF) + "." + ((ip >> 24) & 0xFF);
    }

    public static String getWifiIp(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (null == wifiManager || !wifiManager.isWifiEnabled()) {
            Log.w(TAG, "getWifiIp:WiFi disabled");
            return null;
        }

        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();
        return (0 == ip) ? null : int2Ip(ip);
    }
}
