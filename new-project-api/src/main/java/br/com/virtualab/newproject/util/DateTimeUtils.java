package br.com.virtualab.newproject.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {

    public static final String ZONEID_SAOPAULO = "America/Sao_Paulo";

    public static ZoneId getZoneIdSaoPaulo() {
        return ZoneId.of(ZONEID_SAOPAULO);
    }

    public static LocalDateTime getLocalDateTimeNow() {
        return LocalDateTime.now(getZoneIdSaoPaulo()).withSecond(0).withNano(0);
    }

    public static LocalDate getLocalDateNow() {
        return LocalDate.now(getZoneIdSaoPaulo());
    }

    public static LocalTime getLocalTimeNow() {
        return LocalTime.now(getZoneIdSaoPaulo()).withSecond(0).withNano(0);
    }

    public static DateTimeFormatter formatDate() {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy");
    }

    public static DateTimeFormatter formatTime() {
        return DateTimeFormatter.ofPattern("HH:mm");
    }

    public static DateTimeFormatter formatDateTime() {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm");
    }

}
