package org.openapitools.openapistylevalidator.rules;

import org.eclipse.microprofile.openapi.models.OpenAPI;
import org.openapitools.openapistylevalidator.api.IRule;
import org.openapitools.openapistylevalidator.error.StyleError;

import java.util.Collections;
import java.util.List;

import static org.openapitools.openapistylevalidator.ErrorMessageHelper.logMissingPathsAndComponents;

public class OpenAPIRule implements IRule {

    public static final String OPENAPI = "openapi";

    @Override
    public String name() {
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
