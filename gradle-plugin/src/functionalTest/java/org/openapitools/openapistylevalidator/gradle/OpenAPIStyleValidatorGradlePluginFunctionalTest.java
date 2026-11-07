package org.openapitools.openapistylevalidator.gradle;

import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.GradleRunner;
import org.gradle.testkit.runner.TaskOutcome;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * A simple functional test for the 'org.openapitools.openapistylevalidator' plugin.
 */
public class OpenAPIStyleValidatorGradlePluginFunctionalTest {

    @Test
    public void validateWithOptionsMustBeASuccess() throws IOException {
        // Setup the test build
        File projectDir = new File("build/functionalTest");
        Files.createDirectories(projectDir.toPath());
        writeString(new File(projectDir, "openapi.yaml"),
                "openapi: 3.0.1\n" +
                        "info:\n" +
                        "  title: ping test\n" +
                        "  version: '1.0'\n" +
                        "servers:\n" +
                        "  - url: 'http://localhost:9999/'\n" +
                        "paths:\n" +
                        "  /ping:\n" +
                        "    post:\n" +
                        "      operationId: pingGet\n" +
                        "      responses:\n" +
                        "        '201':\n" +
                        "          description: OK");
        writeString(new File(projectDir, "settings.gradle"), "");
        writeString(new File(projectDir, "build.gradle"),
                "plugins {\n" +
                        "  id('org.openapitools.openapistylevalidator')\n" +
                        "}\n" +
                        "\n" +
                        "openAPIStyleValidator {\n" +
                        "    // set the input file option:\n" +
                        "    inputFile = file('openapi.yaml')\n" +
                        "\n" +
                        "    // customize the validation options:\n" +
                        "    validateInfoLicense = false\n" +
                        "    validateInfoDescription = false\n" +
                        "    validateInfoContact = false\n" +
                        "    validateOperationDescription = false\n" +
                        "    validateOperationTag = false\n" +
                        "    validateOperationSummary = false\n" +
                        "}");

        // Run the build
        GradleRunner runner = GradleRunner.create();
        runner.forwardOutput();
        runner.withPluginClasspath();
        runner.withArguments("openAPIStyleValidator");
        runner.withProjectDir(projectDir);
        BuildResult result = runner.build();

        // Verify the result
        assertEquals(TaskOutcome.SUCCESS, result.task(":openAPIStyleValidator").getOutcome());
    }

    /**
     * See <a href="https://github.com/OpenAPITools/openapi-style-validator/pull/810#discussion_r4502998987">the review discussion</a>:
     * a root tag without a description is a warning, it must not fail the build.
     */
    @Test
    public void validateWithOnlyWarningsMustBeASuccess() throws IOException {
        // Setup the test build
        File projectDir = new File("build/functionalTestWarning");
        Files.createDirectories(projectDir.toPath());
        writeString(new File(projectDir, "openapi.yaml"),
                "openapi: 3.0.1\n" +
                        "info:\n" +
                        "  title: ping test\n" +
                        "  version: '1.0'\n" +
                        "servers:\n" +
                        "  - url: 'http://localhost:9999/'\n" +
                        "tags:\n" +
                        "  - name: demo\n" +
                        "paths:\n" +
                        "  /ping:\n" +
                        "    get:\n" +
                        "      operationId: pingGet\n" +
                        "      summary: ping operation\n" +
                        "      description: ping description\n" +
                        "      tags:\n" +
                        "        - demo\n" +
                        "      responses:\n" +
                        "        '201':\n" +
                        "          description: OK");
        writeString(new File(projectDir, "settings.gradle"), "");
        writeString(new File(projectDir, "build.gradle"),
                "plugins {\n" +
                        "  id('org.openapitools.openapistylevalidator')\n" +
                        "}\n" +
                        "\n" +
                        "openAPIStyleValidator {\n" +
                        "    inputFile = file('openapi.yaml')\n" +
                        "\n" +
                        "    validateInfoLicense = false\n" +
                        "    validateInfoDescription = false\n" +
                        "    validateInfoContact = false\n" +
                        "    validateOperationDescription = false\n" +
                        "    validateOperationSummary = false\n" +
                        "}");

        // Run the build
        GradleRunner runner = GradleRunner.create();
        runner.forwardOutput();
        runner.withPluginClasspath();
        runner.withArguments("openAPIStyleValidator");
        runner.withProjectDir(projectDir);
        BuildResult result = runner.build();

        // Verify the result
        assertEquals(TaskOutcome.SUCCESS, result.task(":openAPIStyleValidator").getOutcome());
        assertTrue(
                result.getOutput().contains(
                        "*WARNING* Section: OpenAPI: 'description' in tag 'demo' -> This field should be present and not empty"),
                result.getOutput());
    }

    /**
     * See <a href="https://github.com/OpenAPITools/openapi-style-validator/pull/810#discussion_r4502998987">the review discussion</a>:
     * an operation tag not declared in the root 'tags' section is still an error.
     */
    @Test
    public void validateWithUndeclaredOperationTagMustFail() throws IOException {
        // Setup the test build
        File projectDir = new File("build/functionalTestUndeclaredTag");
        Files.createDirectories(projectDir.toPath());
        writeString(new File(projectDir, "openapi.yaml"),
                "openapi: 3.0.1\n" +
                        "info:\n" +
                        "  title: ping test\n" +
                        "  version: '1.0'\n" +
                        "servers:\n" +
                        "  - url: 'http://localhost:9999/'\n" +
                        "paths:\n" +
                        "  /ping:\n" +
                        "    get:\n" +
                        "      operationId: pingGet\n" +
                        "      summary: ping operation\n" +
                        "      description: ping description\n" +
                        "      tags:\n" +
                        "        - undeclaredTag\n" +
                        "      responses:\n" +
                        "        '201':\n" +
                        "          description: OK");
        writeString(new File(projectDir, "settings.gradle"), "");
        writeString(new File(projectDir, "build.gradle"),
                "plugins {\n" +
                        "  id('org.openapitools.openapistylevalidator')\n" +
                        "}\n" +
                        "\n" +
                        "openAPIStyleValidator {\n" +
                        "    inputFile = file('openapi.yaml')\n" +
                        "\n" +
                        "    validateInfoLicense = false\n" +
                        "    validateInfoDescription = false\n" +
                        "    validateInfoContact = false\n" +
                        "    validateOperationDescription = false\n" +
                        "    validateOperationSummary = false\n" +
                        "}");

        // Run the build
        GradleRunner runner = GradleRunner.create();
        runner.forwardOutput();
        runner.withPluginClasspath();
        runner.withArguments("openAPIStyleValidator");
        runner.withProjectDir(projectDir);
        BuildResult result = runner.buildAndFail();

        // Verify the result
        assertEquals(TaskOutcome.FAILED, result.task(":openAPIStyleValidator").getOutcome());
        assertTrue(
                result.getOutput().contains(
                        "*ERROR* in Operation GET /ping 'tags' -> The following tags are used but not declared in the root 'tags' section: undeclaredTag"),
                result.getOutput());
    }

    private void writeString(File file, String string) throws IOException {
        try (Writer writer = new FileWriter(file)) {
            writer.write(string);
        }
    }
}
