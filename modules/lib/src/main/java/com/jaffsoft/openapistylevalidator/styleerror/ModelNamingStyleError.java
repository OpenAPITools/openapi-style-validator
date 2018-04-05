package com.jaffsoft.openapistylevalidator.styleerror;

public class ModelNamingStyleError extends StyleError {

    private final String model;

    public ModelNamingStyleError(StyleCheckSection styleCheckSection, String fieldNames, String description, String model) {
        super(styleCheckSection, fieldNames, description);
        this.model = model;
    }

    @Override
    public String toString() {
        return String.format("*ERROR* in model %s '%s' -> %s",
                model,
                fieldNames,
                description);
    }
}
