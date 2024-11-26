FROM docker.io/library/eclipse-temurin:21.0.5_11-jre-alpine

WORKDIR /app
ARG JAR_FILE
COPY ${JAR_FILE} application.jar

# Create group my_group and create user my_self in it
RUN addgroup --system my_group && adduser --system --ingroup my_group my_self \
# Owner of WORKDIR is now my_self:my_group
    && chown -R my_self:my_group /app \
# Application can be run
    && chmod u+x /app/application.jar
# For debug purpose
#    && apk update \
#    && apk add --no-cache curl

EXPOSE 8080

USER my_self
ENTRYPOINT ["java", "-jar", "application.jar"]
