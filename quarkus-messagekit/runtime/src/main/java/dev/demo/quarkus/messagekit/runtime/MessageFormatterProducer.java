package dev.demo.quarkus.messagekit.runtime;

import java.util.Map;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;

import dev.demo.messagekit.MessageCatalog;
import dev.demo.messagekit.MessageFormatter;

@ApplicationScoped
public class MessageFormatterProducer {

    @Produces
    @Singleton
    public MessageCatalog catalog() {
        return new MessageCatalog(Map.of("en", "Hello", "es", "Hola", "hr", "Pozdrav"));
    }

    @Produces
    @Singleton
    public MessageFormatter formatter(MessageCatalog catalog, MessageKitConfig config) {
        return new MessageFormatter(catalog, config.language(), config.prefix().orElse(""), config.uppercase());
    }
}
