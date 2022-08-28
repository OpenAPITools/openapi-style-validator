package org.openapitools.openapistylevalidator;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.eclipse.microprofile.openapi.models.OpenAPI;
import org.openapitools.openapistylevalidator.api.IRuleManager;
import org.openapitools.openapistylevalidator.api.Rule;
import org.openapitools.openapistylevalidator.error.StyleError;

public class OpenAPIStyleValidator {

    private final OpenAPI openAPI;

    private final IRuleManager rulesManager;

    public OpenAPIStyleValidator(OpenAPI openAPI, IRuleManager rulesManager) {
        this.openAPI = openAPI;
        this.rulesManager = rulesManager;
    }

    public List<StyleError> validate() {
        List<Rule> rules = rulesManager.loadRules();
        return rules.stream()
                .map(rule -> rule.execute(openAPI))
                .flatMap(List::stream)
                .collect(toList());
    }
}
