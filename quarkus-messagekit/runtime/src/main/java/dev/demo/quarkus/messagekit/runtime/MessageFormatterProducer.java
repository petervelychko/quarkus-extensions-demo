package dev.demo.quarkus.messagekit.runtime;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;

import dev.demo.messagekit.MessageCatalog;
import dev.demo.messagekit.MessageFormatter;

/**
 * A regular CDI producer in the runtime module. The build step registers
 * this class so consuming applications do not need to provide it themselves.
 */
@ApplicationScoped
public class MessageFormatterProducer {

    @Produces
    @Singleton
    public MessageFormatter formatter(MessageCatalog catalog, MessageKitConfig config) {
        return new MessageFormatter(catalog, config.language(), config.prefix().orElse(""), config.uppercase());
    }
}
