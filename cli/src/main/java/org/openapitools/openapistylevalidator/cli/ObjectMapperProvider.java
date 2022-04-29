package org.openapitools.openapistylevalidator.cli;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.io.IOException;
import org.openapitools.openapistylevalidator.ValidatorParameters;

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
                        .addDeserializer(ValidatorParameters.NamingConvention.class, new NamingOptionsDeserializer()));
        return optionsObjectMapper;
    }

    private class NamingOptionsDeserializer extends JsonDeserializer<ValidatorParameters.NamingConvention> {
        @Override
        public ValidatorParameters.NamingConvention deserialize(JsonParser parser, DeserializationContext ctxt)
                throws IOException, JsonProcessingException {
            String name = parser.getText();
            ValidatorParameters.NamingConvention[] values = ValidatorParameters.NamingConvention.values();
            for (ValidatorParameters.NamingConvention value : values) {
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
