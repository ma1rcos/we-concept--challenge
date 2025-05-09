#!/bin/bash

if [ "$1" = "dev" ]; then
    ./gradlew bootRun
elif [ "$1" = "prod" ]; then
    docker build -f Dockerfile.app -t weconcept-app .
    docker run --rm -p 8080:8080 --name app weconcept-app
elif [ "$1" = "database" ]; then
    docker build -f Dockerfile.database -t weconcept-database .
    docker run --rm -p 5432:5432 --name database weconcept-database
else
    echo "Use: ./deploy.sh [dev|prod|database]"
    exit 1
fi