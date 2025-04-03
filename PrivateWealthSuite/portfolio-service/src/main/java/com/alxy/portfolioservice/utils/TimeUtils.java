package com.alxy.portfolioservice.utils;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtils {
    public static String toDate(String date) {
        OffsetDateTime offsetDateTime = OffsetDateTime.parse(date);

        // 定义目标格式的日期时间格式化器
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 将 OffsetDateTime 格式化为目标格式的字符串
        String formattedDateTime = offsetDateTime.format(formatter);
        return formattedDateTime;
    }
}
