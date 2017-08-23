package com.jaffsoft.swaggerstylevalidator;

import io.swagger.models.HttpMethod;
import io.swagger.models.Operation;
import io.swagger.models.Path;
import io.swagger.models.properties.Property;

import java.util.ArrayList;
import java.util.List;

public class ErrorAggregator {

    List<StyleError> errorList = new ArrayList<>();

    public void logMissingOrEmptyOperationAttribute(String parentObjectName, String fieldNames) {
        errorList.add(new StyleError(StyleError.StyleCheckSection.APIInfo,
                parentObjectName,
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
            errorList.add(new StyleError(styleCheckSection,
                    parentObjectName,
                    fieldNames,
                    "At least one field should be present and not empty"));
        }
    }

    public List<StyleError> getErrorList() {
        return errorList;
    }

    public void logMissingOrEmptyOperationAttribute(StyleError.StyleCheckSection styleCheckSection, String path, HttpMethod method, String field) {
        errorList.add(new OperationStyleError(styleCheckSection,
                "",
                field,
                "This field should be present and not empty",
                path, method));
    }

    public void logMissingOrEmptyOperationCollection(StyleError.StyleCheckSection styleCheckSection, String path, HttpMethod method, String field) {
        errorList.add(new OperationStyleError(styleCheckSection,
                "",
                field,
                "The collection should be present and there should be at least one item in it",
                path, method));
    }

    public void logMissingOrEmptyModelAttribute(StyleError.StyleCheckSection styleCheckSection, String modelName, String propertyName, String field) {
        errorList.add(new ModelStyleError(styleCheckSection,
                "",
                field,
                "This field should be present and not empty",
                modelName, propertyName));
    }
}
