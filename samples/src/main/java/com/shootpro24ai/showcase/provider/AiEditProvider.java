package com.shootpro24ai.showcase.provider;

/**
 * Every AI service (Imagen AI, Photoroom, ...) implements this one interface.
 * The rest of the pipeline only talks to this, never to a vendor SDK directly.
 * Simplified for this showcase.
 */
public interface AiEditProvider {

    /** Short name used in config and logs, e.g. "imagen" or "photoroom". */
    String name();

    /** True if this provider handles the given edit type. */
    boolean supports(EditType type);

    /** Sends the image to the provider and returns where the edited result was saved. */
    EditResult edit(EditRequest request) throws ProviderException;
}
