package org.openapitools.openapistylevalidator.styleerror;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;
import org.openapitools.openapistylevalidator.error.ModelNamingStyleError;

class ModelNamingStyleErrorTest {
    @Test
    void equalsContract() {
        EqualsVerifier.forClass(ModelNamingStyleError.class)
                .withNonnullFields("styleCheckSection", "fieldNames", "description", "model")
                .verify();
    }
}
