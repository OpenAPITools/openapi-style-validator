package org.openapitools.openapistylevalidator.rules;

import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.microprofile.openapi.models.OpenAPI;
import org.eclipse.microprofile.openapi.models.Operation;
import org.eclipse.microprofile.openapi.models.PathItem;
import org.openapitools.openapistylevalidator.api.IRule;
import org.openapitools.openapistylevalidator.commons.OpenAPIUtils;
import org.openapitools.openapistylevalidator.error.StyleError;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.openapitools.openapistylevalidator.ErrorMessageHelper.logMissingOrEmptyOperationCollection;

public class OperationTagRule implements IRule {

   private final Predicate<Operation> predicate = operation -> CollectionUtils.isEmpty(operation.getTags());

    @Override
    public String name() {
        return "Operation ID";
    }

    @Override
    public String description() {
        return "Operation ID should be present";
    }

    @Override
    public List<StyleError> execute(OpenAPI api) {
        Map<String, PathItem> pathItemsToValidate = OpenAPIUtils.getPathItemsToValidate(api);
        return pathItemsToValidate.entrySet()
                .stream()
                .map(path -> validateTags(path.getKey(), path.getValue()))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private List<StyleError> validateTags(String path, PathItem pathItem) {
        return OpenAPIUtils.validateOperation(pathItem, predicate)
                .stream()
                .map(method -> logMissingOrEmptyOperationCollection(path, method, "tags"))
                .collect(Collectors.toList());
    }
}
