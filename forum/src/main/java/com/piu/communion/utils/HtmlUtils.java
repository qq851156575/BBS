package com.piu.communion.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlUtils {

    /*
     * 正则表达过滤HTML标签
     */
    public static String filterHtmlTag(String htmlContent){
        Pattern pattern = Pattern.compile("<([^>]*)>");
        Matcher matcher = pattern.matcher(htmlContent);
        StringBuffer sb = new StringBuffer();
        boolean result1 = matcher.find();
        while (result1) {
            matcher.appendReplacement(sb, "");
            result1 = matcher.find();
        }
        matcher.appendTail(sb);
        return sb.toString();
    }



}
