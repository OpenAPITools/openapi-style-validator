package org.openapitools.openapistylevalidator.cli;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.io.IOException;
import org.openapitools.openapistylevalidator.ValidatorParameters;
import org.openapitools.openapistylevalidator.api.NamingConvention;

class ObjectMapperProvider {

    private ObjectMapper optionsObjectMapper;

    /**
     * @return a Jackson ObjectMapper tailored to deserialize the
     * {@link ValidatorParameters}. This method is not thread safe.
     */
    ObjectMapper getOptionsObjectMapper() {
        if (optionsObjectMapper != null) return optionsObjectMapper;

        optionsObjectMapper = new ObjectMapper()
                .registerModule(new SimpleModule("NamingConvention")
                        .addDeserializer(NamingConvention.class, new NamingOptionsDeserializer()));
        return optionsObjectMapper;
    }

    private static class NamingOptionsDeserializer extends JsonDeserializer<NamingConvention> {
        @Override
        public NamingConvention deserialize(JsonParser parser, DeserializationContext ctxt)
                throws IOException, JsonProcessingException {
            String name = parser.getText();
            NamingConvention[] values = NamingConvention.values();
            for (NamingConvention value : values) {
                if (value.name().equals(name)) {
                    return value;
                }
            }
            /* Return null for unknown values so that validateNamingConvention
             * can report this error */
            return null;
        }
    }
}
