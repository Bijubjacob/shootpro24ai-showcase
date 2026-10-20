# Design decisions

## Why a queue for AI jobs
AI editing calls can take seconds or minutes, and providers sometimes fail. Running them inside
the user's HTTP request would make the app feel stuck and would lose work on errors. With SQS, the
upload returns right away, jobs retry on their own, and one slow provider doesn't hold up the rest.

## Why one interface per provider
Providers change. Keys get rotated, plans run out of quota, and better services show up. Every
provider implements the same `AiEditProvider` interface and has an on/off flag in configuration,
so switching one off or adding a new one is a config change plus one class, not a rewrite.
I use the same pattern in my invoice app, DocPipe, which let me swap one AWS AI service for another
when account access was a problem.

## Why a human review step
AI editing is fast but not always right, and customers pay for the result. Every output gets a
quality check, and anything under the cutoff goes to a human editor before the customer sees it.

## Why Postgres instead of DynamoDB
The data is relational: customers, jobs, edits, and billing all link together, and I wanted real
transactions and SQL for reporting.
