package com.shootpro24ai.showcase.provider;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Picks the provider for an edit type, skipping any provider that is switched off.
 * Turning a provider off is a config change (e.g. PHOTOROOM_ENABLED=false), not a code change.
 * Simplified for this showcase.
 */
public class ProviderRegistry {

    private final List<AiEditProvider> providers;
    private final Set<String> enabled;

    public ProviderRegistry(List<AiEditProvider> providers, Set<String> enabledProviderNames) {
        this.providers = providers;
        this.enabled = enabledProviderNames;
    }

    public Optional<AiEditProvider> forType(EditType type) {
        return providers.stream()
                .filter(p -> enabled.contains(p.name()))
                .filter(p -> p.supports(type))
                .findFirst();
    }
}
