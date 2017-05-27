package com.zhangwy.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONObject;

import java.io.File;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Author: zhangwy(张维亚)
 * 创建时间：2017/4/6 下午4:41
 * 修改时间：2017/4/6 下午4:41
 * Description:
 */

public class Util {


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

        ArrayList<String> arr = new ArrayList<>();

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

    public static ArrayList<Integer> string2IntArray(String text, char splitter, final boolean sort) {
        ArrayList<Integer> array = string2IntArray(text, splitter);
        if (isEmpty(array))
            return null;
        Collections.sort(array, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return sort ? o1 - o2 : o2 - o1;
            }
        });
        return array;
    }

    public static ArrayList<Integer> string2IntArray(String text, char splitter) {
        String[] strings = string2Array(text, splitter);
        if (isEmpty(strings))
            return null;
        ArrayList<Integer> array = new ArrayList<>(strings.length);
        for (String string : strings) {
            try {
                array.add(Integer.getInteger(string));
            } catch (Exception e) {
                Logger.e("string2IntArray", e);
            }
        }
        if (isEmpty(array))
            return null;
        return array;
    }

    public static <E> HashSet<E> array2HashSet(E[] arr) {
        return array2HashSet(array2List(arr));
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
            return new HashSet<>(0);

        HashSet<E> ret = new HashSet<>(arr.size());
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
            return new ArrayList<>(0);

        ArrayList<E> ret = new ArrayList<>(set.size());
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
        HashMap<String, String> map = new HashMap<>();
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
     * 获取当前应用程序的包名
     *
     * @param context 上下文对象
     * @return 返回包名
     */
    public static String getAppPackageName(Context context) {
        return context.getPackageName();
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

    public static <T> boolean isEmpty(T... obj) {
        return obj == null || obj.length <= 0;
    }

    /**
     * map转json
     *
     * @param map Map<String, Object>转成json 其中Object指基本数据类型和String
     * @return String
     */
    public static String map2Json(Map<String, Object> map) {
        if (isEmpty(map))
            return "{}";
        return new JSONObject(map).toString();
    }

    /**
     * 仅使用一个字符进行分割
     */
    public static ArrayList<String> string2List(String text, char splitter) {
        return array2List(string2Array(text, splitter));
    }

    public static <E> ArrayList<E> array2List(E[] arr) {
        List<E> eList = java.util.Arrays.asList(arr);

        ArrayList<E> list = new ArrayList<>(eList.size());
        list.addAll(eList);
        return list;
    }

    public static ArrayList<Integer> string2IntList(String text, char splitter, final boolean sort) {
        ArrayList<Integer> array = string2IntList(text, splitter);
        if (isEmpty(array))
            return null;
        Collections.sort(array, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return sort ? o1 - o2 : o2 - o1;
            }
        });
        return array;
    }

    public static ArrayList<Integer> string2IntList(String text, char splitter) {
        String[] strings = string2Array(text, splitter);
        if (isEmpty(strings))
            return null;
        ArrayList<Integer> array = new ArrayList<>();
        for (String string : strings) {
            try {
                array.add(Integer.valueOf(string));
            } catch (Exception e) {
                Logger.e("string2IntArray", e);
            }
        }
        if (isEmpty(array))
            return null;
        return array;
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

    public static <T> String array2Strings(List<T> array, char splitter) {
        return array2Strings(array, String.valueOf(splitter));
    }

    public static <T> String array2Strings(List<T> arr, String splitter) {
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

    public static String urlEncoder(String params) {
        String paramsResult = "";
        try {
            paramsResult = URLEncoder.encode(params, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Logger.e("encoder", e);
        }
        return paramsResult;
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

    public static String byte2hex(byte[] bytes) {
        if (isEmpty(bytes))
            return "";
        StringBuffer hs = new StringBuffer(bytes.length);
        for (byte b : bytes) {
            String current = Integer.toHexString(b & 0xFF);
            if (current.length() == 1)
                hs = hs.append("0").append(current);
            else {
                hs = hs.append(current);
            }
        }
        return String.valueOf(hs);
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

    public static void installApp(Context context, String filePath) {
        if (hasRootPermission()) {
            Process process = null;
            try {
                process = Runtime.getRuntime().exec("su");
                PrintWriter printWriter = new PrintWriter(process.getOutputStream());
                printWriter.println("chmod 777 " + filePath);
                printWriter.println("export LD_LIBRARY_PATH=/vendor/lib:/system/lib");
                printWriter.println("pm install -r " + filePath);
                printWriter.flush();
                printWriter.close();
                process.waitFor();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (process != null) {
                    process.destroy();
                }
            }
        } else {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(new File(filePath)), "application/vnd.android.package-archive");
            context.startActivity(intent);
        }
    }

    public static boolean hasRootPermission() {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("su");
            PrintWriter printWriter = new PrintWriter(process.getOutputStream());
            printWriter.flush();
            printWriter.close();
            return process.waitFor() == 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
        return false;
    }

    public static void unInstallApp(Context context, String packageName) {
        Uri packageUri = Uri.parse("package:" + packageName);
        Intent intent = new Intent(Intent.ACTION_DELETE, packageUri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static String getApkFilePackage(Context context, File apkFile) {
        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(apkFile.getPath(), PackageManager.GET_ACTIVITIES);
        if (info != null) {
            return info.applicationInfo.packageName;
        }
        return null;
    }

    public static boolean isAppInstalled(Context context, String packageName) {
        List<PackageInfo> packages = context.getPackageManager().getInstalledPackages(0);
        if (!isEmpty(packages)) {
            for (PackageInfo packageInfo : packages) {
                if (packageInfo.packageName.equals(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }
}