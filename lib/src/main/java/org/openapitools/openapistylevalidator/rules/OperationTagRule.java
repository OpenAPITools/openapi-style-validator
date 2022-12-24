package org.openapitools.openapistylevalidator.rules;

import static org.apache.commons.collections4.CollectionUtils.*;
import static org.openapitools.openapistylevalidator.ErrorMessageHelper.logMissingOrEmptyOperationCollection;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.eclipse.microprofile.openapi.models.OpenAPI;
import org.eclipse.microprofile.openapi.models.PathItem;
import org.openapitools.openapistylevalidator.api.IRule;
import org.openapitools.openapistylevalidator.commons.OpenAPIUtils;
import org.openapitools.openapistylevalidator.error.StyleError;

public class OperationTagRule implements IRule {

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
        return pathItemsToValidate.entrySet().stream()
                .map(path -> validateTags(path.getKey(), path.getValue()))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private List<StyleError> validateTags(String path, PathItem pathItem) {
        return OpenAPIUtils.validateOperation(pathItem, op -> isEmpty(op.getTags())).stream()
                .map(method -> logMissingOrEmptyOperationCollection(path, method, "tags"))
                .collect(Collectors.toList());
    }
}
