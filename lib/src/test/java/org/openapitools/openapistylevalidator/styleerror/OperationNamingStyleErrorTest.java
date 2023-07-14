package org.openapitools.openapistylevalidator.styleerror;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class OperationNamingStyleErrorTest {
    @Test
    void equalsContract() {
        EqualsVerifier.forClass(OperationNamingStyleError.class)
                .withNonnullFields("styleCheckSection", "fieldNames", "description", "path")
                .verify();
    }
}
