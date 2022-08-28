package org.openapitools.openapistylevalidator.rules;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.eclipse.microprofile.openapi.models.OpenAPI;
import org.openapitools.openapistylevalidator.api.Rule;
import org.openapitools.openapistylevalidator.error.StyleError;
import org.openapitools.openapistylevalidator.naming.NamingChecker;

public class ParameterNamingRule implements Rule {

    public static final String PARAMETER_NAMING = "parameter naming";
    private final NamingChecker namingChecker;

    public ParameterNamingRule(NamingChecker namingChecker) {
        this.namingChecker = namingChecker;
    }

    @Override
    public String ruleName() {
        return PARAMETER_NAMING;
    }

    @Override
    public String description() {
        return "Rule to validate Parameter naming convention";
    }

    @Override
    public List<StyleError> execute(OpenAPI api) {
        // TODO actual implementation
        return Collections.emptyList();
    }
}
