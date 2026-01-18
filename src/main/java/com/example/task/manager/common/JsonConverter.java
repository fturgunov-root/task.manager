package com.example.task.manager.common;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JsonConverter {
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public <T> T convertFromString(String source, Class<T> targetType) {
        if (source == null) {
            return null;
        }
        return objectMapper.readValue(source, targetType);
    }

    @SneakyThrows
    public <T> T convertFromString(String source, TypeReference<T> valueTypeRef) {
        if (source == null) {
            return null;
        }
        return objectMapper.readValue(source, valueTypeRef);
    }

    @SneakyThrows
    public <T> String convertToString(T t) {
        if (t == null) {
            return null;
        }
        return objectMapper.writeValueAsString(t);
    }
}