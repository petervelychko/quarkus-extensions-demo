package dev.demo.quarkus.messagekit.deployment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import jakarta.inject.Inject;

import dev.demo.messagekit.MessageFormatter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import io.quarkus.test.QuarkusExtensionTest;

public class MessageKitSpanishExtensionTest {

    @RegisterExtension
    static final QuarkusExtensionTest app = new QuarkusExtensionTest()
            .withEmptyApplication()
            .overrideRuntimeConfigKey("quarkus.messagekit.language", "es")
            .overrideRuntimeConfigKey("quarkus.messagekit.prefix", "Welcome")
            .overrideRuntimeConfigKey("quarkus.messagekit.uppercase", "true");

    @Inject
    MessageFormatter formatter;

    @Test
    @DisplayName("Applies configured language prefix and uppercase formatting")
    void runtimeConfigurationChangesTheMessage() {
        assertEquals("WELCOME HOLA QUARKUS", formatter.format("Quarkus"));
    }
}
