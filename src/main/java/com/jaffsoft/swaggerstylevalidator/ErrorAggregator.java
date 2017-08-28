package com.jaffsoft.swaggerstylevalidator;

import io.swagger.models.HttpMethod;

import java.util.ArrayList;
import java.util.List;

class ErrorAggregator {

    private final List<StyleError> errorList = new ArrayList<>();

    public void logMissingOrEmptyOperationAttribute(String fieldNames) {
        errorList.add(new StyleError(StyleError.StyleCheckSection.APIInfo,
                fieldNames,
                "Should be present and not empty"));
    }

    public void validateMinimumInfo(List<Boolean> infoPresence,
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

    public List<StyleError> getErrorList() {
        return errorList;
    }

    public void logMissingOrEmptyOperationAttribute(String path, HttpMethod method, String field) {
        errorList.add(new OperationStyleError(field,
                "This field should be present and not empty",
                path, method));
    }

    public void logMissingOrEmptyOperationCollection(String path, HttpMethod method, String field) {
        errorList.add(new OperationStyleError(field,
                "The collection should be present and there should be at least one item in it",
                path, method));
    }

    public void logMissingOrEmptyModelAttribute(String modelName, String propertyName, String field) {
        errorList.add(new ModelStyleError(
                field,
                "This field should be present and not empty",
                modelName, propertyName));
    }
}
