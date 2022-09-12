package org.openapitools.openapistylevalidator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openapitools.openapistylevalidator.api.NamingConvention;

class ValidatorParametersTest {

    private ValidatorParameters parameters;

    @BeforeEach
    void initEach() {
        parameters = new ValidatorParameters();
    }

    @Test
    void validateDefaultValues() {
        Assertions.assertAll(
                () -> assertTrue(parameters.isValidateInfoLicense()),
                () -> assertTrue(parameters.isValidateInfoDescription()),
                () -> assertTrue(parameters.isValidateInfoContact()),
                () -> assertTrue(parameters.isValidateOperationOperationId()),
                () -> assertTrue(parameters.isValidateOperationDescription()),
                () -> assertTrue(parameters.isValidateOperationTag()),
                () -> assertTrue(parameters.isValidateOperationSummary()),
                () -> assertTrue(parameters.isValidateModelPropertiesExample()),
                () -> assertTrue(parameters.isValidateModelPropertiesDescription()),
                () -> assertTrue(parameters.isValidateModelRequiredProperties()),
                () -> assertTrue(parameters.isValidateModelNoLocalDef()),
                () -> assertTrue(parameters.isValidateNaming()),
                () -> assertTrue(parameters.isIgnoreHeaderXNaming()),
                () -> assertEquals(NamingConvention.HyphenCase, parameters.getPathNamingConvention()),
                () -> assertEquals(NamingConvention.CamelCase, parameters.getParameterNamingConvention()),
                () -> assertEquals(NamingConvention.UnderscoreUpperCase, parameters.getHeaderNamingConvention()),
                () -> assertEquals(NamingConvention.CamelCase, parameters.getPropertyNamingConvention()),
                () -> assertEquals(NamingConvention.CamelCase, parameters.getQueryParamNamingConvention()),
                () -> assertEquals(NamingConvention.CamelCase, parameters.getPathParamNamingConvention()),
                () -> assertEquals(NamingConvention.CamelCase, parameters.getCookieParamNamingConvention()));
    }

    @Test
    void testAllGetterSetters() {
        parameters = TestDataProvider.createParametersDisablingAllValidations()
                .setIgnoreHeaderXNaming(false)
                .setPathNamingConvention(NamingConvention.CamelCase)
                .setParameterNamingConvention(NamingConvention.CamelCase)
                .setHeaderNamingConvention(NamingConvention.UnderscoreUpperCase)
                .setPropertyNamingConvention(NamingConvention.CamelCase)
                .setQueryParamNamingConvention(NamingConvention.CamelCase)
                .setPathParamNamingConvention(NamingConvention.CamelCase)
                .setCookieParamNamingConvention(NamingConvention.CamelCase);

        Assertions.assertAll(
                () -> assertFalse(parameters.isValidateInfoLicense()),
                () -> assertFalse(parameters.isValidateInfoDescription()),
                () -> assertFalse(parameters.isValidateInfoContact()),
                () -> assertFalse(parameters.isValidateOperationOperationId()),
                () -> assertFalse(parameters.isValidateOperationDescription()),
                () -> assertFalse(parameters.isValidateOperationTag()),
                () -> assertFalse(parameters.isValidateOperationSummary()),
                () -> assertFalse(parameters.isValidateModelPropertiesExample()),
                () -> assertFalse(parameters.isValidateModelPropertiesDescription()),
                () -> assertFalse(parameters.isValidateModelRequiredProperties()),
                () -> assertFalse(parameters.isValidateModelNoLocalDef()),
                () -> assertFalse(parameters.isValidateNaming()),
                () -> assertFalse(parameters.isIgnoreHeaderXNaming()),
                () -> assertEquals(NamingConvention.CamelCase, parameters.getPathNamingConvention()),
                () -> assertEquals(NamingConvention.CamelCase, parameters.getParameterNamingConvention()),
                () -> assertEquals(NamingConvention.UnderscoreUpperCase, parameters.getHeaderNamingConvention()),
                () -> assertEquals(NamingConvention.CamelCase, parameters.getPropertyNamingConvention()),
                () -> assertEquals(NamingConvention.CamelCase, parameters.getQueryParamNamingConvention()),
                () -> assertEquals(NamingConvention.CamelCase, parameters.getPathParamNamingConvention()),
                () -> assertEquals(NamingConvention.CamelCase, parameters.getCookieParamNamingConvention()));
    }
}
