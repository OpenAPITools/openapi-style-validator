package org.openapitools.openapistylevalidator.rules;

import java.util.Optional;
import org.eclipse.microprofile.openapi.models.OpenAPI;
import org.openapitools.openapistylevalidator.api.Rule;
import org.openapitools.openapistylevalidator.error.StyleError;
import org.openapitools.openapistylevalidator.naming.NamingChecker;

public class PropertyNamingRule implements Rule {

    public static final String PROPERTY_NAMING = "property Naming";
    private final NamingChecker namingChecker;

    public PropertyNamingRule(NamingChecker namingChecker) {
        this.namingChecker = namingChecker;
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
    public Optional<StyleError> execute(OpenAPI api) {
        // TODO actual implementation
        return Optional.empty();
    }
}
