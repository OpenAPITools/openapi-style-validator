package org.openapitools.openapistylevalidator.rules;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.openapitools.openapistylevalidator.TestDataProvider.createValidOpenApi;

import java.util.List;
import org.eclipse.microprofile.openapi.OASFactory;
import org.eclipse.microprofile.openapi.models.OpenAPI;
import org.junit.jupiter.api.Test;
import org.openapitools.openapistylevalidator.api.IRule;
import org.openapitools.openapistylevalidator.error.StyleError;

public class ContactInfoRuleTest {

    private final IRule rule = new ContactInfoRule();

    @Test
    public void testWithValidOpenAPI() {
        List<StyleError> violation = rule.execute(createValidOpenApi());
        assertTrue(violation.isEmpty());
    }

    @Test
    public void testWithInValidOpenAPI() {
        OpenAPI openapi = OASFactory.createOpenAPI().openapi("3.0.1").info(OASFactory.createInfo());
        List<StyleError> styleErrors = rule.execute(openapi);
        assertEquals(1, styleErrors.size());
        assertEquals(
                "*ERROR* Section: APIInfo: 'contact' -> Should be present and not empty",
                styleErrors.get(0).toString());
    }

    @Test
    public void shouldValidateMissingContactNameAndUrlAndEmailInfo() {
        OpenAPI openapi = OASFactory.createOpenAPI()
                .openapi("3.0.1")
                .info(OASFactory.createInfo()
                        .license(OASFactory.createLicense())
                        .contact(OASFactory.createContact()));
        List<StyleError> styleErrors = rule.execute(openapi);
        assertEquals(1, styleErrors.size());
        assertEquals(
                "*ERROR* Section: APIInfo: 'contact' -> At least one field should be present and not empty. name|url|email",
                styleErrors.get(0).toString());
    }
}
