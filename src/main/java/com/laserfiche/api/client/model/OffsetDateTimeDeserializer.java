package com.laserfiche.api.client.model;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.threeten.bp.OffsetDateTime;

import java.io.IOException;

public class OffsetDateTimeDeserializer extends StdDeserializer<OffsetDateTime> {
    public OffsetDateTimeDeserializer() {
        this(null);
    }

    public OffsetDateTimeDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public OffsetDateTime deserialize(JsonParser parser,
            DeserializationContext ctx) throws IOException, JacksonException {
        TreeNode tree = parser
                .getCodec()
                .readTree(parser);
        String dateString = tree
                .toString()
                .replaceAll("\"", "");
        return OffsetDateTime.parse(dateString);
    }
}
