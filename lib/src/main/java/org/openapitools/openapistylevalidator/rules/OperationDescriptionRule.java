package org.openapitools.openapistylevalidator.rules;

import static org.openapitools.openapistylevalidator.ErrorMessageHelper.logMissingOrEmptyOperationAttribute;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.openapi.models.OpenAPI;
import org.eclipse.microprofile.openapi.models.Operation;
import org.eclipse.microprofile.openapi.models.PathItem;
import org.openapitools.openapistylevalidator.api.IRule;
import org.openapitools.openapistylevalidator.commons.OpenAPIUtils;
import org.openapitools.openapistylevalidator.error.StyleError;

public class OperationDescriptionRule implements IRule {

    private final Predicate<Operation> predicate = operation -> StringUtils.isEmpty(operation.getDescription());

    @Override
    public String name() {
        return "Operation Description";
    }

    @Override
    public String description() {
        return "Operation Description should be present";
    }

    @Override
    public List<StyleError> execute(OpenAPI api) {
        Map<String, PathItem> pathItemsToValidate = OpenAPIUtils.getPathItemsToValidate(api);
        return pathItemsToValidate.entrySet().stream()
                .map(path -> validateTags(path.getKey(), path.getValue()))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private List<StyleError> validateTags(String path, PathItem pathItem) {
        return OpenAPIUtils.validateOperation(pathItem, predicate).stream()
                .map(method -> logMissingOrEmptyOperationAttribute(path, method, "description"))
                .collect(Collectors.toList());
    }
}
