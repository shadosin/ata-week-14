package com.kenzie.dynamodbscan.icecream.converter;

import com.kenzie.dynamodbscan.icecream.exception.SundaeSerializationException;
import com.kenzie.dynamodbscan.icecream.model.Sundae;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang.StringUtils.isBlank;

/**
 * Converts list of Sundae objects to list of Strings, and list of Strings representing Sundaes
 * into list of Sundae objects.
 */
public class SundaeConverter implements DynamoDBTypeConverter<String, List<Sundae>> {
    private ObjectMapper objectMapper;

    public SundaeConverter() {
        objectMapper = new ObjectMapper();
    }

    @Override
    public String convert(List<Sundae> sundaes) {
        if (sundaes == null) {
            return "";
        }
        String jsonSundaes;
        try {
            jsonSundaes = objectMapper.writeValueAsString(sundaes);
        } catch (JsonProcessingException e) {
            throw new SundaeSerializationException(e.getMessage(), e);
        }

        return jsonSundaes;
    }

    @Override
    public List<Sundae> unconvert(String jsonSundaes) {
        List<Sundae> sundaes = new ArrayList<>();
        if (isBlank(jsonSundaes)) {
            return sundaes;
        }
        try {
            sundaes = objectMapper.readValue(jsonSundaes, new TypeReference<List<Sundae>>() {
            });
        } catch (IOException e) {
            throw new SundaeSerializationException(e.getMessage(), e);
        }

        return sundaes;
    }
}
