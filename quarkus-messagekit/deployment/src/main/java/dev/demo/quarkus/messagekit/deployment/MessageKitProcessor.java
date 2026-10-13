package dev.demo.quarkus.messagekit.deployment;

import dev.demo.quarkus.messagekit.runtime.MessageFormatterProducer;

import io.quarkus.arc.deployment.AdditionalBeanBuildItem;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.FeatureBuildItem;

public class MessageKitProcessor {

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem("messagekit");
    }

    @BuildStep
    AdditionalBeanBuildItem registerFormatterProducer() {
        return AdditionalBeanBuildItem.builder()
                .addBeanClass(MessageFormatterProducer.class)
                .setUnremovable()
                .build();
    }
}
