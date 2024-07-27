#!/bin/bash
gradlew.bat bootJar
docker build --no-cache -t event-manager-backend .
docker tag event-manager-backend dragalone/event-manager-backend
docker push dragalone/event-manager-backend
