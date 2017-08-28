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
        boolean actual1 = validator.isUnderscoreCase(goodUnderscoreCase1);
        boolean actual2 = validator.isUnderscoreCase(goodUnderscoreCase2);
        boolean actual3 = validator.isUnderscoreCase(goodUnderscoreCase3);

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
        boolean actual1 = validator.isUnderscoreCase(badUnderscoreCase1);
        boolean actual2 = validator.isUnderscoreCase(badUnderscoreCase2);
        boolean actual3 = validator.isUnderscoreCase(badUnderscoreCase3);
        boolean actual4 = validator.isUnderscoreCase(badUnderscoreCase4);
        boolean actual5 = validator.isUnderscoreCase(badUnderscoreCase5);
        boolean actual6 = validator.isUnderscoreCase(badUnderscoreCase6);

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
        boolean actual1 = validator.isCamelCase(goodCamelCase1);
        boolean actual2 = validator.isCamelCase(goodCamelCase2);
        boolean actual3 = validator.isCamelCase(goodCamelCase3);
        boolean actual4 = validator.isCamelCase(goodCamelCase4);

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
        boolean actual1 = validator.isCamelCase(badCamelCase1);
        boolean actual2 = validator.isCamelCase(badCamelCase2);
        boolean actual3 = validator.isCamelCase(badCamelCase3);
        boolean actual4 = validator.isCamelCase(badCamelCase4);
        boolean actual5 = validator.isCamelCase(badCamelCase5);

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
        boolean actual1 = validator.isHyphenCase(goodHyphenCase1);
        boolean actual2 = validator.isHyphenCase(goodHyphenCase2);
        boolean actual3 = validator.isHyphenCase(goodHyphenCase3);

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
        boolean actual1 = validator.isHyphenCase(badHyphenCase1);
        boolean actual2 = validator.isHyphenCase(badHyphenCase2);
        boolean actual3 = validator.isHyphenCase(badHyphenCase3);
        boolean actual4 = validator.isHyphenCase(badHyphenCase4);
        boolean actual5 = validator.isHyphenCase(badHyphenCase5);
        boolean actual6 = validator.isHyphenCase(badHyphenCase6);

        //Assert
        assertFalse(actual1);
        assertFalse(actual2);
        assertFalse(actual3);
        assertFalse(actual4);
        assertFalse(actual5);
        assertFalse(actual6);
    }

}