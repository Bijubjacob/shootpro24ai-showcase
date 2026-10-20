package com.shootpro24ai.showcase.quality;

import com.shootpro24ai.showcase.provider.EditResult;

/**
 * Decides whether an AI result can go straight to the customer
 * or needs a human editor first. Simplified for this showcase.
 */
public class QualityGate {

    public enum Decision { DELIVER, HUMAN_REVIEW }

    private final QualityScorer scorer;
    private final double cutoff;

    public QualityGate(QualityScorer scorer, double cutoff) {
        this.scorer = scorer;
        this.cutoff = cutoff;
    }

    public Decision check(EditResult result) {
        double score = scorer.score(result);
        return score >= cutoff ? Decision.DELIVER : Decision.HUMAN_REVIEW;
    }
}
