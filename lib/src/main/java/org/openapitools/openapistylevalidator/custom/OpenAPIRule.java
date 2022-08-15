package org.openapitools.openapistylevalidator.custom;

import org.eclipse.microprofile.openapi.models.OpenAPI;
import org.openapitools.openapistylevalidator.ErrorAggregator;

public abstract class OpenAPIRule {

    protected ErrorAggregator errorAggregator;

    public OpenAPIRule(ErrorAggregator errorAggregator) {
        this.errorAggregator = errorAggregator;
    }

    public abstract void execute(OpenAPI api);
}

