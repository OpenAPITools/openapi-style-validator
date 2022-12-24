package org.openapitools.openapistylevalidator.commons;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.eclipse.microprofile.openapi.models.OpenAPI;
import org.eclipse.microprofile.openapi.models.Operation;
import org.eclipse.microprofile.openapi.models.PathItem;

public class OpenAPIUtils {

    private OpenAPIUtils() {}

    public static Map<String, PathItem> getPathItemsToValidate(OpenAPI openapi) {
        return openapi.getPaths().getPathItems().entrySet().stream()
                .filter(path -> !OpenAPIUtils.isPathIgnored(path.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private static boolean isPathIgnored(PathItem path) {
        if (path.getExtensions() != null && path.getExtensions().containsKey("x-style-validator-ignored")) {
            Object o = path.getExtensions().getOrDefault("x-style-validator-ignored", "false");
            return Boolean.parseBoolean(o.toString());
        }
        return false;
    }

    public static List<PathItem.HttpMethod> validateOperation(PathItem pathItem, Predicate<Operation> predicate) {
        return pathItem.getOperations().entrySet().stream()
                .filter(entry -> predicate.test(entry.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
