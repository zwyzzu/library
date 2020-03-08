package com.zhangwy.util;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ping {

    public static String ping(String ipAddress, int times, int timeOut) {
        BufferedReader in = null;
        try {
            String command = "ping -c %d -w %d %s";
            command = String.format(command, times, timeOut, ipAddress);
            Logger.d(command);
            Process process = Runtime.getRuntime().exec(command);
            if (process == null) {
                return "{}";
            }
            in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            JSONArray array = new JSONArray();
            while ((line = in.readLine()) != null) {
                Logger.d(line);
                PingItem item = createPingItem(line);
                if (item == null) {
                    continue;
                }
                array.put(item.toJson());
            }
            return array.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "{}";
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static PingItem createPingItem(String line) {
        if (TextUtils.isEmpty(line)) {
            return null;
        }
        line = line.toLowerCase();
        Pattern pattern = Pattern.compile("(from [0-9]*.[0-9]*.[0-9]*.[0-9]*).*(ttl=[0-9]*).*(time=[0-9]*)");
        Matcher matcher = pattern.matcher(line);
        if (!matcher.find()) {
            return null;
        }
        String ip = null, time = null, ttl = null;
        int index = 0;
        while (true) {
            try {
                String string = matcher.group(index);
                index++;
                if (TextUtils.isEmpty(string)) {
                    continue;
                }
                if (string.startsWith("from")) {
                    ip = getValue(string, " ", "ttl", "time");
                } else if (string.startsWith("time")) {
                    time = getValue(string, "=", "ttl", "from");
                } else if (string.startsWith("ttl")) {
                    ttl = getValue(string, "=", "time", "from");
                }
            } catch (Exception e) {
                break;
            }
        }
        if (TextUtils.isEmpty(ip) || TextUtils.isEmpty(ttl) || TextUtils.isEmpty(time)) {
            return null;
        }
        return PingItem.create(ip, ttl, time);
    }

    private static String getValue(String string, String separator, String... unContains) {
        if (unContains != null) {
            for (String unContain : unContains) {
                if (string.startsWith(unContain)) {
                    return null;
                }
            }
        }
        try {
            return string.split(separator)[1];
        } catch (Exception e) {
            return null;
        }
    }

    public static class PingItem {
        public String ip;
        public int ttl;
        public float time;

        public PingItem(String ip, int ttl, float time) {
            this.ip = ip;
            this.ttl = ttl;
            this.time = time;
        }

        public static PingItem create(String ip, String ttlString, String timeString) {
            int ttl = Integer.parseInt(ttlString);
            float time = Float.parseFloat(timeString);
            return new PingItem(ip, ttl, time);
        }

        public JSONObject toJson() {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("ip", this.ip);
                jsonObject.put("ttl", this.ttl);
                jsonObject.put("time", this.time);
            } catch (JSONException e) {
                Logger.d("toJson", e);
            }
            return jsonObject;
        }

        @Override
        public String toString() {
            return toJson().toString();
        }
    }

}
