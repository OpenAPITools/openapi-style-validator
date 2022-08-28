package org.openapitools.openapistylevalidator.rules;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.eclipse.microprofile.openapi.models.OpenAPI;
import org.eclipse.microprofile.openapi.models.Operation;
import org.eclipse.microprofile.openapi.models.PathItem;
import org.eclipse.microprofile.openapi.models.parameters.Parameter;
import org.openapitools.openapistylevalidator.ErrorMessageHelper;
import org.openapitools.openapistylevalidator.api.Rule;
import org.openapitools.openapistylevalidator.error.StyleError;
import org.openapitools.openapistylevalidator.naming.NamingChecker;

public class HeaderNamingRule implements Rule {

    public static final String HEADER_NAMING = "Header Naming";
    private final NamingChecker checker;

    public HeaderNamingRule(NamingChecker checker) {
        this.checker = checker;
    }

    @Override
    public String ruleName() {
        return HEADER_NAMING;
    }

    @Override
    public String description() {
        return "Rule to validate the header naming convention";
    }

    @Override
    public List<StyleError> execute(OpenAPI openAPI) {
        List<StyleError> errors = new ArrayList<>();
        if (openAPI.getPaths() != null && openAPI.getPaths().getPathItems() != null) {
            for (String key : openAPI.getPaths().getPathItems().keySet()) {
                PathItem path = openAPI.getPaths().getPathItems().get(key);
                for (PathItem.HttpMethod method : path.getOperations().keySet()) {
                    Operation op = path.getOperations().get(method);
                    if (op != null && op.getParameters() != null) {
                        for (Parameter opParam : op.getParameters()) {
                            if (opParam.getRef() == null) {
                                if (opParam.getIn() == Parameter.In.HEADER) {
                                    boolean isNamingConventionMisMatch = checker.getHeaderNamingChecker()
                                            .getPredicate()
                                            .negate()
                                            .test(opParam.getName());
                                    if (isNamingConventionMisMatch) {
                                        StyleError styleError = ErrorMessageHelper.logOperationBadNaming(
                                                opParam.getName(),
                                                "header",
                                                checker.getHeaderNamingChecker().getDesignation(),
                                                key,
                                                method);
                                        errors.add(styleError);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return errors;
    }
}
