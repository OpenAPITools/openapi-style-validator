package org.openapitools.openapistylevalidator.styleerror;

import java.util.Objects;

public final class ModelStyleError extends StyleError {
    private final String modelName;
    private final String propertyName;

    public ModelStyleError(String fieldNames, String description, String modelName, String propertyName) {
        super(StyleCheckSection.Models, fieldNames, description);
        this.modelName = modelName;
        this.propertyName = propertyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModelStyleError that = (ModelStyleError) o;
        return styleCheckSection == that.styleCheckSection
                && Objects.equals(fieldNames, that.fieldNames)
                && Objects.equals(description, that.description)
                && Objects.equals(modelName, that.modelName)
                && Objects.equals(propertyName, that.propertyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(styleCheckSection, fieldNames, description, modelName, propertyName);
    }

    @Override
    public String toString() {
        String propertyPart = propertyName == null ? "" : String.format(", property '%s'", propertyName);
        String fieldPart = fieldNames == null ? "" : String.format(", field '%s'", fieldNames);
        return String.format("*ERROR* in Model '%s'%s%s -> %s", modelName, propertyPart, fieldPart, description);
    }
}
