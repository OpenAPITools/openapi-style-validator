package org.openapitools.openapistylevalidator.rules;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.openapitools.openapistylevalidator.ErrorMessageHelper.logMissingOrEmptyAttribute;
import static org.openapitools.openapistylevalidator.error.StyleCheckSection.APIInfo;

import java.util.Collections;
import java.util.List;
import org.eclipse.microprofile.openapi.models.OpenAPI;
import org.openapitools.openapistylevalidator.api.IRule;
import org.openapitools.openapistylevalidator.error.StyleError;

public class InfoDescriptionRule implements IRule {

    public static final String INFO_DESCRIPTION = "info description";

    @Override
    public String name() {
        return INFO_DESCRIPTION;
    }

    @Override
    public String description() {
        return "Description should be provided in the info section";
    }

    @Override
    public List<StyleError> execute(OpenAPI api) {
        if (isEmpty(api.getInfo().getDescription())) {
            return Collections.singletonList(logMissingOrEmptyAttribute(APIInfo, "description"));
        }
        return Collections.emptyList();
    }
}
