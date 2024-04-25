FROM eclipse-temurin:17-jdk-alpine as build

RUN apk update && \ 
    apk add --no-cache \
    maven \
    libgconf-2-4 \
    wget \
    curl \
    sudo
     
WORKDIR /workspace/app

COPY src src
COPY pom.xml .

RUN mvn wrapper:wrapper
RUN ./mvnw install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM eclipse-temurin:17-jdk-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
VOLUME /tmp
ARG DEPENDENCY=/workspace/app/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","fr.fiducial.exercise.ExerciseApplication"]