package io.ayers.spring_micro_beerservice.web.mappers;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Component
public class DateMapper {
    public OffsetDateTime asOffsetDateTime(Timestamp ts) {
        return ts == null ? null : convertTimestampToOffsetDateTime(ts);
    }

    public Timestamp asTimestamp(OffsetDateTime offsetDateTime) {
        return offsetDateTime == null
                ? null
                : convertOffsetDateTimeToTimestamp(offsetDateTime);
    }

    private Timestamp convertOffsetDateTimeToTimestamp(OffsetDateTime offsetDateTime) {
        return Timestamp.valueOf(offsetDateTime.atZoneSameInstant(ZoneOffset.UTC)
                .toLocalDateTime());
    }

    private OffsetDateTime convertTimestampToOffsetDateTime(Timestamp ts) {

        LocalDateTime localDateTime = ts.toLocalDateTime();
        var year = localDateTime.getYear();
        var month = localDateTime.getMonthValue();
        var day = localDateTime.getDayOfMonth();
        var hour = localDateTime.getHour();
        var minute = localDateTime.getMinute();
        var second = localDateTime.getSecond();
        var nano = localDateTime.getNano();

        return OffsetDateTime.of(year, month, day, hour, minute, second, nano, ZoneOffset.UTC);
    }
}
