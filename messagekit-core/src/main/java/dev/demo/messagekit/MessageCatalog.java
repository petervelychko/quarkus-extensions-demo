package dev.demo.messagekit;

import java.util.Map;
import java.util.Objects;

/**
 * A tiny third-party-style library API. It has no Quarkus dependencies.
 */
public final class MessageCatalog {

    private final Map<String, String> greetings;

    public MessageCatalog(Map<String, String> greetings) {
        this.greetings = Map.copyOf(Objects.requireNonNull(greetings, "greetings"));
        if (!this.greetings.containsKey("en")) {
            throw new IllegalArgumentException("An English greeting is required");
        }
    }

    public String greeting(String language) {
        return greetings.getOrDefault(language, greetings.get("en"));
    }
}
