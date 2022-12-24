package org.openapitools.openapistylevalidator.rules;

import org.eclipse.microprofile.openapi.OASFactory;
import org.eclipse.microprofile.openapi.models.OpenAPI;
import org.junit.jupiter.api.Test;
import org.openapitools.openapistylevalidator.api.IRule;
import org.openapitools.openapistylevalidator.error.StyleError;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.openapitools.openapistylevalidator.TestDataProvider.createValidOpenApi;

public class InfoDescriptionRuleTest {

    private final IRule rule = new InfoDescriptionRule();

    @Test
    public void testWithValidOpenAPI() {
        List<StyleError> violation = rule.execute(createValidOpenApi());
        assertTrue(violation.isEmpty());
    }

    @Test
    public void testWithInValidOpenAPI() {
        OpenAPI openapi = OASFactory.createOpenAPI().openapi("3.0.1").info(OASFactory.createInfo().description(""));
        List<StyleError> styleErrors = rule.execute(openapi);
        assertEquals(1, styleErrors.size());
        assertEquals("*ERROR* Section: APIInfo: 'description' -> Should be present and not empty", styleErrors.get(0).toString());
    }
}
