package dev.demo.quarkus.messagekit.runtime;

import java.util.Map;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;

import dev.demo.messagekit.MessageCatalog;
import dev.demo.messagekit.MessageFormatter;

/** Provides reusable CDI integration for any application using the extension. */
@ApplicationScoped
public class MessageFormatterProducer {

    @Produces
    @Singleton
    public MessageCatalog catalog() {
        return new MessageCatalog(Map.of("en", "Hello", "es", "Hola", "hr", "Pozdrav"));
    }

    @Produces
    @Singleton
    public MessageFormatter formatter(MessageCatalog catalog) {
        return new MessageFormatter(catalog, "en", "", false);
    }
}
