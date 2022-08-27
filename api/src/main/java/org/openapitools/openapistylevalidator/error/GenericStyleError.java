package org.openapitools.openapistylevalidator.error;

import java.util.Objects;

public final class GenericStyleError extends StyleError {

    private final String parentObjectName;

    public GenericStyleError(
            StyleCheckSection styleCheckSection,
            String parentObjectName,
            String fieldNames,
            @SuppressWarnings("SameParameterValue") String description) {
        super(styleCheckSection, fieldNames, description);

        this.parentObjectName = parentObjectName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenericStyleError that = (GenericStyleError) o;
        return styleCheckSection == that.styleCheckSection
                && Objects.equals(fieldNames, that.fieldNames)
                && Objects.equals(description, that.description)
                && Objects.equals(parentObjectName, that.parentObjectName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(styleCheckSection, fieldNames, description, parentObjectName);
    }

    @Override
    public String toString() {
        return String.format(
                "*ERROR* Section: %s: '%s' %s -> %s",
                styleCheckSection.toString(),
                fieldNames,
                parentObjectName.isEmpty() ? "" : String.format("in %s", parentObjectName),
                description);
    }
}
