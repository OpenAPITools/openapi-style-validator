package com.jaffsoft.swaggerstylevalidator;


import io.swagger.models.*;
import io.swagger.models.properties.Property;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

//TODO: Add JUnit + mockito
//TODO: Create a test and make sure the folder is ok
class OpenApiSpecStyleValidator {

    private final Swagger swagger;
    private final ErrorAggregator errorAggregator;
    private ValidatorParameters parameters;

    OpenApiSpecStyleValidator(Swagger swagger) {
        this.swagger = swagger;
        errorAggregator = new ErrorAggregator();
    }

    List<StyleError> validate(ValidatorParameters parameters) {
        this.parameters = parameters;
        validateInfo();
        validateOperations();
        validateModels();
        validateNaming();

        return errorAggregator.getErrorList();
    }

    private void validateInfo() {
        Info info = swagger.getInfo();
        License license = info.getLicense();
        if (parameters.isValidateInfoLicense()) {
            if (license != null) {
                List<Boolean> infoPresence = new ArrayList<>();
                infoPresence.add(license.getName() != null && !license.getName().isEmpty());
                infoPresence.add(license.getUrl() != null && !license.getUrl().isEmpty());
                errorAggregator.validateMinimumInfo(infoPresence, StyleError.StyleCheckSection.APIInfo, "license", "name|url");
            } else {
                errorAggregator.logMissingOrEmptyOperationAttribute("", "license");
            }
        }

        if (parameters.isValidateInfoDescription()) {
            String description = info.getDescription();
            if (description == null || description.isEmpty()) {
                errorAggregator.logMissingOrEmptyOperationAttribute("", "description");
            }
        }

        if (parameters.isValidateInfoContact()) {
            Contact contact = info.getContact();
            if (contact != null) {
                List<Boolean> infoPresence = new ArrayList<>();
                infoPresence.add(contact.getName() != null && !contact.getName().isEmpty());
                infoPresence.add(contact.getUrl() != null && !contact.getUrl().isEmpty());
                infoPresence.add(contact.getEmail() != null && !contact.getEmail().isEmpty());
                errorAggregator.validateMinimumInfo(infoPresence, StyleError.StyleCheckSection.APIInfo, "contact", "name|url|email");
            } else {
                errorAggregator.logMissingOrEmptyOperationAttribute("", "contact");
            }
        }
    }

    private void validateOperations() {
        for (String key :swagger.getPaths().keySet()) {
            Path path = swagger.getPath(key);
            for (HttpMethod method : path.getOperationMap().keySet()) {
                Operation op = path.getOperationMap().get(method);
                if (parameters.isValidateOperationOperationId()) {
                    if (op.getOperationId() == null || op.getOperationId().isEmpty()) {
                        errorAggregator.logMissingOrEmptyOperationAttribute(StyleError.StyleCheckSection.Operations, key, method, "operationId");
                    }
                }

                if (parameters.isValidateOperationDescription()) {
                    if (op.getDescription() == null || op.getDescription().isEmpty()) {
                        errorAggregator.logMissingOrEmptyOperationAttribute(StyleError.StyleCheckSection.Operations, key, method, "description");
                    }
                }

                if (parameters.isValidateOperationSummary()) {
                    if (op.getSummary() == null || op.getSummary().isEmpty()) {
                        errorAggregator.logMissingOrEmptyOperationAttribute(StyleError.StyleCheckSection.Operations, key, method, "summary");
                    }
                }

                if (parameters.isValidateOperationTag()) {
                    if (op.getTags() == null || op.getTags().isEmpty()) {
                        errorAggregator.logMissingOrEmptyOperationCollection(StyleError.StyleCheckSection.Operations, key, method, "tags");
                    }
                }
            }
        }
    }

    private void validateModels() {
        for (String definition : swagger.getDefinitions().keySet()) {
            Model model = swagger.getDefinitions().get(definition);

            for (Map.Entry<String, Property> entry : model.getProperties().entrySet()) {
                Property property = entry.getValue();

                if (parameters.isValidateModelPropertiesExample()) {
                    if (property.getExample() == null) {
                        errorAggregator.logMissingOrEmptyModelAttribute(StyleError.StyleCheckSection.Models, definition, entry.getKey(), "example");
                    }
                }

                if (parameters.isValidateModelNoLocalDef()) {

                }
            }
        }
    }

    private void validateNaming() {

    }
}