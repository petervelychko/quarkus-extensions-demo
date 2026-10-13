package dev.demo.quarkus.messagekit.deployment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import jakarta.inject.Inject;

import dev.demo.messagekit.MessageFormatter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import io.quarkus.test.QuarkusExtensionTest;

/** An explicitly empty optional property must not prevent application startup. */
public class MessageKitEmptyPrefixExtensionTest {

    @RegisterExtension
    static final QuarkusExtensionTest app = new QuarkusExtensionTest()
            .withEmptyApplication()
            .overrideRuntimeConfigKey("quarkus.messagekit.prefix", "");

    @Inject
    MessageFormatter formatter;

    @Test
    @DisplayName("Accepts an explicitly empty optional prefix")
    void emptyPrefixIsAllowed() {
        assertEquals("Hello Quarkus", formatter.format("Quarkus"));
    }
}
