package org.openapitools.openapistylevalidator;

import java.util.Map;
import org.eclipse.microprofile.openapi.models.Components;
import org.eclipse.microprofile.openapi.models.media.Schema;

/*
 * See https://github.com/OpenAPITools/openapi-style-validator/issues/741
 *
 * A schema of type `object` (or one inferred to be an object because it
 * declares properties) should state whether it allows additional properties:
 * when `additionalProperties` is absent, it is left to the implementation to
 * decide if properties outside of the spec are accepted.
 * See https://spec.openapis.org/oas/v3.0.3#schema-object
 *
 * The `additionalProperties` keyword is either a boolean or a schema;
 * declaring either of them satisfies this rule.
 *
 * Like the other model rules, the check is scoped to the named schemas of
 * `components/schemas`.
 */
class AdditionalPropertiesValidator {
    private final ErrorAggregator errorAggregator;

    AdditionalPropertiesValidator(ErrorAggregator errorAggregator) {
        this.errorAggregator = errorAggregator;
    }

    void validate(Components components) {
        if (components == null || components.getSchemas() == null) {
            return;
        }

        for (Map.Entry<String, Schema> entry : components.getSchemas().entrySet()) {
            Schema schema = entry.getValue();
            if (isObjectSchema(schema) && !declaresAdditionalProperties(schema)) {
                errorAggregator.logMissingAdditionalProperties(entry.getKey());
            }
        }
    }

    private static boolean isObjectSchema(Schema schema) {
        if (schema == null) {
            return false;
        }
        if (schema.getType() == Schema.SchemaType.OBJECT) {
            return true;
        }
        /* The `type` keyword is optional: a schema declaring properties is
         * understood as an object. */
        return schema.getType() == null
                && schema.getProperties() != null
                && !schema.getProperties().isEmpty();
    }

    private static boolean declaresAdditionalProperties(Schema schema) {
        return schema.getAdditionalPropertiesBoolean() != null || schema.getAdditionalPropertiesSchema() != null;
    }
}
