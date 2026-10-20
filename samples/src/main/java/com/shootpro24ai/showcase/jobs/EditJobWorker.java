package com.shootpro24ai.showcase.jobs;

import com.shootpro24ai.showcase.provider.*;
import com.shootpro24ai.showcase.quality.QualityGate;

/**
 * Handles one job message from the SQS queue. Simplified for this showcase:
 * in production this is driven by an SQS listener and the job store is Postgres.
 */
public class EditJobWorker {

    private final ProviderRegistry registry;
    private final QualityGate gate;
    private final JobStore jobs;

    public EditJobWorker(ProviderRegistry registry, QualityGate gate, JobStore jobs) {
        this.registry = registry;
        this.gate = gate;
        this.jobs = jobs;
    }

    /** Returns true if the message is done, false if it should go back on the queue. */
    public boolean handle(EditRequest request) {
        var provider = registry.forType(request.type());
        if (provider.isEmpty()) {
            // No provider is switched on for this type, so a person handles it.
            jobs.sendToHumanReview(request.jobId(), "no AI provider enabled for " + request.type());
            return true;
        }
        try {
            EditResult result = provider.get().edit(request);
            if (gate.check(result) == QualityGate.Decision.DELIVER) {
                jobs.markReady(result);
            } else {
                jobs.sendToHumanReview(request.jobId(), "quality score below cutoff");
            }
            return true;
        } catch (ProviderException e) {
            if (e.isRetryable()) {
                return false; // SQS will deliver it again later
            }
            jobs.sendToHumanReview(request.jobId(), "provider error: " + e.getMessage());
            return true;
        }
    }
}
