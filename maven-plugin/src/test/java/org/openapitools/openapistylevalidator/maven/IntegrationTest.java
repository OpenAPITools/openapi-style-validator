package org.openapitools.openapistylevalidator.maven;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;

import io.takari.maven.testing.TestResources;
import io.takari.maven.testing.executor.MavenExecutionResult;
import io.takari.maven.testing.executor.MavenRuntime;
import io.takari.maven.testing.executor.MavenRuntime.MavenRuntimeBuilder;
import io.takari.maven.testing.executor.MavenVersions;
import io.takari.maven.testing.executor.junit.MavenJUnitTestRunner;

@RunWith(MavenJUnitTestRunner.class)
@MavenVersions({"3.6.3"})
public class IntegrationTest {

  @Rule
  public final TestResources resources = new TestResources("src/test/resources/projects", "build/maven-work");

  private final MavenRuntime maven;

  public IntegrationTest(MavenRuntimeBuilder builder) throws Exception {
    this.maven = builder.build();
  }

  @Test
  public void withEmptyPomShouldDisplaySixErrors() throws Exception {
    Path file = Paths.get("../cli/src/test/resources/ping.yaml");
    Assertions.assertTrue(Files.isRegularFile(file));

    File basedir = resources.getBasedir("testEmpty");
    MavenExecutionResult result = maven.forProject(basedir)
        .execute("verify", "-X", "-Dit.inputFile=" + file.normalize().toAbsolutePath());

    sixErrorsAssertions(result);
  }

  @Test
  public void withDefaultPomShouldDisplaySixErrors() throws Exception {
    Path file = Paths.get("../cli/src/test/resources/ping.yaml");
    Assertions.assertTrue(Files.isRegularFile(file));

    File basedir = resources.getBasedir("testDefault");
    MavenExecutionResult result = maven.forProject(basedir)
        .execute("verify", "-X", "-Dit.inputFile=" + file.normalize().toAbsolutePath());

    sixErrorsAssertions(result);
  }

  private void sixErrorsAssertions(MavenExecutionResult result) {
    result
        .assertLogText("Validating spec:")
        .assertLogText(Paths.get("cli", "src", "test", "resources", "ping.yaml").toString())
        .assertLogText("OpenAPI Specification does not meet the requirements. Issues:")
        .assertLogText("*ERROR* Section: APIInfo: 'license' -> Should be present and not empty")
        .assertLogText("*ERROR* Section: APIInfo: 'description' -> Should be present and not empty")
        .assertLogText("*ERROR* Section: APIInfo: 'contact' -> Should be present and not empty")
        .assertLogText("*ERROR* in Operation POST /ping 'description' -> This field should be present and not empty")
        .assertLogText("*ERROR* in Operation POST /ping 'summary' -> This field should be present and not empty")
        .assertLogText("*ERROR* in Operation POST /ping 'tags' -> The collection should be present and there should be at least one item in it")
        .assertLogText("BUILD FAILURE");
  }

  @Test
  public void withCustomPomShouldDisplaySixErrors() throws Exception {
    Path file = Paths.get("../cli/src/test/resources/ping.yaml");
    Assertions.assertTrue(Files.isRegularFile(file));

    File basedir = resources.getBasedir("testCustom");
    MavenExecutionResult result = maven.forProject(basedir)
        .execute("verify", "-X", "-Dit.inputFile=" + file.normalize().toAbsolutePath());

    result
        .assertLogText("Validating spec:")
        .assertLogText(Paths.get("cli", "src", "test", "resources", "ping.yaml").toString())
        .assertLogText("BUILD SUCCESS");
  }

  @Test
  public void withEmptyPomShouldDisplayNamingErrors() throws Exception {
    Path file = Paths.get("../cli/src/test/resources/some.yaml");
    Assertions.assertTrue(Files.isRegularFile(file));

    File basedir = resources.getBasedir("testCustom");
    MavenExecutionResult result = maven.forProject(basedir)
        .execute("verify", "-X", "-Dit.inputFile=" + file.normalize().toAbsolutePath());

    result
        .assertLogText("Validating spec:")
        .assertLogText(Paths.get("cli", "src", "test", "resources", "some.yaml").toString())
        .assertLogText("OpenAPI Specification does not meet the requirements. Issues:")
        .assertLogText("*ERROR* in path POST /some_path/{some_id} 'some_id' -> path parameter should be in camelCase")
        .assertLogText("*ERROR* in path POST /some_path/{some_id} 'some_name' -> query parameter should be in camelCase")
        .assertLogText("*ERROR* in path /some_path/{some_id} 'some_path' -> path should be in hyphen-case")
        .assertLogText("BUILD FAILURE");
  }
}
