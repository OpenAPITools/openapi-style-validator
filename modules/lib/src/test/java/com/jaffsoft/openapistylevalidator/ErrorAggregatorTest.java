package com.jaffsoft.openapistylevalidator;

import com.jaffsoft.openapistylevalidator.styleerror.StyleError;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ErrorAggregatorTest {

    private ErrorAggregator errorAggregator;

    @Before
    public void setUp() {
        errorAggregator = new ErrorAggregator();
    }

    @Test
    public void logMissingOrEmptyAttributeShouldAddToErrorList() {
        //Arrange

        //Act
        errorAggregator.logMissingOrEmptyAttribute(StyleError.StyleCheckSection.APIInfo, "contact");

        //Assert
        assertEquals(1, errorAggregator.getErrorList().size());
        //TODO: Change this to use getter instead!!
        assertEquals("*ERROR* Section: APIInfo: 'contact' -> Should be present and not empty", errorAggregator.getErrorList().get(0).toString());
    }

}