package org.openapitools.openapistylevalidator.styleerror;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class OperationStyleErrorTest {
    @Test
    void equalsContract() {
        EqualsVerifier.forClass(OperationStyleError.class)
                .withNonnullFields("styleCheckSection", "fieldNames", "description", "path", "method")
                .verify();
    }
}