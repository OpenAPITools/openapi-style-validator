package com.jaffsoft.openapistylevalidator.styleerror;

public class ModelStyleError extends StyleError {

    private final String modelName;
    private final String propertyName;

    public ModelStyleError(String fieldNames,
                           String description,
                           String modelName,
                           String propertyName) {
        super(StyleCheckSection.Models, fieldNames, description);
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
