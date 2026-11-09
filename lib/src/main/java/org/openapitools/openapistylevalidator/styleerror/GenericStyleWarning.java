package org.openapitools.openapistylevalidator.styleerror;

import java.util.Objects;

public final class GenericStyleWarning extends StyleError {
    private final String parentObjectName;

    public GenericStyleWarning(
            StyleCheckSection styleCheckSection, String parentObjectName, String fieldNames, String description) {
        super(styleCheckSection, fieldNames, description);

        this.parentObjectName = parentObjectName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenericStyleWarning that = (GenericStyleWarning) o;
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
                "*WARNING* Section: %s: '%s' %s -> %s",
                styleCheckSection.toString(),
                fieldNames,
                parentObjectName.isEmpty() ? "" : String.format("in %s", parentObjectName),
                description);
    }
}
