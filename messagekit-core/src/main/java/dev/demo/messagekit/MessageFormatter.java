package dev.demo.messagekit;

import java.util.Locale;
import java.util.Objects;

/**
 * A reusable plain Java formatter; the Quarkus extension supplies its dependencies.
 */
public final class MessageFormatter {

    private final MessageCatalog catalog;
    private final String language;
    private final String prefix;
    private final boolean uppercase;

    public MessageFormatter(MessageCatalog catalog, String language, String prefix, boolean uppercase) {
        this.catalog = Objects.requireNonNull(catalog, "catalog");
        this.language = Objects.requireNonNull(language, "language");
        this.prefix = Objects.requireNonNull(prefix, "prefix");
        this.uppercase = uppercase;
    }

    public String format(String name) {
        String result = catalog.greeting(language) + " " + Objects.requireNonNull(name, "name");
        if (!prefix.isBlank()) {
            result = prefix + " " + result;
        }
        return uppercase ? result.toUpperCase(Locale.ROOT) : result;
    }
}
