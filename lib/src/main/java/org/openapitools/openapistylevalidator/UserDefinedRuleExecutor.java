package org.openapitools.openapistylevalidator;

import org.eclipse.microprofile.openapi.models.OpenAPI;
import org.openapitools.openapistylevalidator.custom.OpenAPIRule;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

import static org.reflections.scanners.Scanners.SubTypes;

public class UserDefinedRuleExecutor {

    private ErrorAggregator errorAggregator;
    private OpenAPI openAPI;

    public UserDefinedRuleExecutor(ErrorAggregator errorAggregator, OpenAPI openAPI) {
        this.errorAggregator = errorAggregator;
        this.openAPI = openAPI;
    }

    public void loadAndExecuteRules() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Reflections reflections = new Reflections();
        Set<Class<?>> subTypes =
                reflections.get(SubTypes.of(OpenAPIRule.class).asClass());
        for (Class<?> subType : subTypes) {
            Object instance = Class.forName(subType.getName())
                    .getConstructor(ErrorAggregator.class)
                    .newInstance(errorAggregator);
            Method executeMethod = subType.getMethod("execute", OpenAPI.class);
            executeMethod.invoke(instance, openAPI);
        }
    }
}
