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
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.FileVisitResult;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    public void consumerSourceBuildMustWorkWithModernGradle() throws IOException {
        // Reproduces the report of issue #735: the plugin applied straight from this
        // repository's build logic, like a consumer build does with a source control
        // dependency. Gradle evaluates the build files of the source build with the
        // Gradle version of the consumer, so they must be compatible with the modern
        // Gradle versions of consumer builds.
        File workingDirectory = new File("build/functionalTest735");
        deleteRecursively(new File(workingDirectory, "source-build"));
        deleteRecursively(new File(workingDirectory, "consumer"));

        File sourceBuild = new File(workingDirectory, "source-build");
        copyDirectory(new File("..").getCanonicalFile(), sourceBuild);

        File projectDir = new File(workingDirectory, "consumer");
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
        writeString(new File(projectDir, "settings.gradle"),
                "pluginManagement {\n" +
                        "    includeBuild('../source-build')\n" +
                        "}\n" +
                        "\n" +
                        "rootProject.name = 'issue-735-consumer'");
        writeString(new File(projectDir, "build.gradle"),
                "plugins {\n" +
                        "    id 'org.openapitools.openapistylevalidator'\n" +
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

        // Run the build of the consumer with a modern Gradle version,
        // like the Gradle 8.6 of the issue report
        GradleRunner runner = GradleRunner.create();
        runner.forwardOutput();
        runner.withGradleVersion("8.6");
        runner.withTestKitDir(new File(workingDirectory, "testkit").getCanonicalFile());
        runner.withArguments("openAPIStyleValidator");
        runner.withProjectDir(projectDir);
        BuildResult result = runner.build();

        // Verify the result
        assertEquals(TaskOutcome.SUCCESS, result.task(":openAPIStyleValidator").getOutcome());
    }

    private void deleteRecursively(File root) throws IOException {
        if (!root.exists()) {
            return;
        }
        Files.walkFileTree(root.toPath(), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path directory, IOException exception) throws IOException {
                Files.delete(directory);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    private void copyDirectory(File source, File target) throws IOException {
        // Skip what the Gradle build does not need and what is too big to copy,
        // most notably the build outputs that would conflict with the ones of the
        // copied source build
        Set<String> excludedDirectories = new HashSet<>(Arrays.asList(
                ".git", ".github", ".gradle", ".idea", "build", "docs", "node_modules", "website"));
        Files.walkFileTree(source.toPath(), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path directory, BasicFileAttributes attributes) {
                if (!source.toPath().equals(directory)
                        && excludedDirectories.contains(directory.getFileName().toString())) {
                    return FileVisitResult.SKIP_SUBTREE;
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) throws IOException {
                Path targetPath = target.toPath().resolve(source.toPath().relativize(file));
                Files.createDirectories(targetPath.getParent());
                Files.copy(file, targetPath, StandardCopyOption.REPLACE_EXISTING);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    private void writeString(File file, String string) throws IOException {
        try (Writer writer = new FileWriter(file)) {
            writer.write(string);
        }
    }
}
