package dev.demo.quarkus.messagekit.deployment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import jakarta.inject.Inject;

import dev.demo.messagekit.MessageFormatter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import io.quarkus.test.QuarkusExtensionTest;

public class MessageKitCroatianExtensionTest {

    @RegisterExtension
    static final QuarkusExtensionTest app = new QuarkusExtensionTest()
            .withEmptyApplication()
            .overrideRuntimeConfigKey("quarkus.messagekit.language", "hr")
            .overrideRuntimeConfigKey("quarkus.messagekit.prefix", "Poruka")
            .overrideRuntimeConfigKey("quarkus.messagekit.uppercase", "true");

    @Inject
    MessageFormatter formatter;

    @Test
    @DisplayName("Applies Croatian language, prefix, and uppercase formatting")
    void croatianRuntimeConfigurationChangesTheMessage() {
        assertEquals("PORUKA POZDRAV ANA", formatter.format("Ana"));
    }
}
