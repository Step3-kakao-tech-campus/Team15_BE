FROM gradle:8.2.1-jdk11 as builder

WORKDIR /workspace/Team15_BE

COPY . .

RUN echo "systemProp.http.proxyHost=krmp-proxy.9rum.cc\nsystemProp.http.proxyPort=3128\nsystemProp.https.proxyHost=krmp-proxy.9rum.cc\nsystemProp.https.proxyPort=3128" > /root/.gradle/gradle.properties

RUN gradle init

RUN gradle wrapper

RUN chmod +x gradlew

RUN ./gradlew clean build -x test

EXPOSE 8080
