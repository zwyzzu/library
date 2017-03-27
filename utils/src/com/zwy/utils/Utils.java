/*************************************************************************************
 * Module Name:com.zwy.widget.utils
 * File Name:Utils.java
 * Author: 张维亚
 * Copyright 2007-, Funshion Online Technologies Ltd.
 * All Rights Reserved
 * 版权 2007-，北京风行在线技术有限公司
 * 所有版权保护
 * This is UNPUBLISHED PROPRIETARY SOURCE CODE of Funshion Online Technologies Ltd.;
 * the contents of this file may not be disclosed to third parties, copied or
 * duplicated in any form, in whole or in part, without the prior written
 * permission of Funshion Online Technologies Ltd.
 * 这是北京风行在线技术有限公司未公开的私有源代码。本文件及相关内容未经风行在线技术有
 * 限公司事先书面同意，不允许向任何第三方透露，泄密部分或全部; 也不允许任何形式的私自备份。
 ***************************************************************************************/
package com.zwy.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.ActivityManager;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

/**
 * Author: zhangwy
 * 创建时间：2015年9月18日 上午11:22:22
 * 修改时间：2015年9月18日 上午11:22:22
 * Description: 各种小工具
 **/
public class Utils {

    public static HashSet<String> string2HashSet(String text, char splitter) {
        ArrayList<String> arr = string2ArrayList(text, splitter);
        return array2HashSet(arr);
    }

    /**
     * 使用多个字符字进行分割
     */
    public static String[] string2Array(String text, String splitter) {
        ArrayList<String> arr = string2ArrayList(text, splitter);

        return arr.toArray(new String[arr.size()]);
    }

    public static HashSet<String> string2HashSet(String text, String splitter) {
        ArrayList<String> arr = string2ArrayList(text, splitter);
        return array2HashSet(arr);
    }

    /**
     * 仅使用一个字符进行分割
     */
    public static ArrayList<String> string2ArrayList(String text, char splitter) {
        return array2List(string2Array(text, splitter));
    }

    /**
     * 使用多个字符字进行分割
     */
    public static ArrayList<String> string2ArrayList(String text,
                                                     String splitter) {
        if (TextUtils.isEmpty(text))
            text = "";

        ArrayList<String> arr = new ArrayList<String>();

        int start = 0;
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);

            if (splitter.indexOf(ch) == -1)
                continue;

            if (i > start) {
                String sub = text.substring(start, i).trim();
                if (!TextUtils.isEmpty(sub))
                    arr.add(sub);
            }

