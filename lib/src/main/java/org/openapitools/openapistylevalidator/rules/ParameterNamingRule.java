package org.openapitools.openapistylevalidator.rules;

import java.util.Collections;
import java.util.List;
import org.eclipse.microprofile.openapi.models.OpenAPI;
import org.openapitools.openapistylevalidator.api.Rule;
import org.openapitools.openapistylevalidator.error.StyleError;
import org.openapitools.openapistylevalidator.naming.RuleParameterProvider;

public class ParameterNamingRule implements Rule {

    public static final String PARAMETER_NAMING = "parameter naming";
    private final RuleParameterProvider ruleParameterProvider;

    public ParameterNamingRule(RuleParameterProvider ruleParameterProvider) {
        this.ruleParameterProvider = ruleParameterProvider;
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
