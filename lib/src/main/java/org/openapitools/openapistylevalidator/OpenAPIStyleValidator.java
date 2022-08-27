package org.openapitools.openapistylevalidator;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;
import org.eclipse.microprofile.openapi.models.OpenAPI;
import org.openapitools.openapistylevalidator.api.Rule;
import org.openapitools.openapistylevalidator.error.StyleError;

public class OpenAPIStyleValidator {

    private final OpenAPI openAPI;

    private final ValidatorParameters validatorParameters;

    private final RulesManager rulesManager;

    public OpenAPIStyleValidator(OpenAPI openAPI, ValidatorParameters validatorParameters) {
        this.openAPI = openAPI;
        this.validatorParameters = validatorParameters;
        this.rulesManager = new RulesManager();
    }

    public List<StyleError> validate() {
        List<Rule> rules = rulesManager.loadAllRules();
        List<String> ignoredRules = rulesManager.convertToIgnoredRules(validatorParameters);
        List<Rule> rulesToExecute = rules.stream()
                .filter(rule -> !ignoredRules.contains(rule.ruleName()))
                .collect(toList());
        return rulesToExecute.stream()
                .map(rule -> rule.execute(openAPI))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(toList());
    }
}
