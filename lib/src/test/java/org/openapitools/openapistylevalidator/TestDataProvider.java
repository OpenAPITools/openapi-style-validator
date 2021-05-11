package org.openapitools.openapistylevalidator;

class TestDataProvider {
    /**
     * With these parameters the validator should skip all checks
     */
    static ValidatorParameters createParametersDisablingAllValidations() {
        return new ValidatorParameters()
                .setValidateInfoLicense(false)
                .setValidateInfoDescription(false)
                .setValidateInfoContact(false)
                .setValidateOperationOperationId(false)
                .setValidateOperationDescription(false)
                .setValidateOperationTag(false)
                .setValidateOperationSummary(false)
                .setValidateModelPropertiesExample(false)
                .setValidateModelRequiredProperties(false)
                .setValidateModelNoLocalDef(false)
                .setValidateNaming(false);
    }
}
