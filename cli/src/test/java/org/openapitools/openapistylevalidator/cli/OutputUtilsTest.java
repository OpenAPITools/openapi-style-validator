package org.openapitools.openapistylevalidator.cli;

import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.openapitools.openapistylevalidator.styleerror.GenericStyleWarning;
import org.openapitools.openapistylevalidator.styleerror.StyleError;

class OutputUtilsTest {
    /**
     * Warnings (e.g. a root tag without a description) must not fail the run:
     * printResults should report them and return normally, without exiting.
     *
     * <p>See <a href="https://github.com/OpenAPITools/openapi-style-validator/pull/810#discussion_r4502998987">the review discussion</a>.
     * Before the fix, any finding in the list triggered {@code System.exit(1)},
     * which killed the test JVM and failed this test.
     */
    @Test
    void printResultsWithOnlyWarningsShouldNotExit() {
        StyleError warning = new GenericStyleWarning(
                StyleError.StyleCheckSection.OpenAPI,
                "tag 'demo'",
                "description",
                "This field should be present and not empty");

        new OutputUtils().printResults(Collections.singletonList(warning));
    }
}
