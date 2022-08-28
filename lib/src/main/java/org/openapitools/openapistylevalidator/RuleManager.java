package org.openapitools.openapistylevalidator;

import static java.util.stream.Collectors.toList;
import static org.reflections.scanners.Scanners.SubTypes;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import org.openapitools.openapistylevalidator.api.IRuleManager;
import org.openapitools.openapistylevalidator.api.Rule;
import org.openapitools.openapistylevalidator.naming.NamingChecker;
import org.reflections.Reflections;
import org.reflections.Store;
import org.reflections.util.QueryFunction;

public class RuleManager implements IRuleManager {

    private static final Logger logger = Logger.getLogger(RuleManager.class.getName());

    private final List<String> ignoredRules;

    private final NamingChecker checker;

    public RuleManager(List<String> ignoredRules, NamingChecker checker) {
        this.ignoredRules = ignoredRules;
        this.checker = checker;
    }


    @Override
    public List<Rule> loadRules() {
        Reflections reflections = new Reflections();
        QueryFunction<Store, Class<?>> ruleQueryFunction =
                SubTypes.of(Rule.class).asClass();
        return reflections.get(ruleQueryFunction).stream()
                .map(subType -> instantiateRule(subType.getName()))
                .filter(Objects::nonNull)
                .filter(rule -> !ignoredRules.contains(rule.ruleName()))
                .collect(toList());
    }

    private Rule instantiateRule(String className) {
        try {
            Class<?> aClass = Class.forName(className);
            boolean isNamingCheckerNeeded = Arrays.stream(aClass.getDeclaredConstructors())
                    .map(Constructor::getParameterTypes)
                    .filter(params -> params.length == 1)
                    .flatMap(Arrays::stream)
                    .anyMatch(clz -> clz.isAssignableFrom(NamingChecker.class));
            if (isNamingCheckerNeeded) {
                return instantiate(aClass, checker);
            }
            return instantiate(aClass);
        } catch (ClassNotFoundException e) {
            logger.severe("Could not load rule.  name=" + className + " reason=" + e.getMessage());
            return null;
        }
    }

    private Rule instantiate(Class<?> clazz) {
        try {
            return (Rule) clazz.getConstructor().newInstance();
        } catch (Exception e) {
            logger.severe("Could not instantiate the rule. name=" + clazz.getName() + " reason=" + e.getMessage());
            return null;
        }
    }

    private Rule instantiate(Class<?> clazz, NamingChecker namingChecker) {
        try {
            return (Rule) clazz.getConstructor(NamingChecker.class).newInstance(namingChecker);
        } catch (Exception e) {
            logger.severe("Could not instantiate the rule which accepts config type in the constructor. name="
                    + clazz.getName() + " reason=" + e.getMessage());
            return null;
        }
    }
}
