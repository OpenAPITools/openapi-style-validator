package org.openapitools.openapistylevalidator.error;

import java.util.Objects;
import org.eclipse.microprofile.openapi.models.PathItem;

public final class OperationStyleError extends StyleError {

    private final String path;
    private final PathItem.HttpMethod method;

    public OperationStyleError(String fieldNames, String description, String path, PathItem.HttpMethod method) {
        super(StyleCheckSection.Operations, fieldNames, description);
        this.path = path;
        this.method = method;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperationStyleError that = (OperationStyleError) o;
        return styleCheckSection == that.styleCheckSection
                && Objects.equals(fieldNames, that.fieldNames)
                && Objects.equals(description, that.description)
                && Objects.equals(path, that.path)
                && method == that.method;
    }

    @Override
    public int hashCode() {
        return Objects.hash(styleCheckSection, fieldNames, description, path, method);
    }

    @Override
    public String toString() {
        return String.format("*ERROR* in Operation %s %s '%s' -> %s", method.name(), path, fieldNames, description);
    }
}
