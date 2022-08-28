package org.openapitools.openapistylevalidator.rules;

import java.util.Collections;
import java.util.List;
import org.eclipse.microprofile.openapi.models.OpenAPI;
import org.openapitools.openapistylevalidator.api.Rule;
import org.openapitools.openapistylevalidator.error.StyleError;
import org.openapitools.openapistylevalidator.naming.RuleParameterProvider;

public class PropertyNamingRule implements Rule {

    public static final String PROPERTY_NAMING = "property Naming";
    private final RuleParameterProvider ruleParameterProvider;

    public PropertyNamingRule(RuleParameterProvider ruleParameterProvider) {
        this.ruleParameterProvider = ruleParameterProvider;
    }

    @Override
    public String ruleName() {
        return PROPERTY_NAMING;
    }

    @Override
    public String description() {
        return "Rule to validate model naming as per the convention";
    }

    @Override
    public List<StyleError> execute(OpenAPI api) {
        // TODO actual implementation
        return Collections.emptyList();
    }
}
