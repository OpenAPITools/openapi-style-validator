package org.openapitools.openapistylevalidator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import org.eclipse.microprofile.openapi.models.PathItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openapitools.openapistylevalidator.styleerror.ModelStyleError;
import org.openapitools.openapistylevalidator.styleerror.StyleError;

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
        errorAggregator.logMissingOrEmptyAttribute(StyleError.StyleCheckSection.APIInfo, "contact");

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

    @Test
    void logUndeclaredOperationTagsShouldAddToErrorList() {
        // Act
        errorAggregator.logUndeclaredOperationTags("/ping", PathItem.HttpMethod.GET, Arrays.asList("tagOne", "tagTwo"));

        // Assert
        Assertions.assertAll(
                () -> assertEquals(1, errorAggregator.getErrorList().size()),
                () -> assertEquals(
                        "*ERROR* in Operation GET /ping 'tags' -> The following tags are used but not declared in the root 'tags' section: tagOne, tagTwo",
                        errorAggregator.getErrorList().get(0).toString()));
    }

    @Test
    void logMissingTagDescriptionShouldAddToErrorList() {
        // Act
        errorAggregator.logMissingTagDescription("demo");

        // Assert
        Assertions.assertAll(
                () -> assertEquals(1, errorAggregator.getErrorList().size()),
                () -> assertEquals(
                        "*WARNING* Section: OpenAPI: 'description' in tag 'demo' -> This field should be present and not empty",
                        errorAggregator.getErrorList().get(0).toString()));
    }
}
