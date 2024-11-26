# weather-rest-api.spring-boot.demo
Demo of a spring boot application which exposes a weather REST API

## Prerequisites
* JDK 21
* Maven 3.9.8

## Features
* Exposes a versioned REST API which provides current and forecast weather for a city

## Technical choices

### Application
* Auto-executable Spring Boot application
* Application provides a simple cache solution
* REST API is implemented with reactive Spring WebFlux framework
* REST API documentation is provided at [Swagger UI]

### Unit tests
* JUnit5 and Mockito unit tests for execution eficiency
* Everything in the service to be tested has to be mocked
* No unit test with `@SpringBootTest` as spring boot tests are meant for integration tests

### End2End tests
* End2End tests implemented with [Karate]

## Automatic build
* JAR and Docker image are build and publish on [project github CI/CD]
* [Project github packages and images registries]

## Manual build
* `mvn clean package -Dgroups=!e2e surefire-report:report jacoco:report`  
⚠️ `-Dgroups=!e2e` to not execute End2End tests ⚠️
* `docker build --build-arg JAR_FILE=target/*.jar -t ghcr.io/dh-gonzalez/weather-rest-api.spring-boot.demo:latest .`

### Unit tests HTML report and Jacoco code coverage HTML report
* HTML unit tests report is located at `target/reports/surefire.html`
* Code coverage report is located at `target/site/jacoco/index.html`

## Run

### JAR
* `java -jar target/weather-rest-api.spring-boot.demo-{version}.jar`

### Docker
* `docker run --name weather-container -d -p 8080:8080 ghcr.io/dh-gonzalez/weather-rest-api.spring-boot.demo:latest`

### Debug
* `docker logs -f weather-container`
* `docker exec -it weather-container /bin/sh`

## Test

* Swagger UI is available at : [Swagger UI]
* `curl --silent http://localhost:8080/api/v1/weather/current?city=toulouse`
* `curl --silent http://localhost:8080/api/v1/weather/forecast?city=toulouse`

## End2End tests
* Start the application locally
* `mvn test -Dgroups=e2e`

### End2End tests HTML report
* located at `target\karate-reports\karate-summary.html`

## TODOs
* End2End code coverage result and HTML report
* Externalize property files application{-profile}.yml
* Implement cache with Caffeine, Ehcache or Redis
* Upgrade from [Eclipse Temurin] base image to [GCR distroless] base image
* Rewrite all project with Quarkus

[Swagger UI]: http://localhost:8080/swagger-ui/index.html
[project github CI/CD]: https://github.com/dh-gonzalez/weather.rest-api.spring-boot.demo/actions
[Project github packages and images registries]: https://github.com/dh-gonzalez/weather.rest-api.spring-boot.demo/packages/
[Karate]: https://github.com/karatelabs/karate  
[Eclipse Temurin]: https://hub.docker.com/_/eclipse-temurin  
[GCR distroless]: https://console.cloud.google.com/artifacts/docker/distroless/us/gcr.io
