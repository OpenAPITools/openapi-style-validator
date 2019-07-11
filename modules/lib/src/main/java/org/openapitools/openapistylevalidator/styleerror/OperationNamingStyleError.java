package org.openapitools.openapistylevalidator.styleerror;

import org.eclipse.microprofile.openapi.models.PathItem;

public class OperationNamingStyleError extends StyleError {

    private final String path;
    private final PathItem.HttpMethod method;

    public OperationNamingStyleError(StyleCheckSection styleCheckSection, String fieldNames, String description, String path, PathItem.HttpMethod method) {
        super(styleCheckSection, fieldNames, description);
        this.path = path;
        this.method = method;
    }

    @Override
    public String toString() {
        return String.format("*ERROR* in path %s%s '%s' -> %s",
                method == null ? "" : String.format("%s ", method.name()),
                path,
                fieldNames,
                description);
    }
}
