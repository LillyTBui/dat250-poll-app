## Short report

I containerized/dockerized the application from `expass3` which contains frontend and backend code. 
I first tried to containerized the backed project to see if it worked. To do this I followed the example 
code from lecture 14. For the base image I choose a newer version of gradle with JDK21 and the alpine variant
to make the image smaller. I got run error with `./gradlew bootJar` because the bootJar file cound not be found. 
To solve this I had to specify `./gradlew :backend:bootJar` and copy the entire backend directory
with `COPY backend/ backend/` instead of `COPY backend/scr backend/src`, which was what I initially
tried. To build the image I used the command `docker build -t poll/image`. When I ran the image with 
`docker run poll/image` I saw that the spring boot server ran, but I got connection refused 
with Bruno. To solve this I needed to do port mapping with the command `docker run -p 8080:8080 poll/image`.

After making the dockerfile for the backend project work, I tried to make it work with a Docker
compose file with one service, namely backend. Next I created a new dockerfile for creating
a docker image of the frontend project and added it as a service to the docker compose file.
I got problems accessing the frontend project after running `docker compose up` but the 
solution was to add `--host` flag in the CMD instruction of the frontend dockerfile.

- Link to the backend dockerfile: https://github.com/LillyTBui/dat250-poll-app/blob/docker/Dockerfile
- Link to the frontend dockerfile: https://github.com/LillyTBui/dat250-poll-app/blob/docker/frontend/Dockerfile
- Link to the docker compose file: https://github.com/LillyTBui/dat250-poll-app/blob/docker/compose.yaml
