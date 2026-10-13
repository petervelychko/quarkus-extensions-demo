package dev.demo.app;

import java.util.Map;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;

import dev.demo.messagekit.MessageCatalog;
import dev.demo.messagekit.MessageFormatter;

/** Manual CDI wiring inside one application, before we have an extension. */
@ApplicationScoped
public class MessageFormatterProducer {

    @Produces
    @Singleton
    MessageFormatter formatter() {
        MessageCatalog catalog = new MessageCatalog(Map.of(
                "en", "Hello", "es", "Hola", "hr", "Pozdrav"));
        return new MessageFormatter(catalog, "en", "", false);
    }
}
