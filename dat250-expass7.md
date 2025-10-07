## Short report

For the base image I choose a newer version of gradle with JDK21 and the alpine variant 
to make the image smaller. I containerized/dockerized the application from `expass3` which
contains frontend and backend code. But in this exercise I only tried to containerized the 
backend project. To do this exercice I followed the example code from lecture 14, 
but got run error with `./gradlew bootJar` because the bootJar file cound not be found. To 
solve this I had to specify `./gradlew :backend:bootJar` and copy the entire backend directory
with `COPY backend/ backend/` instead of `COPY backend/scr backend/src` which was what I initially
tried.

To build the image I used the command `docker build -t poll/image`. When I ran the image with 
`docker run poll/image` I saw that the spring boot server ran, but I got connection refused 
with Bruno. To solve this I needed to do port mapping with the command `docker run -p 8080:8080 poll/image`.

- Link to the Dockerfile: 