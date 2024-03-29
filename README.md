# Api Gateway [![Build Status](https://travis-ci.com/jaarques-uoc/api-gateway-ws.svg?branch=master)](https://travis-ci.com/jaarques-uoc/api-gateway-ws)

Command line tools:

* Spring boot:
    * build: `./gradlew build`
    * run in localhost: `./gradlew bootRun -Pprofiles=local`
* Docker:
    * build: `docker build --tag=api-gateway-ws .`
    * run: `docker run -p 7000:7000 -t api-gateway-ws`
    * stop: `docker stop $(docker ps -q --filter ancestor=api-gateway-ws)`
    * stop all containers: `docker stop $(docker ps -a -q)`

Initialization endpoint:

* `curl localhost:7000/init`: It redirects the initialisation call to the rest of the microservices.

Monitoring urls:

* Travis CI history: https://travis-ci.com/jaarques-uoc/api-gateway-ws/
* Docker image: https://cloud.docker.com/repository/docker/jaarquesuoc/api-gateway-ws
* Heroku app health-check: https://api-gateway-ws.herokuapp.com/actuator/health
* System:
    * health: https://api-gateway-ws.herokuapp.com/system/health
    * Initialisation: https://api-gateway-ws.herokuapp.com/init
