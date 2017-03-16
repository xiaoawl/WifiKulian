package com.xiaohong.wifikulian.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

import com.xiaohong.wifikulian.Constants;
import com.xiaohong.wifikulian.Variable;

/**
 * Created by Lpoint on 2017/1/24.
 */

public class PhoneInfo {
    public static WifiManager wifiManager = (WifiManager) Variable.BASECONTEXT.getSystemService(Context.WIFI_SERVICE);
    public static TelephonyManager telephonyManager = (TelephonyManager) Variable.BASECONTEXT.getSystemService(Context.TELEPHONY_SERVICE);
    public static WifiInfo wifiInfo = wifiManager.getConnectionInfo();
    public static ConnectivityManager connectivityManager = (ConnectivityManager) Variable.BASECONTEXT
            .getSystemService(Context.CONNECTIVITY_SERVICE);

    public static String Mac() {
        String macAddress = "-1";
        if (wifiManager != null && wifiInfo != null)
            macAddress = wifiInfo.getMacAddress();
        return macAddress;
    }

    public static String SSID() {
        String ssid = "-1";
        if (wifiManager != null && wifiInfo != null)
            ssid = wifiInfo.getSSID();
        return ssid.replace("\"", "");
    }

    public static String IMEI() {
        String imei = "-1";
        if (telephonyManager != null)
            imei = telephonyManager.getDeviceId();
        return imei;
    }

    public static String PHONENUM() {
        String phoneNumber = "-1";
        if (telephonyManager != null)
            phoneNumber = telephonyManager.getLine1Number();
        return phoneNumber;
    }

    public static String PHONEMODEL() {
        return android.os.Build.MODEL;
    }
}
