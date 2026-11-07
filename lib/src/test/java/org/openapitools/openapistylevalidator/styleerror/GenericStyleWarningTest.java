package org.openapitools.openapistylevalidator.styleerror;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class GenericStyleWarningTest {
    @Test
    void equalsContract() {
        EqualsVerifier.forClass(GenericStyleWarning.class)
                .withNonnullFields("styleCheckSection", "fieldNames", "description")
                .verify();
    }
}
