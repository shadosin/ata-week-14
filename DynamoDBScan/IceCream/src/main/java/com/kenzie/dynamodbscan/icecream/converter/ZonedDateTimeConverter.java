package com.kenzie.dynamodbscan.icecream.converter;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Converts ZonedDateTime objects to Strings, and Strings representing ZonedDateTimes into ZonedDateTime objects.
 */
public class ZonedDateTimeConverter implements DynamoDBTypeConverter<String, ZonedDateTime> {
    @Override
    public String convert(ZonedDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ISO_DATE_TIME);
    }

    @Override
    public ZonedDateTime unconvert(String dateTimeRepresentation) {
        return ZonedDateTime.parse(dateTimeRepresentation);
    }
}
