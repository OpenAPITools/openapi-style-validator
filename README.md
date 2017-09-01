# swagger-style-validator
A customizable style validator to make sure your Swagger/OpenApi spec follows your organization's standards.

[![Build Status](https://travis-ci.org/JaffSoft/swagger-style-validator.svg?branch=master)](https://travis-ci.org/JaffSoft/swagger-style-validator)

[![Coverage Status](https://coveralls.io/repos/github/JaffSoft/swagger-style-validator/badge.svg?branch=master)](https://coveralls.io/github/JaffSoft/swagger-style-validator?branch=master)

## Summary

Unless you are working alone on a very small API, you are probably working with other people on your spec (YAML) file. 
Even if you have writen your directions and standards on a PDF or on a wiki somewhere and sent it to all the team 
members, there is no garanty they will follow it.

You could argue that you will do code review to prevent these errors. Yes you could but wouldn't it be better if you
could automatize all of this? You can still do code reviews to find business/requirements/REST path errors
but let swagger-style-validator validate the style and standards like mandatory examples, naming conventions, 
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
- Enforce naming strategy for paths and parameters
    - underscore_case
    - camelCase
    - hyphen-case

## Paid Alternatives

This project was started because when I tried SwaggerHub (which is the paid version of the swagger toolkit), they 
something similar, but it was proprietary. I didn't want to pay for the service so I created this project in my free 
times.

## How to use swagger-style-validator

For now, the project is a simple command line interface (CLI). It is in the roadmap to split the project in both a CLI
and a library so that people can integrate it in other software and tools but it's not ready as we speek.

### To build

The project is configured to use gradle. To build the jar, just do:

`gradlew assemble`

### To change the code

If you want to open the project, I highly suggest that you use IntelliJ IDEA Community (Free) or Ultimate (paid).
It is also cross platform so it should work on any OS. This project was created using this tool and you can just open 
it and shoud work. Maybe you can use other tools to open the project but I will not provide any support.

### To launch

`java -jar swagger-style-validator.jar ./path/to/spec.yaml`

## To the official swagger team (swagger.io)

I am very open to transfer this project to your official organization as long as I remain in charge of the project.
Please contact me in private if it's something that might interest you.

## Roadmap

This is in no specific order

- Split the code into a CLI using a library
- Try to reach 100% unit test code coverage
- Make an official release
- Make it available on Maven
- Add some validations based on feedback

## Maven / Releases

The tool has not been released officialy yet. Your only option for now is to download the source code and build it.