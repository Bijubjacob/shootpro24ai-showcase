package com.shootpro24ai.showcase.provider;

public class ProviderException extends Exception {
    private final boolean retryable;

    public ProviderException(String message, boolean retryable) {
        super(message);
        this.retryable = retryable;
    }

    public boolean isRetryable() {
        return retryable;
    }
}
