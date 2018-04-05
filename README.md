# openapi-style-validator
A customizable style validator to make sure your OpenApi spec follows your organization's standards.

[![Build Status](https://travis-ci.org/JaffSoft/swagger-style-validator.svg?branch=master)](https://travis-ci.org/JaffSoft/swagger-style-validator)

[![Coverage Status](https://coveralls.io/repos/github/JaffSoft/swagger-style-validator/badge.svg?branch=master)](https://coveralls.io/github/JaffSoft/swagger-style-validator?branch=master)

## Summary

Unless you are working alone on a very small API, you are probably working with other people on your spec (YAML) file.
Even if you have written directions and standards in a shared PDF or on a wiki somewhere and sent it to all the team
members, there is no way to be sure they will follow it.

You could argue that you will do code review to prevent these errors. Yes you could but wouldn't it be better if you
could automatize all of this? You can still do code reviews to find business/requirements/REST path errors
but let Jaffsoft openapi-style-validator validate the style and standards like mandatory examples, naming conventions,
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

### Naming strategy
- Enforce naming strategy for paths, parameters and properties
    - underscore_case
    - camelCase
    - hyphen-case

## Paid Alternatives

This project was started because when I tried SwaggerHub (which is the paid version of the swagger toolkit), they had
something similar, but it was proprietary. I didn't want to pay for the service so I created this project in my free
times. I did not validate lately if they still have this feature.

## How to use JaffSoft openapi-style-validator

For now, the project is a simple command line interface (CLI) and a library. The easiest way to use it right now
is to use the CLI and check the output. It will list all errors found based on the options you provided.

### To build

The project is configured to use gradle. To build the jar, just do:

`gradlew assemble`

or

```
gradle build
gradle fatJar
```

### To change the code

If you want to open the project, I highly suggest that you use IntelliJ IDEA Community (Free) or Ultimate (paid).
This IDE is cross platform so it should work on any OS. This project was created using this tool and you can just open
it directly. Maybe you can use other tools to open the project but I will not provide any support.

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

|Option|Type|Possible Values|
|---|---|---|
|validateInfoLicense|boolean|`true`, `false`|
|validateInfoDescription|boolean|`true`, `false`|
|validateInfoContact|boolean|`true`, `false`|
|validateOperationOperationId|boolean|`true`, `false`|
|validateOperationDescription|boolean|`true`, `false`|
|validateOperationTag|boolean|`true`, `false`|
|validateOperationSummary|boolean|`true`, `false`|
|validateModelPropertiesExample|boolean|`true`, `false`|
|validateModelNoLocalDef|boolean|`true`, `false`|
|validateNaming|boolean|`true`, `false`|
|pathNamingStrategy|string|`CamelCase`, `HyphenCase`, `UnderscoreCase`|
|parameterNamingStrategy|string|`CamelCase`, `HyphenCase`, `UnderscoreCase`|
|propertiesNamingStrategy|string|`CamelCase`, `HyphenCase`, `UnderscoreCase`|

## Roadmap

In no specific order

- Try to reach 100% unit test code coverage
- Make an official release
- Make it available on Maven
- Add some validations based on feedback

## Maven / Releases

The tool has not been released yet. Your only option for now is to download the source code and build it.
