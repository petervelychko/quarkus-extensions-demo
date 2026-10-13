# quarkus-extensions-demo

Stage demo-01: the extension skeleton now exists, with separate runtime and
deployment artifacts. The application still uses its manual CDI producer.

Build with Java 25 and Maven: `mvn clean install`.
Inspect `quarkus-messagekit/runtime/pom.xml`, `quarkus-messagekit/deployment/pom.xml`,
and `MessageKitProcessor.java`.
