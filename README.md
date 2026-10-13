# quarkus-extensions-demo

Stage demo-02: `AdditionalBeanBuildItem` registers the runtime module's CDI
producer. The consuming application now depends on the extension and no longer
contains its own producer class.

Build with JDK 25: `mvn clean install`.
Inspect `MessageKitProcessor` and `MessageFormatterProducer`.
