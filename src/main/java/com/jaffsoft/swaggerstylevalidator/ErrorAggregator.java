package com.jaffsoft.swaggerstylevalidator;

import com.jaffsoft.swaggerstylevalidator.styleerror.*;
import io.swagger.models.HttpMethod;

import java.util.ArrayList;
import java.util.List;

class ErrorAggregator {

    private final List<StyleError> errorList = new ArrayList<>();

    void logMissingOrEmptyOperationAttribute(String fieldNames) {
        errorList.add(new StyleError(StyleError.StyleCheckSection.APIInfo,
                fieldNames,
                "Should be present and not empty"));
    }

    void validateMinimumInfo(List<Boolean> infoPresence,
                                     StyleError.StyleCheckSection styleCheckSection,
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
            errorList.add(new GenericStyleError(styleCheckSection,
                    parentObjectName,
                    fieldNames,
                    "At least one field should be present and not empty"));
        }
    }

    List<StyleError> getErrorList() {
        return errorList;
    }

    void logMissingOrEmptyOperationAttribute(String path, HttpMethod method, String field) {
        errorList.add(new OperationStyleError(field,
                "This field should be present and not empty",
                path, method));
    }

    void logMissingOrEmptyOperationCollection(String path, HttpMethod method, String field) {
        errorList.add(new OperationStyleError(field,
                "The collection should be present and there should be at least one item in it",
                path, method));
    }

    void logMissingOrEmptyModelAttribute(String modelName, String propertyName, String field) {
        errorList.add(new ModelStyleError(
                field,
                "This field should be present and not empty",
                modelName, propertyName));
    }

    void logBadNaming(String variableName, String variableType, String neededNamingStrategy, String path, HttpMethod httpMethod) {
        errorList.add(new NamingStyleError(StyleError.StyleCheckSection.Naming, variableName,
                String.format("%s should be in %s", variableType, neededNamingStrategy), path, httpMethod));
    }
}
