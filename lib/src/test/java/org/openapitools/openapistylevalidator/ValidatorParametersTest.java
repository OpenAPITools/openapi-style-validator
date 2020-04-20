package org.openapitools.openapistylevalidator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
                () -> assertTrue(parameters.isValidateModelNoLocalDef()),
                () -> assertTrue(parameters.isValidateNaming()),
                () -> assertTrue(parameters.isIgnoreHeaderXNaming()),
                () -> assertEquals(ValidatorParameters.NamingConvention.HyphenCase, parameters.getPathNamingConvention()),
                () -> assertEquals(ValidatorParameters.NamingConvention.CamelCase, parameters.getParameterNamingConvention()),
                () -> assertEquals(ValidatorParameters.NamingConvention.CamelCase, parameters.getPropertyNamingConvention())
        );
    }

    @Test
    void testAllGetterSetters() {
        parameters
                .setValidateInfoLicense(false)
                .setValidateInfoDescription(false)
                .setValidateInfoContact(false)
                .setValidateOperationOperationId(false)
                .setValidateOperationDescription(false)
                .setValidateOperationTag(false)
                .setValidateOperationSummary(false)
                .setValidateModelPropertiesExample(false)
                .setValidateModelNoLocalDef(false)
                .setValidateNaming(false)
                .setIgnoreHeaderXNaming(false)
                .setPathNamingConvention(ValidatorParameters.NamingConvention.CamelCase)
                .setParameterNamingConvention(ValidatorParameters.NamingConvention.CamelCase)
                .setPropertyNamingConvention(ValidatorParameters.NamingConvention.CamelCase);

        Assertions.assertAll(
                () -> assertFalse(parameters.isValidateInfoLicense()),
                () -> assertFalse(parameters.isValidateInfoDescription()),
                () -> assertFalse(parameters.isValidateInfoContact()),
                () -> assertFalse(parameters.isValidateOperationOperationId()),
                () -> assertFalse(parameters.isValidateOperationDescription()),
                () -> assertFalse(parameters.isValidateOperationTag()),
                () -> assertFalse(parameters.isValidateOperationSummary()),
                () -> assertFalse(parameters.isValidateModelPropertiesExample()),
                () -> assertFalse(parameters.isValidateModelNoLocalDef()),
                () -> assertFalse(parameters.isValidateNaming()),
                () -> assertFalse(parameters.isIgnoreHeaderXNaming()),
                () -> assertEquals(ValidatorParameters.NamingConvention.CamelCase, parameters.getPathNamingConvention()),
                () -> assertEquals(ValidatorParameters.NamingConvention.CamelCase, parameters.getParameterNamingConvention()),
                () -> assertEquals(ValidatorParameters.NamingConvention.CamelCase, parameters.getPropertyNamingConvention())
        );
    }

    @Test
    void validateLegacyDefaultValuesAndSetters() {
        Assertions.assertAll(
                () -> assertEquals(ValidatorParameters.NamingStrategy.HyphenCase, parameters.getPathNamingStrategy()),
                () -> assertEquals(ValidatorParameters.NamingStrategy.CamelCase, parameters.getParameterNamingStrategy()),
                () -> assertEquals(ValidatorParameters.NamingStrategy.CamelCase, parameters.getPropertyNamingStrategy())
        );

        parameters.setPathNamingStrategy(ValidatorParameters.NamingStrategy.CamelCase);
        parameters.setParameterNamingStrategy(ValidatorParameters.NamingStrategy.CamelCase);
        parameters.setPropertyNamingStrategy(ValidatorParameters.NamingStrategy.CamelCase);

        Assertions.assertAll(
                () -> assertEquals(ValidatorParameters.NamingConvention.CamelCase, parameters.getPathNamingConvention()),
                () -> assertEquals(ValidatorParameters.NamingConvention.CamelCase, parameters.getParameterNamingConvention()),
                () -> assertEquals(ValidatorParameters.NamingConvention.CamelCase, parameters.getPropertyNamingConvention())
        );
    }
}