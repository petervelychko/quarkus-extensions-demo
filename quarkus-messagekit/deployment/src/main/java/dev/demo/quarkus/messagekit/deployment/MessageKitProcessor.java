package dev.demo.quarkus.messagekit.deployment;

import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.FeatureBuildItem;

/** The deployment artifact participates in Quarkus augmentation. */
public class MessageKitProcessor {

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem("messagekit");
    }
}
