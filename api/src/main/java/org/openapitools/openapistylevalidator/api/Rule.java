package org.openapitools.openapistylevalidator.api;

import java.util.Optional;
import org.eclipse.microprofile.openapi.models.OpenAPI;
import org.openapitools.openapistylevalidator.error.StyleError;

public interface Rule {

    String ruleName();

    String description();

    Optional<StyleError> execute(OpenAPI api);
}
