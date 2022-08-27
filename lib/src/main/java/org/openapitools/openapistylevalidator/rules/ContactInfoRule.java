package org.openapitools.openapistylevalidator.rules;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import java.util.Optional;
import org.eclipse.microprofile.openapi.models.OpenAPI;
import org.eclipse.microprofile.openapi.models.info.Contact;
import org.openapitools.openapistylevalidator.api.Rule;
import org.openapitools.openapistylevalidator.error.StyleCheckSection;
import org.openapitools.openapistylevalidator.error.StyleError;

public class ContactInfoRule implements Rule {

    public static final String CONTACT_INFO = "contact info";

    @Override
    public String ruleName() {
        return CONTACT_INFO;
    }

    @Override
    public String description() {
        return "At least one field should be present and not empty. name|url|email";
    }

    @Override
    public Optional<StyleError> execute(OpenAPI api) {
        Contact contact = api.getInfo().getContact();
        if (contact == null) {
            StyleError styleError =
                    new StyleError(StyleCheckSection.APIInfo, "contact", "Should be present and not empty");
            return Optional.of(styleError);
        }
        if (isEmpty(contact.getName()) && isEmpty(contact.getUrl()) && isNotEmpty(contact.getEmail())) {
            StyleError styleError = new StyleError(
                    StyleCheckSection.APIInfo,
                    "contact",
                    "At least one field should be present and not empty. name|url|email");
            return Optional.of(styleError);
        }
        return Optional.empty();
    }
}
