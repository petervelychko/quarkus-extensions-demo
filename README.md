# quarkus-extensions-demo

A complete Maven multi-module example for the talk "Building Quarkus Extensions:
A Practical Guide for Java Developers". The project is designed to show the
difference between a plain Java library and an idiomatic Quarkus extension.

## Prerequisites

- JDK 25 (Maven must run on JDK 25 or newer)
- Maven 3.9.16 or later
- An internet connection on first build to retrieve Maven dependencies

Quarkus version: 3.40.1 (LTS)
Maven groupId: dev.demo (all project modules)
Java source/bytecode target: 25 (configured using maven.compiler.release)

## Modules

- `messagekit-core`: Plain Java library with no Quarkus dependency.
- `quarkus-messagekit/runtime`: Config mapping, CDI producer, recorder, extension descriptor.
- `quarkus-messagekit/deployment`: Build steps, bean registration, synthetic bean, extension tests.
- `demo-app`: Quarkus REST application which depends only on the runtime extension artifact.

## Build and test everything

Confirm the active JDK first:

```bash
java -version
mvn -version
```

Both should report Java 25 or newer. From the repository root:

```bash
mvn clean install
```

The build targets Java 25 and runs the plain library tests, extension
deployment tests, and consuming application tests. It also installs all modules into your
local Maven repository, so the deployment artifact can be resolved by Quarkus.

## Test naming convention

All JUnit tests use `@DisplayName("...")` to describe the expected behavior in
plain English. Keep test method names meaningful, and add a descriptive
`@DisplayName` to every new test method. Test data includes both English
and Croatian examples. The catalog also retains a Spanish example.

## Run the demo application

```bash
cd demo-app
mvn quarkus:dev
```

In another terminal:

```bash
curl http://localhost:8080/hello
# Hello Quarkus

curl http://localhost:8080/hello/Ada
# Hello Ada
```

To change the behavior, edit `demo-app/src/main/resources/application.properties`:

```properties
quarkus.messagekit.language=hr
quarkus.messagekit.prefix=Poruka
quarkus.messagekit.uppercase=true
```

Expected response for `/hello` with these settings:

```text
PORUKA POZDRAV QUARKUS
```


## Live demo checkpoints (real Git tags)

The ZIP includes the `.git` directory and six annotated Git tags with distinct
commits. Extract the entire archive (including hidden files) to preserve them.
The `main` branch points to the final working project; `demo-final` tags it.

| Tag | What the audience sees |
| --- | --- |
| `demo-00` | Plain Java library and manual CDI wiring in the demo application |
| `demo-01` | Extension runtime and deployment Maven modules, metadata, first build step |
| `demo-02` | Register a CDI producer with `AdditionalBeanBuildItem` |
| `demo-03` | Quarkus runtime configuration with `@ConfigMapping` |
| `demo-04` | `@Recorder`, `@Record(RUNTIME_INIT)` and synthetic bean |
| `demo-05` | Extension tests with English and Croatian test data |
| `demo-final` | Complete project and this README |

To view all tags and the history:

```bash
git tag -n
git log --oneline --decorate --graph --all
```

Switch to a checkpoint from the repository root, and rebuild before running:

```bash
git switch --detach demo-02
mvn clean install
cd demo-app
mvn quarkus:dev
```

For the next stage, stop dev mode, return to the repository root, and run:

```bash
git switch --detach demo-03
mvn clean install
```

To return to the complete project:

```bash
git switch main
mvn clean install
```

Git checks out a detached HEAD when you select a tag. For live coding, make a
separate throwaway branch at the chosen checkpoint so you can commit or discard
changes without rewriting the prepared tags:

```bash
git switch -c live-demo demo-02
# Make your live changes here.
# Before restarting from demo-02, delete the throwaway branch if desired.
```

For a clean restore to a tagged state, stash or commit local edits first,
and avoid reusing a running dev-mode process after switching tags.
Because checkpoints change the Maven reactor and extension artifacts, run
`mvn clean install` at each stage to avoid stale local artifacts.

## How the extension works

1. The runtime extension artifact includes metadata pointing Quarkus to the
   matching `quarkus-messagekit-deployment` artifact. Maven generates the
   bootstrap descriptor with `quarkus-extension-maven-plugin`.
2. `MessageKitProcessor` is loaded at augmentation time. `@BuildStep` methods
   produce build items such as `FeatureBuildItem` and `AdditionalBeanBuildItem`.
3. `AdditionalBeanBuildItem` makes `MessageFormatterProducer` available for CDI.
4. `MessageKitRecorder` supplies a `MessageCatalog` synthetic CDI bean. The
   build step runs during augmentation, but `createCatalog()` is recorded
   for application runtime initialization via `@Record(RUNTIME_INIT)`.
5. The producer combines the catalog with runtime configuration to create a
   `MessageFormatter`, which consuming applications can inject.

The optional `quarkus.messagekit.prefix` configuration is mapped to
`Optional<String>` and is treated as an empty prefix when unset or explicitly
empty. Do not use `@WithDefault("")` for a mandatory `String` config mapping:
SmallRye Config treats an empty string as a missing value during conversion.

The example message catalog supports English (`en`, `Hello`), Croatian
(`hr`, `Pozdrav`), and Spanish (`es`, `Hola`). English is the default and
the fallback for an unknown language code.

The recorder is intentionally small. In a production extension it might
prepare more substantial metadata or initialize runtime-only resources.
This project demonstrates the lifecycle; it is not a benchmark claiming
that a recorder makes this trivial formatting library faster.

## Where to look first

- `messagekit-core/src/main/java/dev/demo/messagekit/MessageFormatter.java`
- `quarkus-messagekit/deployment/src/main/java/dev/demo/quarkus/messagekit/deployment/MessageKitProcessor.java`
- `quarkus-messagekit/runtime/src/main/java/dev/demo/quarkus/messagekit/runtime/MessageKitRecorder.java`
- `quarkus-messagekit/runtime/src/main/java/dev/demo/quarkus/messagekit/runtime/MessageKitConfig.java`
- `demo-app/src/main/java/dev/demo/app/GreetingResource.java`

## Useful commands

```bash
# Rebuild everything and rerun tests (after the first mvn install)
mvn clean install

# Package the example application (after mvn install)
cd demo-app
mvn package
java -jar target/quarkus-app/quarkus-run.jar
```

## Official documentation

- https://quarkus.io/guides/building-my-first-extension
- https://quarkus.io/guides/writing-extensions
- https://quarkus.io/guides/cdi-integration
- https://quarkus.io/guides/config-mappings
