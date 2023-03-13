package org.openapitools.openapistylevalidator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NamingValidatorTest {

    private NamingValidator validator;

    @BeforeEach
    void initEach() {
        validator = new NamingValidator();
    }

    @Test
    void anyCaseShouldAlwaysReturnTrue() {
        // Arrange
        String whateverCase1 = "my_variable";
        String whateverCase2 = "my-variable";
        String whateverCase3 = "variable";

        // Act
        boolean actual1 = validator.isNamingValid(whateverCase1, ValidatorParameters.NamingConvention.AnyCase);
        boolean actual2 = validator.isNamingValid(whateverCase2, ValidatorParameters.NamingConvention.AnyCase);
        boolean actual3 = validator.isNamingValid(whateverCase3, ValidatorParameters.NamingConvention.AnyCase);

        // Assert
        Assertions.assertAll(() -> assertTrue(actual1), () -> assertTrue(actual2), () -> assertTrue(actual3));
    }

    @Test
    void goodUnderscoreCaseShouldReturnTrue() {
        // Arrange
        String goodUnderscoreCase1 = "my_variable";
        String goodUnderscoreCase2 = "variable";
        String goodUnderscoreCase3 = "my_super_variable";

        // Act
        boolean actual1 =
                validator.isNamingValid(goodUnderscoreCase1, ValidatorParameters.NamingConvention.UnderscoreCase);
        boolean actual2 =
                validator.isNamingValid(goodUnderscoreCase2, ValidatorParameters.NamingConvention.UnderscoreCase);
        boolean actual3 =
                validator.isNamingValid(goodUnderscoreCase3, ValidatorParameters.NamingConvention.UnderscoreCase);

        // Assert
        Assertions.assertAll(() -> assertTrue(actual1), () -> assertTrue(actual2), () -> assertTrue(actual3));
    }

    @Test
    void badUnderscoreCaseShouldReturnFalse() {
        // Arrange
        String badUnderscoreCase1 = "myVariable";
        String badUnderscoreCase2 = "my-variable";
        String badUnderscoreCase3 = "my-Variable";
        String badUnderscoreCase4 = "my__variable_lol";
        String badUnderscoreCase5 = "_my_variable";
        String badUnderscoreCase6 = "my_variable_";

        // Act
        boolean actual1 =
                validator.isNamingValid(badUnderscoreCase1, ValidatorParameters.NamingConvention.UnderscoreCase);
        boolean actual2 =
                validator.isNamingValid(badUnderscoreCase2, ValidatorParameters.NamingConvention.UnderscoreCase);
        boolean actual3 =
                validator.isNamingValid(badUnderscoreCase3, ValidatorParameters.NamingConvention.UnderscoreCase);
        boolean actual4 =
                validator.isNamingValid(badUnderscoreCase4, ValidatorParameters.NamingConvention.UnderscoreCase);
        boolean actual5 =
                validator.isNamingValid(badUnderscoreCase5, ValidatorParameters.NamingConvention.UnderscoreCase);
        boolean actual6 =
                validator.isNamingValid(badUnderscoreCase6, ValidatorParameters.NamingConvention.UnderscoreCase);

        // Assert
        Assertions.assertAll(
                () -> assertFalse(actual1),
                () -> assertFalse(actual2),
                () -> assertFalse(actual3),
                () -> assertFalse(actual4),
                () -> assertFalse(actual5),
                () -> assertFalse(actual6));
    }

    @Test
    void goodCamelCaseShouldReturnTrue() {
        // Arrange
        String goodCamelCase1 = "myVariable";
        String goodCamelCase2 = "variable";
        String goodCamelCase3 = "mySuperVariable";
        String goodCamelCase4 = "mySuperVariableIsAFunnyOne";
        String goodCamelCase5 = "zone2Delete";
        String goodCamelCase6 = "address1";

        // Act
        boolean actual1 = validator.isNamingValid(goodCamelCase1, ValidatorParameters.NamingConvention.CamelCase);
        boolean actual2 = validator.isNamingValid(goodCamelCase2, ValidatorParameters.NamingConvention.CamelCase);
        boolean actual3 = validator.isNamingValid(goodCamelCase3, ValidatorParameters.NamingConvention.CamelCase);
        boolean actual4 = validator.isNamingValid(goodCamelCase4, ValidatorParameters.NamingConvention.CamelCase);
        boolean actual5 = validator.isNamingValid(goodCamelCase5, ValidatorParameters.NamingConvention.CamelCase);
        boolean actual6 = validator.isNamingValid(goodCamelCase6, ValidatorParameters.NamingConvention.CamelCase);

        // Assert
        Assertions.assertAll(
                () -> assertTrue(actual1),
                () -> assertTrue(actual2),
                () -> assertTrue(actual3),
                () -> assertTrue(actual4),
                () -> assertTrue(actual5),
                () -> assertTrue(actual6));
    }

    @Test
    void badCamelCaseShouldReturnFalse() {
        // Arrange
        String badCamelCase1 = "my_variable";
        String badCamelCase2 = "my-variable";
        String badCamelCase3 = "my-Variable";
        String badCamelCase4 = "Variable";
        String badCamelCase5 = "my variable";

        // Act
        boolean actual1 = validator.isNamingValid(badCamelCase1, ValidatorParameters.NamingConvention.CamelCase);
        boolean actual2 = validator.isNamingValid(badCamelCase2, ValidatorParameters.NamingConvention.CamelCase);
        boolean actual3 = validator.isNamingValid(badCamelCase3, ValidatorParameters.NamingConvention.CamelCase);
        boolean actual4 = validator.isNamingValid(badCamelCase4, ValidatorParameters.NamingConvention.CamelCase);
        boolean actual5 = validator.isNamingValid(badCamelCase5, ValidatorParameters.NamingConvention.CamelCase);

        // Assert
        Assertions.assertAll(
                () -> assertFalse(actual1),
                () -> assertFalse(actual2),
                () -> assertFalse(actual3),
                () -> assertFalse(actual4),
                () -> assertFalse(actual5));
    }

    @Test
    void goodPascalCaseShouldReturnTrue1() {
        // Arrange
        String goodPascalCase1 = "V";
        String goodPascalCase2 = "Variable";
        String goodPascalCase3 = "MySuperVariable";
        String goodPascalCase4 = "MySuperVariableIsAFunnyOne";
        String goodPascalCase5 = "Zone2Delete";
        String goodPascalCase6 = "Address1";

        // Act
        boolean actual1 = validator.isNamingValid(goodPascalCase1, ValidatorParameters.NamingConvention.PascalCase);
        boolean actual2 = validator.isNamingValid(goodPascalCase2, ValidatorParameters.NamingConvention.PascalCase);
        boolean actual3 = validator.isNamingValid(goodPascalCase3, ValidatorParameters.NamingConvention.PascalCase);
        boolean actual4 = validator.isNamingValid(goodPascalCase4, ValidatorParameters.NamingConvention.PascalCase);
        boolean actual5 = validator.isNamingValid(goodPascalCase5, ValidatorParameters.NamingConvention.PascalCase);
        boolean actual6 = validator.isNamingValid(goodPascalCase6, ValidatorParameters.NamingConvention.PascalCase);

        // Assert
        Assertions.assertAll(
                () -> assertTrue(actual1),
                () -> assertTrue(actual2),
                () -> assertTrue(actual3),
                () -> assertTrue(actual4),
                () -> assertTrue(actual5),
                () -> assertTrue(actual6));
    }

    @Test
    void badPascalCaseShouldReturnFalse() {
        // Arrange
        String badPascalCase1 = "my_variable";
        String badPascalCase2 = "my-variable";
        String badPascalCase3 = "my-Variable";
        String badPascalCase4 = "variable";
        String badPascalCase5 = "my variable";

        // Act
        boolean actual1 = validator.isNamingValid(badPascalCase1, ValidatorParameters.NamingConvention.PascalCase);
        boolean actual2 = validator.isNamingValid(badPascalCase2, ValidatorParameters.NamingConvention.PascalCase);
        boolean actual3 = validator.isNamingValid(badPascalCase3, ValidatorParameters.NamingConvention.PascalCase);
        boolean actual4 = validator.isNamingValid(badPascalCase4, ValidatorParameters.NamingConvention.PascalCase);
        boolean actual5 = validator.isNamingValid(badPascalCase5, ValidatorParameters.NamingConvention.PascalCase);

        // Assert
        Assertions.assertAll(
                () -> assertFalse(actual1),
                () -> assertFalse(actual2),
                () -> assertFalse(actual3),
                () -> assertFalse(actual4),
                () -> assertFalse(actual5));
    }

    @Test
    void goodHyphenCaseShouldReturnTrue() {
        // Arrange
        String goodHyphenCase1 = "my-variable";
        String goodHyphenCase2 = "variable";
        String goodHyphenCase3 = "my-super-variable";

        // Act
        boolean actual1 = validator.isNamingValid(goodHyphenCase1, ValidatorParameters.NamingConvention.HyphenCase);
        boolean actual2 = validator.isNamingValid(goodHyphenCase2, ValidatorParameters.NamingConvention.HyphenCase);
        boolean actual3 = validator.isNamingValid(goodHyphenCase3, ValidatorParameters.NamingConvention.HyphenCase);

        // Assert
        Assertions.assertAll(() -> assertTrue(actual1), () -> assertTrue(actual2), () -> assertTrue(actual3));
    }

    @Test
    void badHyphenCaseShouldReturnFalse() {
        // Arrange
        String badHyphenCase1 = "my_variable";
        String badHyphenCase2 = "myVariable";
        String badHyphenCase3 = "my_Variable";
        String badHyphenCase4 = "my__Variable_is_important";
        String badHyphenCase5 = "_my_variable";
        String badHyphenCase6 = "my_variable_";

        // Act
        boolean actual1 = validator.isNamingValid(badHyphenCase1, ValidatorParameters.NamingConvention.HyphenCase);
        boolean actual2 = validator.isNamingValid(badHyphenCase2, ValidatorParameters.NamingConvention.HyphenCase);
        boolean actual3 = validator.isNamingValid(badHyphenCase3, ValidatorParameters.NamingConvention.HyphenCase);
        boolean actual4 = validator.isNamingValid(badHyphenCase4, ValidatorParameters.NamingConvention.HyphenCase);
        boolean actual5 = validator.isNamingValid(badHyphenCase5, ValidatorParameters.NamingConvention.HyphenCase);
        boolean actual6 = validator.isNamingValid(badHyphenCase6, ValidatorParameters.NamingConvention.HyphenCase);

        // Assert
        Assertions.assertAll(
                () -> assertFalse(actual1),
                () -> assertFalse(actual2),
                () -> assertFalse(actual3),
                () -> assertFalse(actual4),
                () -> assertFalse(actual5),
                () -> assertFalse(actual6));
    }

    @Test
    void goodHyphenUpperCaseShouldReturnTrue() {
        // Arrange
        String goodHyphenUpperCase1 = "My-Variable";
        String goodHyphenUpperCase2 = "Variable";
        String goodHyphenUpperCase3 = "My-Super-Variable";

        // Act
        boolean actual1 =
                validator.isNamingValid(goodHyphenUpperCase1, ValidatorParameters.NamingConvention.HyphenUpperCase);
        boolean actual2 =
                validator.isNamingValid(goodHyphenUpperCase2, ValidatorParameters.NamingConvention.HyphenUpperCase);
        boolean actual3 =
                validator.isNamingValid(goodHyphenUpperCase3, ValidatorParameters.NamingConvention.HyphenUpperCase);

        // Assert
        Assertions.assertAll(() -> assertTrue(actual1), () -> assertTrue(actual2), () -> assertTrue(actual3));
    }

    @Test
    void badHyphenUpperCaseShouldReturnFalse() {
        // Arrange
        String badHyphenUpperCase1 = "my-Variable";
        String badHyphenUpperCase2 = "variable";
        String badHyphenUpperCase3 = "my--variable";
        String badHyphenUpperCase4 = "My_Variable";
        String badHyphenUpperCase5 = "MyVariable";

        // Act
        boolean actual1 =
                validator.isNamingValid(badHyphenUpperCase1, ValidatorParameters.NamingConvention.HyphenUpperCase);
        boolean actual2 =
                validator.isNamingValid(badHyphenUpperCase2, ValidatorParameters.NamingConvention.HyphenUpperCase);
        boolean actual3 =
                validator.isNamingValid(badHyphenUpperCase3, ValidatorParameters.NamingConvention.HyphenUpperCase);
        boolean actual4 =
                validator.isNamingValid(badHyphenUpperCase4, ValidatorParameters.NamingConvention.HyphenUpperCase);
        boolean actual5 =
                validator.isNamingValid(badHyphenUpperCase5, ValidatorParameters.NamingConvention.HyphenUpperCase);

        // Assert
        Assertions.assertAll(
                () -> assertFalse(actual1, badHyphenUpperCase1),
                () -> assertFalse(actual2, badHyphenUpperCase2),
                () -> assertFalse(actual3, badHyphenUpperCase3),
                () -> assertFalse(actual4, badHyphenUpperCase4),
                () -> assertFalse(actual5, badHyphenUpperCase5));
    }
}
