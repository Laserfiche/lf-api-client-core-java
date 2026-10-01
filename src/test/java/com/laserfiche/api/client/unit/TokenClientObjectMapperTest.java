// Copyright (c) Laserfiche.
// Licensed under the MIT License. See LICENSE in the project root for license information.
package com.laserfiche.api.client.unit;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.impl.TypeWrappedDeserializer;
import com.fasterxml.jackson.databind.jsontype.impl.TypeDeserializerBase;
import com.laserfiche.api.client.deserialization.TokenClientObjectMapper;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TokenClientObjectMapperTest {
    private static final int REQUEST_COUNT = 10_000;
    private static final int MAX_CACHED_TYPE_IDS = 1_000;

    @Test
    void readValue_DistinctUnknownTypeIdsHaveBoundedCache() throws ReflectiveOperationException {
        TokenClientObjectMapper mapper = new TokenClientObjectMapper();
        mapper.readValue("{\"type\":\"known\",\"value\":\"before\"}", Base.class);
        Map<?, ?> cache = cachedTypeDeserializers(mapper);
        int maximumCacheSize = cache.size();

        for (int i = 0; i < REQUEST_COUNT; i++) {
            Base result = mapper.readValue(
                    "{\"type\":\"unknown-" + i + "\",\"value\":\"fallback\"}", Base.class);
            assertInstanceOf(Fallback.class, result);
            assertEquals("fallback", result.value);
            maximumCacheSize = Math.max(maximumCacheSize, cache.size());
        }

        Base known = mapper.readValue("{\"type\":\"known\",\"value\":\"after\"}", Base.class);
        assertInstanceOf(Known.class, known);
        assertEquals("after", known.value);
        assertTrue(cache.containsKey("known"));
        assertTrue(maximumCacheSize <= MAX_CACHED_TYPE_IDS,
                "Maximum cache size after distinct unknown IDs: " + maximumCacheSize);
        assertTrue(cache.size() <= MAX_CACHED_TYPE_IDS);
    }

    @Test
    void readValue_RepeatedUnknownTypeIdRetainsOneCacheEntry() throws ReflectiveOperationException {
        TokenClientObjectMapper mapper = new TokenClientObjectMapper();
        for (int i = 0; i < REQUEST_COUNT; i++) {
            Base result = mapper.readValue(
                    "{\"type\":\"unknown\",\"value\":\"fallback\"}", Base.class);
            assertInstanceOf(Fallback.class, result);
            assertEquals("fallback", result.value);
        }

        assertEquals(1, cachedTypeDeserializers(mapper).size());
    }

    @Test
    void readValue_OverlongUnknownTypeIdIsNotCached() throws ReflectiveOperationException {
        TokenClientObjectMapper mapper = new TokenClientObjectMapper();
        String typeId = new String(new char[257]).replace('\0', 'x');

        Base result = mapper.readValue(
                "{\"type\":\"" + typeId + "\",\"value\":\"fallback\"}", Base.class);

        assertInstanceOf(Fallback.class, result);
        assertEquals("fallback", result.value);
        assertTrue(cachedTypeDeserializers(mapper).isEmpty());
    }

    private static Map<?, ?> cachedTypeDeserializers(TokenClientObjectMapper mapper)
            throws ReflectiveOperationException {
        ObjectMapper jacksonMapper = (ObjectMapper) readField(
                mapper, TokenClientObjectMapper.class, "jacksonMapper");
        Map<?, ?> roots = (Map<?, ?>) readField(
                jacksonMapper, ObjectMapper.class, "_rootDeserializers");
        Object root = roots.get(jacksonMapper.constructType(Base.class));
        assertInstanceOf(TypeWrappedDeserializer.class, root);
        Object typeDeserializer = readField(
                root, TypeWrappedDeserializer.class, "_typeDeserializer");
        return (Map<?, ?>) readField(
                typeDeserializer, TypeDeserializerBase.class, "_deserializers");
    }

    private static Object readField(Object target, Class<?> declaringClass, String name)
            throws ReflectiveOperationException {
        Field field = declaringClass.getDeclaredField(name);
        field.setAccessible(true);
        return field.get(target);
    }

    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", defaultImpl = Fallback.class)
    @JsonSubTypes(@JsonSubTypes.Type(value = Known.class, name = "known"))
    public abstract static class Base {
        public String value;
    }

    public static class Fallback extends Base {
    }

    public static class Known extends Base {
    }
}
