package com.shootpro24ai.showcase.jobs;

import com.shootpro24ai.showcase.provider.EditResult;

public interface JobStore {
    void markReady(EditResult result);
    void sendToHumanReview(long jobId, String reason);
}
