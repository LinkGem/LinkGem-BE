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

        long pastHours = Duration.between(date, LocalDateTime.now()).toHours();

        long pastDays = pastHours / 24;

        if (isDoublePasted(pastHours)) {
            pastDays++;
        }

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

    /**
     * 시간상으로 하루가 지나지 않았지만 일수는 변했을 경우
     * @param pastHours 경과 시간
     * @return true/false
     */
    private static boolean isDoublePasted(long pastHours) {
        return pastHours % 24 > 0;
    }
}
