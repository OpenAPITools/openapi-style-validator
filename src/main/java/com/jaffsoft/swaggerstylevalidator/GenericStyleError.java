package com.jaffsoft.swaggerstylevalidator;

public class GenericStyleError extends StyleError {

    private final String parentObjectName;

    public GenericStyleError(StyleCheckSection styleCheckSection, String parentObjectName, String fieldNames, @SuppressWarnings("SameParameterValue") String description) {
        super(styleCheckSection, fieldNames, description);

        this.parentObjectName = parentObjectName;
    }

    @Override
    public String toString() {
        return String.format("*ERROR* Section: %s: '%s' %s -> %s",
                styleCheckSection.toString(),
                fieldNames,
                parentObjectName.isEmpty() ? "" : String.format("in %s", parentObjectName),
                description);
    }
}
