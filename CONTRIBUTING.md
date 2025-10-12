# Guidelines For Contributing

## Before submitting an issue

 - If you're not using the latest master to generate API clients or server stubs, please give it another try by pulling the latest master as the issue may have already been addressed.
 - Search the [open issue](https://github.com/openapitools/openapi-style-validator/issues) and [closed issue](https://github.com/openapitools/openapi-style-validator/issues?q=is%3Aissue+is%3Aclosed) to ensure no one else has reported something similar before.
 - File an [issue ticket](https://github.com/openapitools/openapi-style-validator/issues/new) by providing all the required information.
 - Test with the latest master by building the JAR locally to see if the issue has already been addressed.
 - You can also make a suggestion or ask a question by opening an "issue".

## Before submitting a PR

 - Search the [open issue](https://github.com/openapitools/openapi-style-validator/issues) to ensure no one else has reported something similar and no one is actively working on similar proposed change.
 - If no one has suggested something similar, open an ["issue"](https://github.com/openapitools/openapi-style-validator/issues) with your suggestion to gather feedback from the community.
 - It's recommended to **create a new git branch** for the change so that the merge commit message looks nicer in the commit history.

## How to contribute

### git

If you're new to git, you may find the following FAQs useful:

https://github.com/openapitools/openapi-generator/wiki/FAQ#git

### Branches

Please file the pull request against the correct branch. Most of the time it will be `master`.

### Testing

It is not required but highly suggested that you create unit tests for the code you write. Also, make sure to run the existing tests before creating your PR.

### Tips
- Smaller changes are easier to review
- [Optional] For bug fixes, provide an OpenAPI Spec to repeat the issue so that the reviewer can use it to confirm the fix
- Add test case(s) to cover the change
- Make sure test cases passed after the change. All tests run after a PR is created or modified.
- File a PR with a meaningful title, description and commit messages.
- To close an issue (e.g. issue 1542) automatically after a PR is merged, use keywords "fix", "close", "resolve" in the PR description, e.g. `fix #1542`. (Ref: [closing issues using keywords](https://help.github.com/articles/closing-issues-using-keywords/))
