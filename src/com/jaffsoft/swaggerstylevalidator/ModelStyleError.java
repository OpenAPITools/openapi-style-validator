package com.jaffsoft.swaggerstylevalidator;

import io.swagger.models.HttpMethod;
import io.swagger.models.properties.Property;

public class ModelStyleError extends StyleError {

    private final String modelName;
    private final String propertyName;

    public ModelStyleError(StyleCheckSection styleCheckSection,
                           String parentObjectName,
                           String fieldNames,
                           String description,
                           String modelName,
                           String propertyName) {
        super(styleCheckSection, parentObjectName, fieldNames, description);
        this.modelName = modelName;
        this.propertyName = propertyName;

    }

    @Override
    public String toString() {
        return String.format("*ERROR* in Model '%s', property '%s', field '%s' -> %s",
                modelName,
                propertyName,
                fieldNames,
                description);
    }
}
