# quarkus-extensions-demo

Stage demo-04: `MessageKitRecorder` arranges runtime creation of a
`MessageCatalog` synthetic CDI bean, while the build step still runs during
Quarkus augmentation. See `@Record(RUNTIME_INIT)` in `MessageKitProcessor`.

Build with JDK 25: `mvn clean install`.
