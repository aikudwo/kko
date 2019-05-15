package com.aikudwo.ccy.utils;

import java.util.Map;

public class StringUtils extends org.apache.commons.lang3.StringUtils {

    public static String toCommaString(String[] strings) {
        if (strings.length == 0)
            return "";
        StringBuilder buf = new StringBuilder();
        for (String s : strings) {
            buf.append(",");
            buf.append(s);
        }
        buf.deleteCharAt(0);
        return buf.toString();
    }

    public static String toCommaStringByMap(String[] strings, Map map) {
        return toCommaStringByMap(strings, map, ",");
    }

    public static String toCommaStringByMap(String[] strings, Map map, String splitString) {
        if (strings.length == 0)
            return "";
        StringBuilder buf = new StringBuilder();
        for (String s : strings) {
            if (map.get(s) != null) {
                buf.append(splitString);
                buf.append(map.get(s));
            }
        }
        if (buf.length() == 0)
            return "";
        buf.deleteCharAt(0);
        return buf.toString();
    }
}
