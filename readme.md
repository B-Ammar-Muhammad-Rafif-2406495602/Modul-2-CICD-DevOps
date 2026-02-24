
## REFLECTION

1. One of the code quality issues I fixed during this exercise was the AvoidDuplicateLiterals warning found in ProductRepositoryTest.java. 
PMD detected that the string literal "Sampo Cap Bambang" was hardcoded 14 times throughout the file.
My strategy for fixing this was to extract the repeated string into a constant class variable (private final String testProductName). 
I then replaced all hardcoded instances with this variable.
This not only resolved the PMD warning but also improved the maintainability of the code, as any future name changes will now only require updating a single variable.

2. Yes, the current implementation successfully meets the definitions of both Continuous Integration (CI) and Continuous Deployment (CD). 
For CI, the GitHub Actions workflows are configured to automatically trigger on every push, 
running the Gradle build and PMD code quality scans to ensure that new code integrates cleanly without breaking the existing codebase.
For CD, the pipeline is set up so that whenever verified code is merged into the main branch,
a deployment workflow automatically builds the Docker image and pushes it to our PaaS platform (Koyeb). 
This automated pipeline ensures every validated change is seamlessly delivered to the live production environment without requiring manual deployment steps.