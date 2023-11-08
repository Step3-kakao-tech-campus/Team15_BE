FROM krmp-d2hub-idock.9rum.cc/goorm/gradle:8.2.1-jdk11 as builder

WORKDIR /workspace/Team15_BE

COPY . .

RUN echo "systemProp.http.proxyHost=krmp-proxy.9rum.cc\nsystemProp.http.proxyPort=3128\nsystemProp.https.proxyHost=krmp-proxy.9rum.cc\nsystemProp.https.proxyPort=3128" > /root/.gradle/gradle.properties

RUN gradle init

RUN gradle wrapper

RUN chmod +x gradlew

RUN ./gradlew clean build -x test

EXPOSE 8080

FROM krmp-d2hub-idock.9rum.cc/goorm/gradle:8.2.1-jdk11

COPY --from=builder /workspace/Team15_BE/build/libs/borrowme-0.0.1-SNAPSHOT.jar .

ENV DATABASE_URL=jdbc:mysql://mysql:3306/borrowme_db

CMD ["java", "-jar", "-Dspring.profiles.active=ide", "borrowme-0.0.1-SNAPSHOT.jar"]
