package org.openapitools.openapistylevalidator;

import org.eclipse.microprofile.openapi.models.PathItem;
import org.openapitools.openapistylevalidator.error.GenericStyleError;
import org.openapitools.openapistylevalidator.error.OperationNamingStyleError;
import org.openapitools.openapistylevalidator.error.OperationStyleError;
import org.openapitools.openapistylevalidator.error.StyleCheckSection;
import org.openapitools.openapistylevalidator.error.StyleError;

public class ErrorMessageHelper {

    private ErrorMessageHelper() {}

    public static StyleError logMissingOrEmptyAttribute(StyleCheckSection styleCheckSection, String fieldNames) {
        return new StyleError(styleCheckSection, fieldNames, "Should be present and not empty");
    }

    public static StyleError logMissingPathsAndComponents() {
        return new StyleError(
                StyleCheckSection.OpenAPI, "paths,components", "Should have at least one of paths or components");
    }

    public static StyleError validateMinimumInfo(
            StyleCheckSection styleCheckSection, String parentObjectName, String fieldNames) {

        return new GenericStyleError(
                styleCheckSection, parentObjectName, fieldNames, "At least one field should be present and not empty");
    }

    public static StyleError logMissingOrEmptyOperationCollection(
            String path, PathItem.HttpMethod method, String field) {
        return new OperationStyleError(
                field, "The collection should be present and there should be at least one item in it", path, method);
    }

    public static StyleError logMissingOrEmptyOperationAttribute(
            String path, PathItem.HttpMethod method, String field) {
        return new OperationStyleError(field, "This field should be present and not empty", path, method);
    }

    public static StyleError logOperationBadNaming(
            String variableName,
            String variableType,
            String neededNamingStrategy,
            String path,
            PathItem.HttpMethod httpMethod) {
        return new OperationNamingStyleError(
                StyleCheckSection.Naming,
                variableName,
                String.format("%s should be in %s", variableType, neededNamingStrategy),
                path,
                httpMethod);
    }
}
