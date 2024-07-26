#!/bin/bash
./gradlew bootJar
docker build --no-cache -t event-manager-backend .
tag event-manager-backend dragalone/event-manager-backend
docker push dragalone/event-manager-backend
