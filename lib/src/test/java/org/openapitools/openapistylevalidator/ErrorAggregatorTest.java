package org.openapitools.openapistylevalidator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openapitools.openapistylevalidator.error.ModelStyleError;
import org.openapitools.openapistylevalidator.error.StyleCheckSection;

class ErrorAggregatorTest {

    private ErrorAggregator errorAggregator;

    @BeforeEach
    void initEach() {
        errorAggregator = new ErrorAggregator();
    }

    @Test
    void logMissingOrEmptyAttributeShouldAddToErrorList() {
        // Arrange

        // Act
        errorAggregator.logMissingOrEmptyAttribute(StyleCheckSection.APIInfo, "contact");

        // Assert
        Assertions.assertAll(
                () -> assertEquals(1, errorAggregator.getErrorList().size()),
                // TODO: Change this to use getter instead!!
                () -> assertEquals(
                        "*ERROR* Section: APIInfo: 'contact' -> Should be present and not empty",
                        errorAggregator.getErrorList().get(0).toString()));
    }

    @Test
    void logMissingModelProperty() {
        String modelName = "someModel";
        String nameOfMissingProperty = "missingProperty";
        errorAggregator.logMissingModelProperty(modelName, nameOfMissingProperty);
        ModelStyleError expectedError = new ModelStyleError(
                null,
                "This property should be present or removed from the list of required",
                modelName,
                nameOfMissingProperty);
        assertEquals(Collections.singletonList(expectedError), errorAggregator.getErrorList());
    }
}
