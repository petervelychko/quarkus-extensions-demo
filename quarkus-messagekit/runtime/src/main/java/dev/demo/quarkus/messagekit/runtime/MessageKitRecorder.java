package dev.demo.quarkus.messagekit.runtime;

import java.util.Map;

import dev.demo.messagekit.MessageCatalog;

import io.quarkus.runtime.RuntimeValue;
import io.quarkus.runtime.annotations.Recorder;

/**
 * A recorder arranges runtime initialization using values chosen during
 * augmentation. It is not directly invoked by the real build processor.
 */
@Recorder
public class MessageKitRecorder {

    public RuntimeValue<MessageCatalog> createCatalog(String english, String spanish, String croatian) {
        return new RuntimeValue<>(new MessageCatalog(Map.of("en", english, "es", spanish, "hr", croatian)));
    }
}
