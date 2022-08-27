package org.openapitools.openapistylevalidator.styleerror;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;
import org.openapitools.openapistylevalidator.error.GenericStyleError;

class GenericStyleErrorTest {
    @Test
    void equalsContract() {
        EqualsVerifier.forClass(GenericStyleError.class)
                .withNonnullFields("styleCheckSection", "fieldNames", "description")
                .verify();
    }
}
