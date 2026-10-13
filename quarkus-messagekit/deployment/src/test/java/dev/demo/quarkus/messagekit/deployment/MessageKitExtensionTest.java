package dev.demo.quarkus.messagekit.deployment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import jakarta.inject.Inject;

import dev.demo.messagekit.MessageFormatter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import io.quarkus.test.QuarkusExtensionTest;

/** Verifies the extension from the point of view of a consuming application. */
public class MessageKitExtensionTest {

    @RegisterExtension
    static final QuarkusExtensionTest app = new QuarkusExtensionTest()
            .withEmptyApplication();

    @Inject
    MessageFormatter formatter;

    @Test
    @DisplayName("Registers an injectable formatter with default configuration")
    void formatterIsRegisteredWithDefaultConfiguration() {
        assertEquals("Hello Quarkus", formatter.format("Quarkus"));
    }
}
