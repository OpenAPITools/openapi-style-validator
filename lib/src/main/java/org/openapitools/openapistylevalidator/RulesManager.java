package org.openapitools.openapistylevalidator;

import static java.util.stream.Collectors.toList;
import static org.openapitools.openapistylevalidator.rules.ContactInfoRule.CONTACT_INFO;
import static org.openapitools.openapistylevalidator.rules.InfoDescriptionRule.INFO_DESCRIPTION;
import static org.openapitools.openapistylevalidator.rules.LicenseRule.LICENCE_INFO;
import static org.reflections.scanners.Scanners.SubTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import org.openapitools.openapistylevalidator.api.Rule;
import org.reflections.Reflections;
import org.reflections.Store;
import org.reflections.util.QueryFunction;

public class RulesManager {

    private static final Logger logger = Logger.getLogger(RulesManager.class.getName());

    public List<String> convertToIgnoredRules(ValidatorParameters parameters) {
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
        return ignoredRules;
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
            Class<?> aClass = Class.forName(className);
            return (Rule) aClass.newInstance();
        } catch (Exception e) {
            logger.severe("Could not instantiate the rule name=" + className + " reason=" + e.getMessage());
            return null;
        }
    }
}
