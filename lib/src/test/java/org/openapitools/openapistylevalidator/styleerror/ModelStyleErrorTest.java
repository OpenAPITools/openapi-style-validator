package org.openapitools.openapistylevalidator.styleerror;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class ModelStyleErrorTest {
    @Test
    void equalsContract() {
        EqualsVerifier.forClass(ModelStyleError.class)
                .withNonnullFields("styleCheckSection", "fieldNames", "description", "modelName", "propertyName")
                .verify();
    }
}