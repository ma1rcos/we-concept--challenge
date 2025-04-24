FROM rockylinux:9.3 AS builder

RUN dnf update -y && dnf clean all

RUN dnf install -y java-21-openjdk-devel && dnf clean all

WORKDIR /usr/src/app

COPY . .

RUN ./gradlew build --no-daemon

FROM rockylinux:9.3

RUN dnf update -y && dnf clean all

RUN dnf install -y java-21-openjdk && dnf clean all

WORKDIR /usr/src/app

COPY --from=builder /usr/src/app/build/libs/*.jar /usr/src/app/app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]