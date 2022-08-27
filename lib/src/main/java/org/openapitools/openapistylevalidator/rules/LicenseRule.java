package org.openapitools.openapistylevalidator.rules;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.openapitools.openapistylevalidator.ErrorMessageHelper.logMissingOrEmptyAttribute;
import static org.openapitools.openapistylevalidator.ErrorMessageHelper.validateMinimumInfo;
import static org.openapitools.openapistylevalidator.error.StyleCheckSection.APIInfo;

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
    public Optional<StyleError> execute(OpenAPI api) {
        Info info = api.getInfo();
        License license = info.getLicense();
        if (license == null) {
            return Optional.of(logMissingOrEmptyAttribute(APIInfo, "license"));
        }

        if (isEmpty(license.getName()) && isEmpty(license.getUrl())) {
            return Optional.of(validateMinimumInfo(APIInfo, "license", "name|url"));
        }
        return Optional.empty();
    }
}
