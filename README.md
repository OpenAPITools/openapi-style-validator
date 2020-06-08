# OpenApi Tools: openapi-style-validator
A customizable style validator to make sure your OpenApi spec follows your organization's standards.

[![](https://img.shields.io/badge/Buy%20us%20a%20tree-%F0%9F%8C%B3-lightgreen)](https://offset.earth/darkjaff)

[![Build Status](https://travis-ci.org/OpenAPITools/openapi-style-validator.svg?branch=master)](https://travis-ci.org/OpenAPITools/openapi-style-validator) [![Maven Central](https://img.shields.io/maven-central/v/org.openapitools.openapistylevalidator/openapi-style-validator-cli)](https://mvnrepository.com/artifact/org.openapitools.openapistylevalidator) [![codecov](https://codecov.io/gh/OpenAPITools/openapi-style-validator/branch/master/graph/badge.svg)](https://codecov.io/gh/OpenAPITools/openapi-style-validator)

[![Join the Slack chat room](https://img.shields.io/badge/Slack-Join%20the%20chat%20room-orange)](https://join.slack.com/t/openapi-generator/shared_invite/enQtNzAyNDMyOTU0OTE1LTY5ZDBiNDI5NzI5ZjQ1Y2E5OWVjMjZkYzY1ZGM2MWQ4YWFjMzcyNDY5MGI4NjQxNDBiMTlmZTc5NjY2ZTQ5MGM)  [Official Website](https://openapitools.github.io/openapi-style-validator/)

## Summary

Unless you are working alone on a very small API, you are probably working with other people on your spec (YAML) file.
Even if you have written directions and standards in a shared PDF or on a wiki somewhere and sent it to all the team
members, there is no way to be sure they will follow it.

You could argue that you will do code review to prevent these errors. Yes you could but wouldn't it be better if you
could automatize all of this? You can still do code reviews to find business/requirements/REST path errors
but let openapi-style-validator validate the style and standards like mandatory examples, naming conventions,
description, etc...

## What it can validate

### API Info
- API license must be present and non-empty string
- API description must be present and non-empty string
- API contact must be present and non-empty string

### Operations
- OperationId must be present and non-empty string
- Operation description must be present and non-empty string
- Operation must have a tag and non-empty string
- Operation summary must be present and non-empty string

### Models
- All model properties must have examples

### Naming convention
- Enforce naming convention for paths, parameters and properties
    - underscore_case
    - camelCase
    - hyphen-case

## Paid Alternatives

This project was started because when I tried SwaggerHub (which is the paid version of the swagger toolkit), they had
something similar, but it was proprietary. I didn't want to pay for the service so I created this project in my free
times. I did not validate lately if they still have this feature.

## How to use the style validator

For now, the project is a simple command line interface (CLI) and a library. The easiest way to use it right now
is to use the CLI and check the output. It will list all errors found based on the options you provided.

### To build

The project is configured to use gradle. To build the jar, just do:

`gradlew assemble`

or if you want to invoke the jar creation directly

`gradlew shadowJar`

### To change the code

If you want to open the project, I highly suggest that you use IntelliJ IDEA Community (Free) or Ultimate (paid).
This IDE is cross platform so it should work on any OS. This project was created using this tool and you can just open it directly. Maybe you can use other tools to open the project but I will not provide any support.

### To launch

`java -jar openapi-style-validator.jar -s ./path/to/spec.yaml -o ./path/to/options.json`

Example using the default output path for the jar:

`java -jar modules/cli/build/libs/openapi-style-validator-cli-1.0.jar -s specs/petstore.yaml -o specs/options.json`

#### Command Line

|Parameter|Required?|Description|
|---|---|---|
|-s, -source|yes|The path to your json/yaml spec file|
|-o, -options|no|The path to your json options file|

#### Options File
The options file is described in json (example in `specs/options.json`), and has the following possible values:

|Option|Type|Possible Values|Description|
|---|---|---|---|
|validateInfoLicense|boolean|`true`, `false`|Ensures that there is a license section in the info section|
|validateInfoDescription|boolean|`true`, `false`|Ensures that there is a description attribute in the info section|
|validateInfoContact|boolean|`true`, `false`|Ensures that there is a contact section in the info section|
|validateOperationOperationId|boolean|`true`, `false`|Ensures that there is an operation id for each operation|
|validateOperationDescription|boolean|`true`, `false`|Ensures that there is a description for each operation|
|validateOperationTag|boolean|`true`, `false`|Ensures that there is a tag for each operation|
|validateOperationSummary|boolean|`true`, `false`|Ensures that there is a summary for each operation|
|validateModelPropertiesExample|boolean|`true`, `false`|Ensures that the properties of the Schemas have an example value defined|
|validateModelNoLocalDef|boolean|`true`, `false`|Not implemented yet|
|validateNaming|boolean|`true`, `false`|Ensures the names follow a given naming convention|
|ignoreHeaderXNaming|boolean|`true`, `false`|Exclude from validation header parameters starting with `x-`|
|pathNamingConvention|string|`CamelCase`, `HyphenCase`, `UnderscoreCase`|Naming convention for paths|
|parameterNamingConvention|string|`CamelCase`, `HyphenCase`, `UnderscoreCase`|Naming convention for parameters|
|propertiesNamingConvention|string|`CamelCase`, `HyphenCase`, `UnderscoreCase`|Naming convention for properties|

## Roadmap

In no specific order

- Try to reach 100% unit test code coverage
- Make an official release
- Make it available on Maven
- Add some validations based on feedback

## Releases on maven central

The different components of this project are released on Maven Central:

The core library:

```xml
<dependency>
  <groupId>org.openapitools.openapistylevalidator</groupId>
  <artifactId>openapi-style-validator-lib</artifactId>
  <version>${validatorVersion}</version>
</dependency>
```

The command line tool (without dependency):

```xml
<dependency>
  <groupId>org.openapitools.openapistylevalidator</groupId>
  <artifactId>openapi-style-validator-cli</artifactId>
  <version>${validatorVersion}</version>
</dependency>
```

The command line tool is also available as standalone jar (containing its dependencies):

```xml
<dependency>
  <groupId>org.openapitools.openapistylevalidator</groupId>
  <artifactId>openapi-style-validator-cli</artifactId>
  <version>${validatorVersion}</version>
  <classifier>all</classifier>
</dependency>
```

You can download it manually (replace the `<version>` placeholder in following URL):

```
https://repo1.maven.org/maven2/org/openapitools/openapistylevalidator/openapi-style-validator-cli/<version>/openapi-style-validator-cli-<version>-all.jar
```
