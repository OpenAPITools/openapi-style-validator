package com.jaffsoft.openapistylevalidator.styleerror;

import io.swagger.models.HttpMethod;

public class OperationStyleError extends StyleError {

    private final String path;
    private final HttpMethod method;

    public OperationStyleError(String fieldNames,
                               String description,
                               String path,
                               HttpMethod method) {
        super(StyleCheckSection.Operations, fieldNames, description);
        this.path = path;
        this.method = method;

    }

    @Override
    public String toString() {
        return String.format("*ERROR* in Operation %s %s '%s' -> %s",
                method.name(),
                path,
                fieldNames,
                description);
    }
}
