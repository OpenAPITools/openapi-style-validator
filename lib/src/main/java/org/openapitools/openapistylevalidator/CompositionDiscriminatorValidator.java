package org.openapitools.openapistylevalidator;

import java.util.Map;
import org.eclipse.microprofile.openapi.models.Components;
import org.eclipse.microprofile.openapi.models.media.Schema;

/*
 * See https://github.com/OpenAPITools/openapi-style-validator/issues/806
 *
 * A schema that composes alternative models with `oneOf` or `anyOf` should
 * define a `discriminator` so that API consumers can tell the possible object
 * types apart. See
 * https://swagger.io/docs/specification/v3_0/data-models/inheritance-and-polymorphism/
 *
 * `allOf` compositions are out of scope: `allOf` describes a merged model
 * rather than a set of alternatives, and the documentation referenced above
 * limits the use of the discriminator to `oneOf` and `anyOf`.
 */
class CompositionDiscriminatorValidator {
    private final ErrorAggregator errorAggregator;

    CompositionDiscriminatorValidator(ErrorAggregator errorAggregator) {
        this.errorAggregator = errorAggregator;
    }

    void validate(Components components) {
        if (components == null || components.getSchemas() == null) {
            return;
        }

        for (Map.Entry<String, Schema> entry : components.getSchemas().entrySet()) {
            Schema schema = entry.getValue();
            if (hasAlternativeComposition(schema) && schema.getDiscriminator() == null) {
                errorAggregator.logMissingDiscriminator(entry.getKey());
            }
        }
    }

    private boolean hasAlternativeComposition(Schema schema) {
        return (schema.getOneOf() != null && !schema.getOneOf().isEmpty())
                || (schema.getAnyOf() != null && !schema.getAnyOf().isEmpty());
    }
}
