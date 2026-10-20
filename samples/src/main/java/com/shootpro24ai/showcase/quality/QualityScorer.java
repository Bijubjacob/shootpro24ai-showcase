package com.shootpro24ai.showcase.quality;

import com.shootpro24ai.showcase.provider.EditResult;

/** Returns a score from 0.0 to 1.0 for an edited image. */
public interface QualityScorer {
    double score(EditResult result);
}
