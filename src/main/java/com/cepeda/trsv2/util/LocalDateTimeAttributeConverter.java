package com.cepeda.trsv2.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author CyborgK27
 */
@Converter(autoApply = true)
public class LocalDateTimeAttributeConverter 
        implements AttributeConverter<LocalDateTime, String> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public String convertToDatabaseColumn(LocalDateTime locDateTime) {
        return (locDateTime == null ? null : locDateTime.format(FORMATTER));
    }

    @Override
    public LocalDateTime convertToEntityAttribute(String sqlDateTime) {
        return (sqlDateTime == null ? null : LocalDateTime
                .parse(sqlDateTime, FORMATTER));
    }
}