            start = i + 1;
        }

        if (start < text.length())
            arr.add(text.substring(start));

        return arr;
    }

    /**
     * 仅使用一个字符进行分割
     */
    public static String[] string2Array(String text, char splitter) {
        if (TextUtils.isEmpty(text))
            text = "";

        String reg = "\\" + Character.toString(splitter);
        return text.split(reg);
    }

    public static <E> HashSet<E> array2HashSet(E[] arr) {
        return array2HashSet(array2List(arr));
    }

    public static <E> ArrayList<E> array2List(E[] arr) {
        List<E> eList = java.util.Arrays.asList(arr);

        ArrayList<E> list = new ArrayList<E>(eList.size());
        list.addAll(eList);
        return list;
    }

    public static String array2Strings(char splitter, Object... objs) {
        String value = "";
        if (isEmpty(objs))
            return value;

        int length = objs.length;
        for (int i = 0; i < length; i++) {
            if (i > 0) {
                value += splitter;
            }

            value += objs[i].toString();
        }
        return value;
    }

    public static <T> String array2Strings(ArrayList<T> array, char splitter) {
        return array2Strings(array, String.valueOf(splitter));
    }

    public static <T> String array2Strings(ArrayList<T> arr, String splitter) {
        if (isEmpty(arr))
            return "";

        String value = "";
        for (int i = 0; i < arr.size(); i++) {
            if (i > 0) {
                value += splitter;
            }

            value += arr.get(i).toString();
        }
        return value;
    }

    public static <E> HashSet<E> array2HashSet(ArrayList<E> arr) {
        if (isEmpty(arr))
            return new HashSet<E>(0);

        HashSet<E> ret = new HashSet<E>(arr.size());
        for (E elem : arr) {
            ret.add(elem);
        }

        return ret;
    }

    public static String hashSet2String(HashSet<String> set, char ch) {
        ArrayList<String> arr = hashSet2Array(set);
        return array2Strings(arr, ch);
    }

    public static <E> ArrayList<E> hashSet2Array(HashSet<E> set) {
        if (isEmpty(set))
            return new ArrayList<E>(0);

        ArrayList<E> ret = new ArrayList<E>(set.size());
        for (E elem : set) {
            ret.add(elem);
        }

        return ret;
    }

    /**
     * 分解键值对列表 下同
     */
    public static HashMap<String, String> keyValueList2Map(String keyValues,
                                                           String chStr, char chArray) {
        return keyValueList2Map(string2ArrayList(keyValues, chStr), chArray);
    }

    public static HashMap<String, String> keyValueList2Map(
            ArrayList<String> keyValues, char ch) {
        HashMap<String, String> map = new HashMap<String, String>();
        for (String string : keyValues) {
            int index = string.indexOf(ch);
            if (index <= -1)
                continue;
            String key = string.substring(0, index);
            String value = string.substring(index + 1);
            map.put(key, value);
        }
        return map;
    }

    @SuppressWarnings("rawtypes")
    public static <K, V> String mapValue2String(HashMap<K, V> map, char ch) {
        if (isEmpty(map)) {
            return "";
        }

        StringBuilder str = new StringBuilder();
        Collection<V> collection = map.values();
        for (Iterator it = collection.iterator(); it.hasNext(); ) {
            str.append(String.valueOf(it.next()));
            if (it.hasNext()) {
                str.append(ch);
            }
        }
        return str.toString();
    }

    /**
     * 判断array(list)是否为空
     */
    public static boolean isEmpty(List<?> array) {
        return array == null || array.size() <= 0;
    }

    public static boolean isEmpty(HashSet<?> set) {
        return set == null || set.size() <= 0;
    }

    public static <K, V> boolean isEmpty(Map<K, V> map) {
        return map == null || map.size() <= 0;
    }

    public static boolean isEmpty(Object... obj) {
        return obj == null || obj.length <= 0;
    }

    public static void releaseView(View view) {
        if (view == null)
            return;

        if (view.getBackground() != null)
            view.getBackground().setCallback(null);

        if (view instanceof ViewGroup)
            releaseView((ViewGroup) view);
    }

    public static void releaseView(ViewGroup view) {
        if (view == null)
            return;

        for (int i = 0; i < view.getChildCount(); i++) {
            releaseView(view.getChildAt(i));
        }
        view.removeAllViews();
    }

    public static double byte2Kb(long byteSize) {
        return byteSize / 1024.0;
    }

    public static double kb2Mb(long kb) {
        return kb / 1024.0;
    }

    public static double byte2Mb(long byteSize) {
        return byteSize / (1024 * 1024);
    }

    public static int getEvenNumber(int num) {
        if (num % 2 != 0) {
            return num - 1;
        }
        return num;
    }

    public static String getProcessName(Context ctx) {
        int pid = android.os.Process.myPid();
        ActivityManager manager = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo process : manager.getRunningAppProcesses()) {
            if (process.pid == pid)
                return process.processName;
        }
        return "unknown";
    }

    public static String makeCycleMsg(Object obj, String cycle) {
        if (obj == null)
            return cycle;
        return (new StringBuffer()).append(obj.getClass().getSimpleName()).append('|').append(cycle).append('|').append(obj.hashCode()).toString();
    }

    public static String reverser(String string) {
        if (TextUtils.isEmpty(string))
            return string;
        char[] arrays = string.toCharArray();
        StringBuffer buffer = new StringBuffer();
        for (int i = arrays.length - 1; i >= 0; i--) {
            buffer.append(arrays[i]);
        }
        return buffer.toString();
    }
}