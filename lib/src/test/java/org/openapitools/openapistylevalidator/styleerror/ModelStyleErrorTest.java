package org.openapitools.openapistylevalidator.styleerror;

import static org.junit.jupiter.api.Assertions.assertEquals;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;
import org.openapitools.openapistylevalidator.error.ModelStyleError;

class ModelStyleErrorTest {
    @Test
    void equalsContract() {
        EqualsVerifier.forClass(ModelStyleError.class)
                .withNonnullFields("styleCheckSection", "fieldNames", "description", "modelName", "propertyName")
                .verify();
    }

    @Test
    void toString_notNullFieldNames() {
        ModelStyleError error = new ModelStyleError("f", "d", "m", "p");
        assertEquals("*ERROR* in Model 'm', property 'p', field 'f' -> d", error.toString());
    }

    @Test
    void toString_nullFieldNames() {
        ModelStyleError error = new ModelStyleError(null, "d", "m", "p");
        assertEquals("*ERROR* in Model 'm', property 'p' -> d", error.toString());
    }
}
