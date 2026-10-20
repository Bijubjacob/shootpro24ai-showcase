package com.shootpro24ai.showcase.provider;

public record EditRequest(long jobId, EditType type, String sourceKey) {}
