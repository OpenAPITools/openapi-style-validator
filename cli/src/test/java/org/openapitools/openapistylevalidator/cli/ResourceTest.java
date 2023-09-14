package org.openapitools.openapistylevalidator.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.openapitools.openapistylevalidator.ValidatorParameters;

class ResourceTest {
    private static final String PREFIX = "    \"";
    private static final String SEPARATOR = "\": ";
    private static final String SEPARATOR_QUOTE = "\": \"";
    private static final String NEXT_LINE = "," + System.lineSeparator();
    private static final String NEXT_LINE_QUOTE = "\"," + System.lineSeparator();

    @Test
    void defaultJsonFileContainsConstants() throws Exception {
        InputStream stream = getClass().getResourceAsStream("/default.json");
        assert stream != null;
        String content = IOUtils.toString(stream, StandardCharsets.UTF_8);

        String sb = "{" + System.lineSeparator()
                + PREFIX
                + ValidatorParameters.VALIDATE_INFO_LICENSE
                + SEPARATOR
                + "true"
                + NEXT_LINE
                + PREFIX
                + ValidatorParameters.VALIDATE_INFO_DESCRIPTION
                + SEPARATOR
                + "true"
                + NEXT_LINE
                + PREFIX
                + ValidatorParameters.VALIDATE_INFO_CONTACT
                + SEPARATOR
                + "true"
                + NEXT_LINE
                + PREFIX
                + ValidatorParameters.VALIDATE_OPERATION_OPERATION_ID
                + SEPARATOR
                + "true"
                + NEXT_LINE
                + PREFIX
                + ValidatorParameters.VALIDATE_OPERATION_DESCRIPTION
                + SEPARATOR
                + "true"
                + NEXT_LINE
                + PREFIX
                + ValidatorParameters.VALIDATE_OPERATION_TAG
                + SEPARATOR
                + "true"
                + NEXT_LINE
                + PREFIX
                + ValidatorParameters.VALIDATE_OPERATION_SUMMARY
                + SEPARATOR
                + "true"
                + NEXT_LINE
                + PREFIX
                + ValidatorParameters.VALIDATE_MODEL_PROPERTIES_EXAMPLE
                + SEPARATOR
                + "true"
                + NEXT_LINE
                + PREFIX
                + ValidatorParameters.VALIDATE_MODEL_PROPERTIES_DESCRIPTION
                + SEPARATOR
                + "true"
                + NEXT_LINE
                + PREFIX
                + ValidatorParameters.VALIDATE_MODEL_REQUIRED_PROPERTIES
                + SEPARATOR
                + "true"
                + NEXT_LINE
                + PREFIX
                + ValidatorParameters.VALIDATE_MODEL_NO_LOCAL_DEF
                + SEPARATOR
                + "true"
                + NEXT_LINE
                + PREFIX
                + ValidatorParameters.VALIDATE_NAMING
                + SEPARATOR
                + "true"
                + NEXT_LINE
                + PREFIX
                + ValidatorParameters.IGNORE_HEADER_X_NAMING
                + SEPARATOR
                + "true"
                + NEXT_LINE
                + PREFIX
                + ValidatorParameters.PATH_NAMING_CONVENTION
                + SEPARATOR_QUOTE
                + ValidatorParameters.NamingConvention.HyphenCase
                + NEXT_LINE_QUOTE
                + PREFIX
                + ValidatorParameters.HEADER_NAMING_CONVENTION
                + SEPARATOR_QUOTE
                + ValidatorParameters.NamingConvention.UnderscoreUpperCase
                + NEXT_LINE_QUOTE
                + PREFIX
                + ValidatorParameters.PARAMETER_NAMING_CONVENTION
                + SEPARATOR_QUOTE
                + ValidatorParameters.NamingConvention.CamelCase
                + NEXT_LINE_QUOTE
                + PREFIX
                + ValidatorParameters.SCHEMA_NAMING_CONVENTION
                + SEPARATOR_QUOTE
                + ValidatorParameters.NamingConvention.PascalCase
                + NEXT_LINE_QUOTE
                + PREFIX
                + ValidatorParameters.PROPERTY_NAMING_CONVENTION
                + SEPARATOR_QUOTE
                + ValidatorParameters.NamingConvention.CamelCase
                + "\""
                + System.lineSeparator()
                + "}";
        assertEquals(sb, content);
    }
}
