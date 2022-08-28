package org.openapitools.openapistylevalidator.rules;

import java.util.Collections;
import java.util.List;
import org.eclipse.microprofile.openapi.models.OpenAPI;
import org.openapitools.openapistylevalidator.api.Rule;
import org.openapitools.openapistylevalidator.error.StyleError;
import org.openapitools.openapistylevalidator.naming.RuleParameterProvider;

public class PathNamingRule implements Rule {

    public static final String PATH_NAMING = "path naming";
    private final RuleParameterProvider ruleParameterProvider;

    public PathNamingRule(RuleParameterProvider ruleParameterProvider) {
        this.ruleParameterProvider = ruleParameterProvider;
    }

    @Override
    public String ruleName() {
        return PATH_NAMING;
    }

    @Override
    public String description() {
        return "Rule to validate path parameter naming convention";
    }

    @Override
    public List<StyleError> execute(OpenAPI api) {
        // TODO actual implementation
        return Collections.emptyList();
    }
}
