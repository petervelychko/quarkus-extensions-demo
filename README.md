# quarkus-extensions-demo

Java 25, Maven, and Quarkus 3.40.1. All modules use groupId `dev.demo`.

This repository contains a sequence of Git tags used for a Quarkus extension talk.
The current stage is demo-00: a plain Java library plus a Quarkus application
that integrates it manually with a CDI producer.

Run `mvn clean install`, then `cd demo-app && mvn quarkus:dev`.
Try `curl http://localhost:8080/hello` and `curl http://localhost:8080/hello/Ana`.
The catalog includes English, Croatian, and Spanish greetings.
