package com.shootpro24ai.showcase.provider;

/** resultKey is the S3 key of the edited image. */
public record EditResult(long jobId, String provider, String resultKey) {}
