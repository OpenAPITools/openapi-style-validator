package org.openapitools.openapistylevalidator.rules;

import static org.openapitools.openapistylevalidator.ErrorMessageHelper.*;

import java.util.Collections;
import java.util.List;
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
    public List<StyleError> execute(OpenAPI api) {
        if (api.getPaths() == null && api.getComponents() == null) {
            return Collections.singletonList(logMissingPathsAndComponents());
        }
        return Collections.emptyList();
    }
}
