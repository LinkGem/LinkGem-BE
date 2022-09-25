package com.linkgem.domain.common.date;

import java.time.Duration;
import java.time.LocalDateTime;

public class PastDay {

    private PastDay() {
    }

    public static String getPastDay(LocalDateTime date) {

        if (date == null) {
            return "";
        }

        long pastDays = Duration.between(date, LocalDateTime.now()).toDays();

        if (pastDays > 120) {
            return "오래 전";
        } else if (pastDays > 90) {
            return "3개월 전";
        } else if (pastDays > 60) {
            return "2개월 전";
        } else if (pastDays > 30) {
            return "1개월 전";
        } else if (pastDays == 0) {
            return "오늘";
        } else {
            return String.format("%d일 전", pastDays);
        }
    }
}
