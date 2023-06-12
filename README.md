## Pre-Requisite

- Java 18
- Install docker and psql
- Postgres - `docker run --name postgres -it -p 5431:5432 -e POSTGRES_USER=$USER -e POSTGRES_PASSWORD=$USER -e POSTGRES_DB=postgresql -d postgres`.

  - Connect postgres locally in port **5431** and db name as **postgresql**.
  
## Preferred IDE

- Intellij IDEA

## Commands
- Running the terminal: `./gradlew clean run`
- Building: `./gradlew clean build`

## Micronaut 3.9.2 Documentation

- [User Guide](https://docs.micronaut.io/3.9.2/guide/index.html)
- [API Reference](https://docs.micronaut.io/3.9.2/api/index.html)
- [Configuration Reference](https://docs.micronaut.io/3.9.2/guide/configurationreference.html)
- [Micronaut Guides](https://guides.micronaut.io/index.html)

---

- [Micronaut Gradle Plugin documentation](https://micronaut-projects.github.io/micronaut-gradle-plugin/latest/)
- [GraalVM Gradle Plugin documentation](https://graalvm.github.io/native-build-tools/latest/gradle-plugin.html)
- [Shadow Gradle Plugin](https://plugins.gradle.org/plugin/com.github.johnrengelman.shadow)

## Feature http-client documentation

- [Micronaut HTTP Client documentation](https://docs.micronaut.io/latest/guide/index.html#httpClient)


