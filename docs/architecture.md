# Architecture

## Request flow

1. The photographer signs in (Cognito) and uploads images from the React app.
   Images go to S3, and a job record is saved in Postgres.
2. The API puts a message on SQS with the job ID and the edit type, then returns right away.
   The user sees the job as "processing."
3. A worker reads the message, loads the job, and asks the provider registry for the
   provider that handles that edit type.
4. The provider sends the image to the AI service (Imagen AI or Photoroom) and saves the result to S3.
5. The quality gate scores the result. If it passes, the job is marked ready and the customer is
   notified. If it doesn't, it goes to the human review queue.
6. If a provider call fails, the message goes back on the queue and is retried. After too many
   failures the job is flagged for a person instead of failing quietly.

## ChatGPT app

ShootPro24AI is also published as an app in ChatGPT. ChatGPT calls a small set of well-defined
tools on the same API, so the model can only take narrow, specific actions on a user's account.

## AWS services

| Service | Used for |
|---|---|
| Elastic Beanstalk | Runs the Spring Boot API |
| CloudFront | Serves the React frontend and delivered files |
| S3 | Original uploads and edited results |
| RDS Postgres | Users, jobs, billing, results |
| Cognito | Sign-in |
| SQS | AI job queue |
| Rekognition | Image analysis |
| SES + SNS | Email delivery and tracking |
