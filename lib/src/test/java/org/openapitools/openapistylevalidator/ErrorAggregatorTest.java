package org.openapitools.openapistylevalidator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openapitools.openapistylevalidator.styleerror.StyleError;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ErrorAggregatorTest {

    private ErrorAggregator errorAggregator;

    @BeforeEach
    void initEch() {
        errorAggregator = new ErrorAggregator();
    }

    @Test
    void logMissingOrEmptyAttributeShouldAddToErrorList() {
        //Arrange

        //Act
        errorAggregator.logMissingOrEmptyAttribute(StyleError.StyleCheckSection.APIInfo, "contact");

        //Assert
        Assertions.assertAll(
                () -> assertEquals(1, errorAggregator.getErrorList().size()),
                //TODO: Change this to use getter instead!!
                () -> assertEquals("*ERROR* Section: APIInfo: 'contact' -> Should be present and not empty", errorAggregator.getErrorList().get(0).toString())
        );
    }

}