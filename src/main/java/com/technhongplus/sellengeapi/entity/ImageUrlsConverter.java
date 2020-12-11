package com.technhongplus.sellengeapi.entity;

import lombok.NonNull;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.List;

@Converter
public class ImageUrlsConverter implements AttributeConverter<List<String>, String> {
    @Override
    public String convertToDatabaseColumn(@NonNull List<String> attribute) {
        return String.join(",", attribute);
    }

    @Override
    public List<String> convertToEntityAttribute(@NonNull String dbData) {
        return Arrays.asList(dbData.split(","));
    }
}