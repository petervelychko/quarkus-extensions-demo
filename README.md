# quarkus-extensions-demo

Stage demo-03: `@ConfigMapping` adds `quarkus.messagekit.language`,
`quarkus.messagekit.prefix`, and `quarkus.messagekit.uppercase`.
The optional prefix avoids empty-string configuration validation failures.

Build with JDK 25: `mvn clean install`.
Edit `demo-app/src/main/resources/application.properties` to try `language=hr`.
