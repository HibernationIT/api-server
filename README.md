# Hibernation IT API Server

This is the API server used in all my projects.

## How to run this project with Docker

### PostgreSQL
```bash
docker run --name postgres \
-p 5432:5432 \
-e POSTGRES_PASSWORD=secret1234 \
-d postgres:16
```

### Redis
```bash
docker run --name redis \
-p 6379:6379 \
-d redis:7.2.4
```