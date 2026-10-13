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

The ZIP includes the `.git` directory and seven annotated Git tags with distinct
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

## Switching between demo stages

Use the executable `scripts/checkpoint.sh` from any directory inside the
repository (the script moves to the repository root automatically):

```bash
./scripts/checkpoint.sh 00  # Plain library and manual CDI wiring
./scripts/checkpoint.sh 01  # Extension skeleton
./scripts/checkpoint.sh 02  # CDI integration with a build step
./scripts/checkpoint.sh 03  # Configuration mapping
./scripts/checkpoint.sh 04  # Runtime recorder and synthetic bean
./scripts/checkpoint.sh 05  # Extension tests
./scripts/checkpoint.sh 06  # Final project
```

**Warning:** The script runs `git switch --detach --discard-changes`, which
permanently discards uncommitted changes to tracked files. Commit or stash
anything you want to keep. Untracked files are not automatically deleted.

The script is committed in **every checkpoint**, including `demo-00`, so it
remains available after switching between tags. It leaves Git in detached
HEAD state, which is appropriate for a read-only stage demonstration.

To show what changes between stages:

```bash
git diff demo-02 demo-03
```

Rebuild after switching stages because the Maven module graph and extension
artifacts can differ. Stop a running Quarkus dev-mode process before switching:

```bash
mvn clean install
cd demo-app
mvn quarkus:dev
```

To return to the `main` branch instead of detached `demo-final`, use:

```bash
git switch main
```

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
