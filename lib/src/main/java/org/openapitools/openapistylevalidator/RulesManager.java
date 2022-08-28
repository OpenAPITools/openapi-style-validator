package org.openapitools.openapistylevalidator;

import static java.util.stream.Collectors.toList;
import static org.openapitools.openapistylevalidator.rules.ContactInfoRule.CONTACT_INFO;
import static org.openapitools.openapistylevalidator.rules.HeaderNamingRule.*;
import static org.openapitools.openapistylevalidator.rules.InfoDescriptionRule.INFO_DESCRIPTION;
import static org.openapitools.openapistylevalidator.rules.LicenseRule.LICENCE_INFO;
import static org.openapitools.openapistylevalidator.rules.ParameterNamingRule.PARAMETER_NAMING;
import static org.openapitools.openapistylevalidator.rules.PathNamingRule.*;
import static org.openapitools.openapistylevalidator.rules.PropertyNamingRule.PROPERTY_NAMING;
import static org.reflections.scanners.Scanners.SubTypes;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import org.openapitools.openapistylevalidator.api.Rule;
import org.openapitools.openapistylevalidator.naming.NamingChecker;
import org.reflections.Reflections;
import org.reflections.Store;
import org.reflections.util.QueryFunction;

public class RulesManager {

    private static final Logger logger = Logger.getLogger(RulesManager.class.getName());

    private final ValidatorParameters parameters;

    public RulesManager(ValidatorParameters parameters) {
        this.parameters = parameters;
    }

    public List<String> convertToIgnoredRules() {
        ArrayList<String> ignoredRules = new ArrayList<>();
        if (!parameters.isValidateInfoContact()) {
            ignoredRules.add(CONTACT_INFO);
        }
        if (!parameters.isValidateInfoLicense()) {
            ignoredRules.add(LICENCE_INFO);
        }
        if (!parameters.isValidateInfoDescription()) {
            ignoredRules.add(INFO_DESCRIPTION);
        }
        if (!parameters.isValidateNaming()) {
            ignoredRules.add(HEADER_NAMING);
            ignoredRules.add(PARAMETER_NAMING);
            ignoredRules.add(PATH_NAMING);
            ignoredRules.add(PROPERTY_NAMING);
        }
        return ignoredRules;
    }

    private NamingChecker toNamingChecker() {
        NamingChecker.Builder builder = new NamingChecker.Builder();
        builder.withParameterNamingChecker(parameters.getParameterNamingConvention());
        builder.withHeaderNamingChecker(parameters.getHeaderNamingConvention());
        builder.withPathNamingChecker(parameters.getPathNamingConvention());
        builder.withPropertyNamingChecker(parameters.getPropertyNamingConvention());
        return builder.build();
    }

    public List<Rule> loadAllRules() {
        Reflections reflections = new Reflections();
        QueryFunction<Store, Class<?>> ruleQueryFunction =
                SubTypes.of(Rule.class).asClass();
        return reflections.get(ruleQueryFunction).stream()
                .map(subType -> instantiateRule(subType.getName()))
                .filter(Objects::nonNull)
                .collect(toList());
    }

    private Rule instantiateRule(String className) {
        try {
            NamingChecker namingChecker = toNamingChecker();
            Class<?> aClass = Class.forName(className);
            boolean isNamingCheckerNeeded = Arrays.stream(aClass.getDeclaredConstructors())
                    .map(Constructor::getParameterTypes)
                    .filter(params -> params.length == 1)
                    .flatMap(Arrays::stream)
                    .anyMatch(clz -> clz.isAssignableFrom(NamingChecker.class));
            if (isNamingCheckerNeeded) {
                return instantiate(aClass, namingChecker);
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
