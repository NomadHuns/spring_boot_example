package com.example.spring_boot_jpa_example._core.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonScriptUtils {
    public static String back(String msg) {
        StringBuilder sb = new StringBuilder();
        msg = escapeRegexCharacters(msg);
        msg = msg.replace("'", "\\'").replace("\"", "\\\""); // 작은따옴표와 큰따옴표 이스케이프
        sb.append("<html>");
        sb.append("<script>");
        sb.append("alert('" + msg + "');");
        sb.append("history.back();");
        sb.append("</script>");
        sb.append("</html>");
        return sb.toString();
    }

    public static String herf(String msg, String location) {
        StringBuilder sb = new StringBuilder();
        msg = escapeRegexCharacters(msg);
        msg = msg.replace("'", "\\'").replace("\"", "\\\""); // 작은따옴표와 큰따옴표 이스케이프
        sb.append("<html>");
        sb.append("<script>");
        sb.append("alert('" + msg + "');");
        sb.append("location.href =\" " + location + "\" ;");
        sb.append("</script>");
        sb.append("</html>");
        return sb.toString();
    }

    public static String escapeRegexCharacters(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        // 정규식 특수 문자
        String specialChars = "[\\.\\^\\$\\*\\+\\-\\?\\(\\)\\[\\]\\{\\}\\|\\\\]";
        Pattern pattern = Pattern.compile(specialChars);
        Matcher matcher = pattern.matcher(input);

        // 특수 문자 앞에 '\' 추가
        StringBuffer escapedString = new StringBuffer();
        while (matcher.find()) {
            // 특수 문자 앞에 이스케이프 추가
            matcher.appendReplacement(escapedString, "\\" + matcher.group());
        }
        matcher.appendTail(escapedString);

        return escapedString.toString();
    }
}
