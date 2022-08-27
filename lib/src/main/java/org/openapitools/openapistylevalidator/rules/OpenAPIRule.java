package org.openapitools.openapistylevalidator.rules;

import static org.openapitools.openapistylevalidator.ErrorMessageHelper.*;

import java.util.Optional;
import org.eclipse.microprofile.openapi.models.OpenAPI;
import org.openapitools.openapistylevalidator.api.Rule;
import org.openapitools.openapistylevalidator.error.StyleError;

public class OpenAPIRule implements Rule {

    public static final String OPENAPI = "openapi";

    @Override
    public String ruleName() {
        return OPENAPI;
    }

    @Override
    public String description() {
        return "Rule for validating openAPI has at least one component and one path";
    }

    @Override
    public Optional<StyleError> execute(OpenAPI api) {
        if (api.getPaths() == null && api.getComponents() == null) {
            return Optional.of(logMissingPathsAndComponents());
        }
        return Optional.empty();
    }
}
