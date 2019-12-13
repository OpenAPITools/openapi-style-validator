package org.openapitools.openapistylevalidator.gradle;

import static org.junit.Assert.assertNotNull;

import org.gradle.api.Project;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.Test;

/**
 * A simple unit test for the 'org.openapitools.openapistylevalidator' plugin.
 */
public class OpenAPIStyleValidatorGradlePluginTest {

    @Test
    public void pluginRegistersATask() {
        // Create a test project and apply the plugin
        Project project = ProjectBuilder.builder().build();
        project.getPlugins().apply("org.openapitools.openapistylevalidator");

        // Verify the result
        assertNotNull(project.getTasks().findByName("openAPIStyleValidator"));
    }
}
