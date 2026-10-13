package dev.demo.quarkus.messagekit.runtime;

import java.util.Optional;

import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;
import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithDefault;

/** Runtime settings may be changed without rebuilding the application. */
@ConfigRoot(phase = ConfigPhase.RUN_TIME)
@ConfigMapping(prefix = "quarkus.messagekit")
public interface MessageKitConfig {

    /** Language code. The demo catalog supports en, es, and hr. */
    @WithDefault("en")
    String language();

    /** Optional prefix prepended before the greeting. */
    Optional<String> prefix();

    /** Convert the final message to uppercase. */
    @WithDefault("false")
    boolean uppercase();
}
