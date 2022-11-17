package org.openapitools.openapistylevalidator.api;

import java.util.List;
import org.eclipse.microprofile.openapi.models.OpenAPI;
import org.openapitools.openapistylevalidator.error.StyleError;

public interface IRule {

    String name();

    String description();

    List<StyleError> execute(OpenAPI api);
}
