package com.jaffsoft.swaggerstylevalidator;

import com.jaffsoft.swaggerstylevalidator.styleerror.StyleError;
import io.swagger.models.*;
import io.swagger.models.properties.Property;
import io.swagger.models.parameters.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class OpenApiSpecStyleValidator {

    private final Swagger swagger;
    private final ErrorAggregator errorAggregator;
    private ValidatorParameters parameters;
    private final NamingValidator namingValidator;

    OpenApiSpecStyleValidator(Swagger swagger) {
        this.swagger = swagger;
        errorAggregator = new ErrorAggregator();
        namingValidator = new NamingValidator();
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
                errorAggregator.logMissingOrEmptyAttribute(StyleError.StyleCheckSection.APIInfo,"license");
            }
        }

        if (parameters.isValidateInfoDescription()) {
            String description = info.getDescription();
            if (description == null || description.isEmpty()) {
                errorAggregator.logMissingOrEmptyAttribute(StyleError.StyleCheckSection.APIInfo,"description");
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
                errorAggregator.logMissingOrEmptyAttribute(StyleError.StyleCheckSection.APIInfo,"contact");
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
                        errorAggregator.logMissingOrEmptyOperationAttribute(key, method, "operationId");
                    }
                }

                if (parameters.isValidateOperationDescription()) {
                    if (op.getDescription() == null || op.getDescription().isEmpty()) {
                        errorAggregator.logMissingOrEmptyOperationAttribute(key, method, "description");
                    }
                }

                if (parameters.isValidateOperationSummary()) {
                    if (op.getSummary() == null || op.getSummary().isEmpty()) {
                        errorAggregator.logMissingOrEmptyOperationAttribute(key, method, "summary");
                    }
                }

                if (parameters.isValidateOperationTag()) {
                    if (op.getTags() == null || op.getTags().isEmpty()) {
                        errorAggregator.logMissingOrEmptyOperationCollection(key, method, "tags");
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
                        errorAggregator.logMissingOrEmptyModelAttribute(definition, entry.getKey(), "example");
                    }
                }

                /*
                if (parameters.isValidateModelNoLocalDef()) {
                    //TODO:
                }*/
            }
        }
    }

    private void validateNaming() {
        if (parameters.isValidateNaming()) {
            for (String definition : swagger.getDefinitions().keySet()) {
                Model model = swagger.getDefinitions().get(definition);

                for (Map.Entry<String, Property> entry : model.getProperties().entrySet()) {
                    Property property = entry.getValue();
                    boolean isValid = namingValidator.isNamingValid(entry.getKey(), parameters.getPropertyNamingStrategy());
                    if (!isValid) {
                        errorAggregator.logModelBadNaming(property.getName(),
                                "property",
                                parameters.getPropertyNamingStrategy().getAppelation(),
                                entry.getKey());
                    }
                }
            }

            for (String key : swagger.getPaths().keySet()) {
                Path path = swagger.getPath(key);
                for (HttpMethod method : path.getOperationMap().keySet()) {
                    Operation op = path.getOperationMap().get(method);
                    for (Parameter opParam : op.getParameters()) {
                        boolean isValid = namingValidator.isNamingValid(opParam.getName(), parameters.getParameterNamingStrategy());
                        if (!isValid) {
                            errorAggregator.logOperationBadNaming(opParam.getName(),
                                    "parameter",
                                    parameters.getParameterNamingStrategy().getAppelation(),
                                    key,
                                    method);
                        }
                    }
                }

                String[] pathParts = key.split("/");
                for (String part : pathParts) {
                    if (!(part.startsWith("{") && part.endsWith("}"))) {
                        boolean isValid = namingValidator.isNamingValid("part", parameters.getPathNamingStrategy());
                        if (!isValid) {
                            errorAggregator.logOperationBadNaming(part,
                                    "path",
                                    parameters.getPathNamingStrategy().getAppelation(),
                                    key,
                                    null);
                        }
                    }
                }
            }
        }
    }
}