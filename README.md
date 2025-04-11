# weather.rest-api.spring-boot.demo
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

## Manual build
* Replace `YOUR_API_KEY` with your [Weatherbit] API KEY
* `mvn clean package surefire-report:report jacoco:report`
* `docker build . --file src/main/docker/Dockerfile --build-arg JAR_FILE=target/*.jar --tag ghcr.io/dh-gonzalez/weather.rest-api.spring-boot.demo:latest`

### Unit tests HTML report and Jacoco code coverage HTML report
* HTML unit tests report is located at `target/reports/surefire.html`
* Code coverage report is located at `target/site/jacoco/index.html`

## Run

### JAR
* `java -jar target/weather.rest-api.spring-boot.demo-{version}.jar --spring.config.location=file:src/main/resources/`

### Docker
* `docker run --name weather-container -d -p 8080:8080 -v {absolute/path/to/config}:/config -e SPRING_CONFIG_LOCATION=/config/ ghcr.io/dh-gonzalez/weather.rest-api.spring-boot.demo:latest`

#### Debug
* `docker logs -f weather-container`
* `docker exec -it weather-container /bin/sh`

## Test

* Swagger UI is available at : [Swagger UI]
* `curl --silent http://localhost:8080/api/v1/weather/current?city=toulouse`
* `curl --silent http://localhost:8080/api/v1/weather/forecast?city=toulouse`

## End2End tests
* Start the application locally
* `mvn test -Pe2e`

### End2End tests HTML report
* located at `target\karate-reports\karate-summary.html`

[Weatherbit]: https://www.weatherbit.io/

[Swagger UI]: http://localhost:8080/swagger-ui/index.html
[Swagger api-docs]: http://localhost:8080/v3/api-docs
[Spring Actuator Health]: http://localhost:8080/actuator/health
[Karate]: https://github.com/karatelabs/karate  
