package com.jaffsoft.swaggerstylevalidator;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NamingValidatorTest {

    private NamingValidator validator;

    @Before
    public void setUp() throws Exception {
        validator = new NamingValidator();
    }

    @Test
    public void goodUnderscoreCaseShouldReturnTrue() throws Exception {
        //Arrange
        String goodUnderscoreCase1 = "my_variable";
        String goodUnderscoreCase2 = "variable";
        String goodUnderscoreCase3 = "my_super_variable";

        //Act
        boolean actual1 = validator.isNamingValid(goodUnderscoreCase1, ValidatorParameters.NamingStrategy.UnderscoreCase);
        boolean actual2 = validator.isNamingValid(goodUnderscoreCase2, ValidatorParameters.NamingStrategy.UnderscoreCase);
        boolean actual3 = validator.isNamingValid(goodUnderscoreCase3, ValidatorParameters.NamingStrategy.UnderscoreCase);

        //Assert
        assertTrue(actual1);
        assertTrue(actual2);
        assertTrue(actual3);
    }

    @Test
    public void badUnderscoreCaseShouldReturnFalse() throws Exception {
        //Arrange
        String badUnderscoreCase1 = "myVariable";
        String badUnderscoreCase2 = "my-variable";
        String badUnderscoreCase3 = "my-Variable";
        String badUnderscoreCase4 = "my__variable_lol";
        String badUnderscoreCase5 = "_my_variable";
        String badUnderscoreCase6 = "my_variable_";

        //Act
        boolean actual1 = validator.isNamingValid(badUnderscoreCase1, ValidatorParameters.NamingStrategy.UnderscoreCase);
        boolean actual2 = validator.isNamingValid(badUnderscoreCase2, ValidatorParameters.NamingStrategy.UnderscoreCase);
        boolean actual3 = validator.isNamingValid(badUnderscoreCase3, ValidatorParameters.NamingStrategy.UnderscoreCase);
        boolean actual4 = validator.isNamingValid(badUnderscoreCase4, ValidatorParameters.NamingStrategy.UnderscoreCase);
        boolean actual5 = validator.isNamingValid(badUnderscoreCase5, ValidatorParameters.NamingStrategy.UnderscoreCase);
        boolean actual6 = validator.isNamingValid(badUnderscoreCase6, ValidatorParameters.NamingStrategy.UnderscoreCase);

        //Assert
        assertFalse(actual1);
        assertFalse(actual2);
        assertFalse(actual3);
        assertFalse(actual4);
        assertFalse(actual5);
        assertFalse(actual6);
    }

    @Test
    public void goodCamelCaseShouldReturnTrue() throws Exception {
        //Arrange
        String goodCamelCase1 = "myVariable";
        String goodCamelCase2 = "variable";
        String goodCamelCase3 = "mySuperVariable";
        String goodCamelCase4 = "mySuperVariableIsAFunnyOne";

        //Act
        boolean actual1 = validator.isNamingValid(goodCamelCase1, ValidatorParameters.NamingStrategy.CamelCase);
        boolean actual2 = validator.isNamingValid(goodCamelCase2, ValidatorParameters.NamingStrategy.CamelCase);
        boolean actual3 = validator.isNamingValid(goodCamelCase3, ValidatorParameters.NamingStrategy.CamelCase);
        boolean actual4 = validator.isNamingValid(goodCamelCase4, ValidatorParameters.NamingStrategy.CamelCase);

        //Assert
        assertTrue(actual1);
        assertTrue(actual2);
        assertTrue(actual3);
        assertTrue(actual4);
    }

    @Test
    public void badCamelCaseShouldReturnFalse() throws Exception {
        //Arrange
        String badCamelCase1 = "my_variable";
        String badCamelCase2 = "my-variable";
        String badCamelCase3 = "my-Variable";
        String badCamelCase4 = "Variable";
        String badCamelCase5 = "my variable";

        //Act
        boolean actual1 = validator.isNamingValid(badCamelCase1, ValidatorParameters.NamingStrategy.CamelCase);
        boolean actual2 = validator.isNamingValid(badCamelCase2, ValidatorParameters.NamingStrategy.CamelCase);
        boolean actual3 = validator.isNamingValid(badCamelCase3, ValidatorParameters.NamingStrategy.CamelCase);
        boolean actual4 = validator.isNamingValid(badCamelCase4, ValidatorParameters.NamingStrategy.CamelCase);
        boolean actual5 = validator.isNamingValid(badCamelCase5, ValidatorParameters.NamingStrategy.CamelCase);

        //Assert
        assertFalse(actual1);
        assertFalse(actual2);
        assertFalse(actual3);
        assertFalse(actual4);
        assertFalse(actual5);
    }

    @Test
    public void goodHyphenCaseShouldReturnTrue() throws Exception {
        //Arrange
        String goodHyphenCase1 = "my-variable";
        String goodHyphenCase2 = "variable";
        String goodHyphenCase3 = "my-super-variable";

        //Act
        boolean actual1 = validator.isNamingValid(goodHyphenCase1, ValidatorParameters.NamingStrategy.HyphenCase);
        boolean actual2 = validator.isNamingValid(goodHyphenCase2, ValidatorParameters.NamingStrategy.HyphenCase);
        boolean actual3 = validator.isNamingValid(goodHyphenCase3, ValidatorParameters.NamingStrategy.HyphenCase);

        //Assert
        assertTrue(actual1);
        assertTrue(actual2);
        assertTrue(actual3);
    }

    @Test
    public void badHyphenCaseShouldReturnFalse() throws Exception {
        //Arrange
        String badHyphenCase1 = "my_variable";
        String badHyphenCase2 = "myVariable";
        String badHyphenCase3 = "my_Variable";
        String badHyphenCase4 = "my__Variable_is_important";
        String badHyphenCase5 = "_my_variable";
        String badHyphenCase6 = "my_variable_";

        //Act
        boolean actual1 = validator.isNamingValid(badHyphenCase1, ValidatorParameters.NamingStrategy.HyphenCase);
        boolean actual2 = validator.isNamingValid(badHyphenCase2, ValidatorParameters.NamingStrategy.HyphenCase);
        boolean actual3 = validator.isNamingValid(badHyphenCase3, ValidatorParameters.NamingStrategy.HyphenCase);
        boolean actual4 = validator.isNamingValid(badHyphenCase4, ValidatorParameters.NamingStrategy.HyphenCase);
        boolean actual5 = validator.isNamingValid(badHyphenCase5, ValidatorParameters.NamingStrategy.HyphenCase);
        boolean actual6 = validator.isNamingValid(badHyphenCase6, ValidatorParameters.NamingStrategy.HyphenCase);

        //Assert
        assertFalse(actual1);
        assertFalse(actual2);
        assertFalse(actual3);
        assertFalse(actual4);
        assertFalse(actual5);
        assertFalse(actual6);
    }

}