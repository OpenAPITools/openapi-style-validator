package org.openapitools.openapistylevalidator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
                () -> assertEquals(
                        ValidatorParameters.NamingConvention.HyphenCase, parameters.getPathNamingConvention()),
                () -> assertEquals(
                        ValidatorParameters.NamingConvention.CamelCase, parameters.getParameterNamingConvention()),
                () -> assertEquals(
                        ValidatorParameters.NamingConvention.UnderscoreUpperCase,
                        parameters.getHeaderNamingConvention()),
                () -> assertEquals(
                        ValidatorParameters.NamingConvention.PascalCase, parameters.getSchemaNamingConvention()),
                () -> assertEquals(
                        ValidatorParameters.NamingConvention.CamelCase, parameters.getPropertyNamingConvention()),
                () -> assertEquals(
                        ValidatorParameters.NamingConvention.CamelCase, parameters.getQueryParamNamingConvention()),
                () -> assertEquals(
                        ValidatorParameters.NamingConvention.CamelCase, parameters.getPathParamNamingConvention()),
                () -> assertEquals(
                        ValidatorParameters.NamingConvention.CamelCase, parameters.getCookieParamNamingConvention()));
    }

    @Test
    void testAllGetterSetters() {
        parameters = TestDataProvider.createParametersDisablingAllValidations()
                .setIgnoreHeaderXNaming(false)
                .setPathNamingConvention(ValidatorParameters.NamingConvention.CamelCase)
                .setParameterNamingConvention(ValidatorParameters.NamingConvention.CamelCase)
                .setHeaderNamingConvention(ValidatorParameters.NamingConvention.UnderscoreUpperCase)
                .setSchemaNamingConvention(ValidatorParameters.NamingConvention.PascalCase)
                .setPropertyNamingConvention(ValidatorParameters.NamingConvention.CamelCase)
                .setQueryParamNamingConvention(ValidatorParameters.NamingConvention.CamelCase)
                .setPathParamNamingConvention(ValidatorParameters.NamingConvention.CamelCase)
                .setCookieParamNamingConvention(ValidatorParameters.NamingConvention.CamelCase);

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
                () -> assertEquals(
                        ValidatorParameters.NamingConvention.CamelCase, parameters.getPathNamingConvention()),
                () -> assertEquals(
                        ValidatorParameters.NamingConvention.CamelCase, parameters.getParameterNamingConvention()),
                () -> assertEquals(
                        ValidatorParameters.NamingConvention.UnderscoreUpperCase,
                        parameters.getHeaderNamingConvention()),
                () -> assertEquals(
                        ValidatorParameters.NamingConvention.PascalCase, parameters.getSchemaNamingConvention()),
                () -> assertEquals(
                        ValidatorParameters.NamingConvention.CamelCase, parameters.getPropertyNamingConvention()),
                () -> assertEquals(
                        ValidatorParameters.NamingConvention.CamelCase, parameters.getQueryParamNamingConvention()),
                () -> assertEquals(
                        ValidatorParameters.NamingConvention.CamelCase, parameters.getPathParamNamingConvention()),
                () -> assertEquals(
                        ValidatorParameters.NamingConvention.CamelCase, parameters.getCookieParamNamingConvention()));
    }
}
