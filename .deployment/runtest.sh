#!/bin/bash

docker run -d -p 5432:5432 -e POSTGRES_PASSWORD=test -e POSTGRES_DB=test -e POSTGRES_USER=test --name bootstrapPlay2PGTest mdillon/postgis:latest

(
for i in `seq 1 10`;
            do
              nc -z localhost 5432 && echo Success && exit 0
              echo -n .
              sleep 1
            done
            echo Failed waiting for Postgres && exit 1
)

sbt ciTests
docker stop bootstrapPlay2PGTest
docker rm bootstrapPlay2PGTest