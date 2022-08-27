package org.openapitools.openapistylevalidator.styleerror;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;
import org.openapitools.openapistylevalidator.error.OperationStyleError;

class OperationStyleErrorTest {
    @Test
    void equalsContract() {
        EqualsVerifier.forClass(OperationStyleError.class)
                .withNonnullFields("styleCheckSection", "fieldNames", "description", "path", "method")
                .verify();
    }
}
