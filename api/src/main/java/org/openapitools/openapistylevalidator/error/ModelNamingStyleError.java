package org.openapitools.openapistylevalidator.error;

import java.util.Objects;

public final class ModelNamingStyleError extends StyleError {

    private final String model;

    public ModelNamingStyleError(
            StyleCheckSection styleCheckSection, String fieldNames, String description, String model) {
        super(styleCheckSection, fieldNames, description);
        this.model = model;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModelNamingStyleError that = (ModelNamingStyleError) o;
        return styleCheckSection == that.styleCheckSection
                && Objects.equals(fieldNames, that.fieldNames)
                && Objects.equals(description, that.description)
                && Objects.equals(model, that.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(styleCheckSection, fieldNames, description, model);
    }

    @Override
    public String toString() {
        return String.format("*ERROR* in model %s '%s' -> %s", model, fieldNames, description);
    }
}
