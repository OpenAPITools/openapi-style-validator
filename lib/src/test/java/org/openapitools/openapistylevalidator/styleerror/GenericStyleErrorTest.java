package org.openapitools.openapistylevalidator.styleerror;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class GenericStyleErrorTest {
    @Test
    void equalsContract() {
        EqualsVerifier.forClass(GenericStyleError.class)
                .withNonnullFields("styleCheckSection", "fieldNames", "description")
                .verify();
    }
}
