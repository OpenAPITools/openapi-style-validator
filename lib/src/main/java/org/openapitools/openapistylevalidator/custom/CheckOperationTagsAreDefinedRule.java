package org.openapitools.openapistylevalidator.custom;

import org.eclipse.microprofile.openapi.models.OpenAPI;
import org.eclipse.microprofile.openapi.models.Operation;
import org.eclipse.microprofile.openapi.models.PathItem;
import org.eclipse.microprofile.openapi.models.tags.Tag;
import org.openapitools.openapistylevalidator.ErrorAggregator;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This rule will check all the tags used in the operations is defined under tag section
 */
public class CheckOperationTagsAreDefinedRule extends OpenAPIRule {

    public CheckOperationTagsAreDefinedRule(ErrorAggregator errorAggregator) {
        super(errorAggregator);
    }

    @Override
    public void execute(OpenAPI openAPI) {
        Set<String> definedTags = getDefinedTags(openAPI);
        for (String key : openAPI.getPaths().getPathItems().keySet()) {
            PathItem path = openAPI.getPaths().getPathItems().get(key);
            for (PathItem.HttpMethod method : path.getOperations().keySet()) {
                Operation op = path.getOperations().get(method);
                if (op.getTags() != null && !op.getTags().isEmpty()) {
                    op.getTags()
                            .stream()
                            .filter(tag -> !definedTags.contains(tag))
                            .forEach(errorAggregator::logUndefinedTag);
                }
            }
        }
    }

    private Set<String> getDefinedTags(OpenAPI openAPI) {
        if (openAPI.getTags() != null && !openAPI.getTags().isEmpty()) {
            return openAPI.getTags().stream().map(Tag::getName).collect(Collectors.toSet());
        }
        return Collections.EMPTY_SET;
    }
}
