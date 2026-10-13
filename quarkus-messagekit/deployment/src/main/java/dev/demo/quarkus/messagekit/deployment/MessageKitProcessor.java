package dev.demo.quarkus.messagekit.deployment;

import static io.quarkus.deployment.annotations.ExecutionTime.RUNTIME_INIT;

import jakarta.inject.Singleton;

import dev.demo.messagekit.MessageCatalog;
import dev.demo.quarkus.messagekit.runtime.MessageFormatterProducer;
import dev.demo.quarkus.messagekit.runtime.MessageKitRecorder;

import io.quarkus.arc.deployment.AdditionalBeanBuildItem;
import io.quarkus.arc.deployment.SyntheticBeanBuildItem;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.annotations.Record;
import io.quarkus.deployment.builditem.FeatureBuildItem;

/**
 * This class belongs only to the deployment artifact. It runs during
 * Quarkus augmentation and is not an application runtime component.
 */
public class MessageKitProcessor {

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem("messagekit");
    }

    @BuildStep
    AdditionalBeanBuildItem registerFormatterProducer() {
        // Register a bean that otherwise would not be indexed and discovered.
        return AdditionalBeanBuildItem.builder()
                .addBeanClass(MessageFormatterProducer.class)
                .setUnremovable()
                .build();
    }

    @BuildStep
    @Record(RUNTIME_INIT)
    SyntheticBeanBuildItem createMessageCatalog(MessageKitRecorder recorder) {
        // The language list is established during augmentation. The actual
        // MessageCatalog object is created when the application starts.
        return SyntheticBeanBuildItem.configure(MessageCatalog.class)
                .scope(Singleton.class)
                .setRuntimeInit()
                .runtimeValue(recorder.createCatalog("Hello", "Hola", "Pozdrav"))
                .done();
    }
}
