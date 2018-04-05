package com.jaffsoft.openapistylevalidator;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ValidatorParametersTest {

    private ValidatorParameters parameters;

    @Before
    public void setUp() {
        parameters = new ValidatorParameters();
    }

    @Test
    public void validateDefaultValues() {
        assertTrue(parameters.isValidateInfoLicense());
        assertTrue(parameters.isValidateInfoDescription());
        assertTrue(parameters.isValidateInfoContact());
        assertTrue(parameters.isValidateOperationOperationId());
        assertTrue(parameters.isValidateOperationDescription());
        assertTrue(parameters.isValidateOperationTag());
        assertTrue(parameters.isValidateOperationSummary());
        assertTrue(parameters.isValidateModelPropertiesExample());
        assertTrue(parameters.isValidateModelNoLocalDef());

        assertTrue(parameters.isValidateNaming());
        assertEquals(ValidatorParameters.NamingStrategy.HyphenCase, parameters.getPathNamingStrategy());
        assertEquals(ValidatorParameters.NamingStrategy.CamelCase, parameters.getParameterNamingStrategy());
        assertEquals(ValidatorParameters.NamingStrategy.CamelCase, parameters.getPropertyNamingStrategy());
    }

    @Test
    public void testAllGetterSetters() {
        parameters.setValidateInfoLicense(false);
        parameters.setValidateInfoDescription(false);
        parameters.setValidateInfoContact(false);
        parameters.setValidateOperationOperationId(false);
        parameters.setValidateOperationDescription(false);
        parameters.setValidateOperationTag(false);
        parameters.setValidateOperationSummary(false);
        parameters.setValidateModelPropertiesExample(false);
        parameters.setValidateModelNoLocalDef(false);
        parameters.setValidateNaming(false);

        parameters.setPathNamingStrategy(ValidatorParameters.NamingStrategy.CamelCase);
        parameters.setParameterNamingStrategy(ValidatorParameters.NamingStrategy.CamelCase);
        parameters.setPropertyNamingStrategy(ValidatorParameters.NamingStrategy.CamelCase);

        assertFalse(parameters.isValidateInfoLicense());
        assertFalse(parameters.isValidateInfoDescription());
        assertFalse(parameters.isValidateInfoContact());
        assertFalse(parameters.isValidateOperationOperationId());
        assertFalse(parameters.isValidateOperationDescription());
        assertFalse(parameters.isValidateOperationTag());
        assertFalse(parameters.isValidateOperationSummary());
        assertFalse(parameters.isValidateModelPropertiesExample());
        assertFalse(parameters.isValidateModelNoLocalDef());
        assertFalse(parameters.isValidateNaming());

        assertEquals(ValidatorParameters.NamingStrategy.CamelCase, parameters.getPathNamingStrategy());
        assertEquals(ValidatorParameters.NamingStrategy.CamelCase, parameters.getParameterNamingStrategy());
        assertEquals(ValidatorParameters.NamingStrategy.CamelCase, parameters.getPropertyNamingStrategy());
    }

}