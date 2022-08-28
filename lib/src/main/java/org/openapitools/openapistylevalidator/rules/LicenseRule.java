package org.openapitools.openapistylevalidator.rules;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.openapitools.openapistylevalidator.ErrorMessageHelper.logMissingOrEmptyAttribute;
import static org.openapitools.openapistylevalidator.ErrorMessageHelper.validateMinimumInfo;
import static org.openapitools.openapistylevalidator.error.StyleCheckSection.APIInfo;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.eclipse.microprofile.openapi.models.OpenAPI;
import org.eclipse.microprofile.openapi.models.info.Info;
import org.eclipse.microprofile.openapi.models.info.License;
import org.openapitools.openapistylevalidator.api.Rule;
import org.openapitools.openapistylevalidator.error.StyleError;

public class LicenseRule implements Rule {

    public static final String LICENCE_INFO = "licence info";

    @Override
    public String ruleName() {
        return LICENCE_INFO;
    }

    @Override
    public String description() {
        return "at least name or url should be present in the license section";
    }

    @Override
    public List<StyleError> execute(OpenAPI api) {
        Info info = api.getInfo();
        License license = info.getLicense();
        if (license == null) {
            return Collections.singletonList(logMissingOrEmptyAttribute(APIInfo, "license"));
        }

        if (isEmpty(license.getName()) && isEmpty(license.getUrl())) {
            return Collections.singletonList(validateMinimumInfo(APIInfo, "license", "name|url"));
        }
        return Collections.emptyList();
    }
}
