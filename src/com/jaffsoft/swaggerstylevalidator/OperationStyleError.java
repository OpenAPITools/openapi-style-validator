package com.jaffsoft.swaggerstylevalidator;

import io.swagger.models.HttpMethod;
import io.swagger.models.Path;

public class OperationStyleError extends StyleError {

    private final String path;
    private final HttpMethod method;

    public OperationStyleError(StyleCheckSection styleCheckSection,
                               String parentObjectName,
                               String fieldNames,
                               String description,
                               String path,
                               HttpMethod method) {
        super(styleCheckSection, parentObjectName, fieldNames, description);
        this.path = path;
        this.method = method;

    }

    @Override
    public String toString() {
        return String.format("*ERROR* in Operation %s %s '%s' %s -> %s",
                method.name(),
                path,
                fieldNames,
                parentObjectName.isEmpty() ? "" : String.format("in %s", parentObjectName),
                description);
    }
}
