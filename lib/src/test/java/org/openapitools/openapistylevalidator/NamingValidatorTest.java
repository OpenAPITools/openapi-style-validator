package org.openapitools.openapistylevalidator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NamingValidatorTest {

    private NamingValidator validator;

    @BeforeEach
    void initEach() {
        validator = new NamingValidator();
    }

    @Test
    void goodUnderscoreCaseShouldReturnTrue() {
        //Arrange
        String goodUnderscoreCase1 = "my_variable";
        String goodUnderscoreCase2 = "variable";
        String goodUnderscoreCase3 = "my_super_variable";

        //Act
        boolean actual1 = validator.isNamingValid(goodUnderscoreCase1, ValidatorParameters.NamingConvention.UnderscoreCase);
        boolean actual2 = validator.isNamingValid(goodUnderscoreCase2, ValidatorParameters.NamingConvention.UnderscoreCase);
        boolean actual3 = validator.isNamingValid(goodUnderscoreCase3, ValidatorParameters.NamingConvention.UnderscoreCase);

        //Assert
        Assertions.assertAll(
                () -> assertTrue(actual1),
                () -> assertTrue(actual2),
                () -> assertTrue(actual3)
        );
    }

    @Test
    void badUnderscoreCaseShouldReturnFalse() {
        //Arrange
        String badUnderscoreCase1 = "myVariable";
        String badUnderscoreCase2 = "my-variable";
        String badUnderscoreCase3 = "my-Variable";
        String badUnderscoreCase4 = "my__variable_lol";
        String badUnderscoreCase5 = "_my_variable";
        String badUnderscoreCase6 = "my_variable_";

        //Act
        boolean actual1 = validator.isNamingValid(badUnderscoreCase1, ValidatorParameters.NamingConvention.UnderscoreCase);
        boolean actual2 = validator.isNamingValid(badUnderscoreCase2, ValidatorParameters.NamingConvention.UnderscoreCase);
        boolean actual3 = validator.isNamingValid(badUnderscoreCase3, ValidatorParameters.NamingConvention.UnderscoreCase);
        boolean actual4 = validator.isNamingValid(badUnderscoreCase4, ValidatorParameters.NamingConvention.UnderscoreCase);
        boolean actual5 = validator.isNamingValid(badUnderscoreCase5, ValidatorParameters.NamingConvention.UnderscoreCase);
        boolean actual6 = validator.isNamingValid(badUnderscoreCase6, ValidatorParameters.NamingConvention.UnderscoreCase);

        //Assert
        Assertions.assertAll(
                () -> assertFalse(actual1),
                () -> assertFalse(actual2),
                () -> assertFalse(actual3),
                () -> assertFalse(actual4),
                () -> assertFalse(actual5),
                () -> assertFalse(actual6)
        );
    }

    @Test
    void goodCamelCaseShouldReturnTrue() {
        //Arrange
        String goodCamelCase1 = "myVariable";
        String goodCamelCase2 = "variable";
        String goodCamelCase3 = "mySuperVariable";
        String goodCamelCase4 = "mySuperVariableIsAFunnyOne";
        String goodCamelCase5 = "zone2Delete";
        String goodCamelCase6 = "address1";

        //Act
        boolean actual1 = validator.isNamingValid(goodCamelCase1, ValidatorParameters.NamingConvention.CamelCase);
        boolean actual2 = validator.isNamingValid(goodCamelCase2, ValidatorParameters.NamingConvention.CamelCase);
        boolean actual3 = validator.isNamingValid(goodCamelCase3, ValidatorParameters.NamingConvention.CamelCase);
        boolean actual4 = validator.isNamingValid(goodCamelCase4, ValidatorParameters.NamingConvention.CamelCase);
        boolean actual5 = validator.isNamingValid(goodCamelCase5, ValidatorParameters.NamingConvention.CamelCase);
        boolean actual6 = validator.isNamingValid(goodCamelCase6, ValidatorParameters.NamingConvention.CamelCase);

        //Assert
        Assertions.assertAll(
                () -> assertTrue(actual1),
                () -> assertTrue(actual2),
                () -> assertTrue(actual3),
                () -> assertTrue(actual4),
                () -> assertTrue(actual5),
                () -> assertTrue(actual6)
        );
    }

    @Test
    void badCamelCaseShouldReturnFalse() {
        //Arrange
        String badCamelCase1 = "my_variable";
        String badCamelCase2 = "my-variable";
        String badCamelCase3 = "my-Variable";
        String badCamelCase4 = "Variable";
        String badCamelCase5 = "my variable";

        //Act
        boolean actual1 = validator.isNamingValid(badCamelCase1, ValidatorParameters.NamingConvention.CamelCase);
        boolean actual2 = validator.isNamingValid(badCamelCase2, ValidatorParameters.NamingConvention.CamelCase);
        boolean actual3 = validator.isNamingValid(badCamelCase3, ValidatorParameters.NamingConvention.CamelCase);
        boolean actual4 = validator.isNamingValid(badCamelCase4, ValidatorParameters.NamingConvention.CamelCase);
        boolean actual5 = validator.isNamingValid(badCamelCase5, ValidatorParameters.NamingConvention.CamelCase);

        //Assert
        Assertions.assertAll(
                () -> assertFalse(actual1),
                () -> assertFalse(actual2),
                () -> assertFalse(actual3),
                () -> assertFalse(actual4),
                () -> assertFalse(actual5)
        );
    }

    @Test
    void goodHyphenCaseShouldReturnTrue() {
        //Arrange
        String goodHyphenCase1 = "my-variable";
        String goodHyphenCase2 = "variable";
        String goodHyphenCase3 = "my-super-variable";

        //Act
        boolean actual1 = validator.isNamingValid(goodHyphenCase1, ValidatorParameters.NamingConvention.HyphenCase);
        boolean actual2 = validator.isNamingValid(goodHyphenCase2, ValidatorParameters.NamingConvention.HyphenCase);
        boolean actual3 = validator.isNamingValid(goodHyphenCase3, ValidatorParameters.NamingConvention.HyphenCase);

        //Assert
        Assertions.assertAll(
                () -> assertTrue(actual1),
                () -> assertTrue(actual2),
                () -> assertTrue(actual3)
        );
    }

    @Test
    void badHyphenCaseShouldReturnFalse() {
        //Arrange
        String badHyphenCase1 = "my_variable";
        String badHyphenCase2 = "myVariable";
        String badHyphenCase3 = "my_Variable";
        String badHyphenCase4 = "my__Variable_is_important";
        String badHyphenCase5 = "_my_variable";
        String badHyphenCase6 = "my_variable_";

        //Act
        boolean actual1 = validator.isNamingValid(badHyphenCase1, ValidatorParameters.NamingConvention.HyphenCase);
        boolean actual2 = validator.isNamingValid(badHyphenCase2, ValidatorParameters.NamingConvention.HyphenCase);
        boolean actual3 = validator.isNamingValid(badHyphenCase3, ValidatorParameters.NamingConvention.HyphenCase);
        boolean actual4 = validator.isNamingValid(badHyphenCase4, ValidatorParameters.NamingConvention.HyphenCase);
        boolean actual5 = validator.isNamingValid(badHyphenCase5, ValidatorParameters.NamingConvention.HyphenCase);
        boolean actual6 = validator.isNamingValid(badHyphenCase6, ValidatorParameters.NamingConvention.HyphenCase);

        //Assert
        Assertions.assertAll(
                () -> assertFalse(actual1),
                () -> assertFalse(actual2),
                () -> assertFalse(actual3),
                () -> assertFalse(actual4),
                () -> assertFalse(actual5),
                () -> assertFalse(actual6)
        );
    }

}