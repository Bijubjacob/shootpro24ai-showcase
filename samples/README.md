# Code samples

Simplified versions of three patterns from ShootPro24AI. They show the design, not the full
production code (no vendor SDK calls, keys, or AWS wiring).

- `provider/` - one interface for every AI provider, plus a registry that skips providers that are switched off
- `quality/` - the quality gate that sends low-scoring results to a human
- `jobs/` - the worker that handles one queued job, with retry and human-review fallbacks
