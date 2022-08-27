package org.openapitools.openapistylevalidator;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.microprofile.openapi.models.PathItem;
import org.openapitools.openapistylevalidator.error.GenericStyleError;
import org.openapitools.openapistylevalidator.error.ModelNamingStyleError;
import org.openapitools.openapistylevalidator.error.ModelStyleError;
import org.openapitools.openapistylevalidator.error.OperationNamingStyleError;
import org.openapitools.openapistylevalidator.error.OperationStyleError;
import org.openapitools.openapistylevalidator.error.StyleCheckSection;
import org.openapitools.openapistylevalidator.error.StyleError;

public class ErrorAggregator {

    private final List<StyleError> errorList = new ArrayList<>();

    public void logMissingOrEmptyAttribute(StyleCheckSection styleCheckSection, String fieldNames) {
        errorList.add(new StyleError(styleCheckSection, fieldNames, "Should be present and not empty"));
    }

    public void validateMinimumInfo(
            List<Boolean> infoPresence,
            StyleCheckSection styleCheckSection,
            String parentObjectName,
            String fieldNames) {
        boolean hasMinimumInfo = false;
        for (Boolean presence : infoPresence) {
            if (presence) {
                hasMinimumInfo = true;
                break;
            }
        }

        if (!hasMinimumInfo) {
            errorList.add(new GenericStyleError(
                    styleCheckSection,
                    parentObjectName,
                    fieldNames,
                    "At least one field should be present and not empty"));
        }
    }

    List<StyleError> getErrorList() {
        return errorList;
    }

    public void logMissingOrEmptyOperationAttribute(String path, PathItem.HttpMethod method, String field) {
        errorList.add(new OperationStyleError(field, "This field should be present and not empty", path, method));
    }

    public void logMissingOrEmptyOperationCollection(String path, PathItem.HttpMethod method, String field) {
        errorList.add(new OperationStyleError(
                field, "The collection should be present and there should be at least one item in it", path, method));
    }

    public void logMissingOrEmptyModelAttribute(String modelName, String propertyName, String field) {
        errorList.add(
                new ModelStyleError(field, "This field should be present and not empty", modelName, propertyName));
    }

    public void logMissingModelProperty(String modelName, String propertyName) {
        errorList.add(new ModelStyleError(
                null, "This property should be present or removed from the list of required", modelName, propertyName));
    }

    public void logOperationBadNaming(
            String variableName,
            String variableType,
            String neededNamingStrategy,
            String path,
            PathItem.HttpMethod httpMethod) {
        errorList.add(new OperationNamingStyleError(
                StyleCheckSection.Naming,
                variableName,
                String.format("%s should be in %s", variableType, neededNamingStrategy),
                path,
                httpMethod));
    }

    public void logModelBadNaming(String variableName, String variableType, String neededNamingStrategy, String model) {
        errorList.add(new ModelNamingStyleError(
                StyleCheckSection.Naming,
                variableName,
                String.format("%s should be in %s", variableType, neededNamingStrategy),
                model));
    }

    public void logMissingPathsAndComponents() {
        errorList.add(new StyleError(
                StyleCheckSection.OpenAPI, "paths,components", "Should have at least one of paths or components"));
    }
}
