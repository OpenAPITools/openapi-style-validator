package org.openapitools.openapistylevalidator.rules;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.openapitools.openapistylevalidator.ErrorMessageHelper.logMissingOrEmptyAttribute;
import static org.openapitools.openapistylevalidator.error.StyleCheckSection.APIInfo;

import java.util.Optional;
import org.eclipse.microprofile.openapi.models.OpenAPI;
import org.openapitools.openapistylevalidator.api.Rule;
import org.openapitools.openapistylevalidator.error.StyleError;

public class InfoDescriptionRule implements Rule {

    public static final String INFO_DESCRIPTION = "info description";

    @Override
    public String ruleName() {
        return INFO_DESCRIPTION;
    }

    @Override
    public String description() {
        return "Description should be provided in the info section";
    }

    @Override
    public Optional<StyleError> execute(OpenAPI api) {
        String description = api.getInfo().getDescription();
        if (isEmpty(description)) {
            return Optional.of(logMissingOrEmptyAttribute(APIInfo, "description"));
        }
        return Optional.empty();
    }
}
