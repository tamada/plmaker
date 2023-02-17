FROM ghcr.io/graalvm/native-image:ol8-java17 AS base

ARG JAR_FILE="build/libs/plmaker-0.5.0.jar"

COPY build/libs /opt/plmaker/libs
COPY build/classes/java/main/META-INF/native-image/picocli-generated/plmaker/reflect-config.json  /opt/plmaker/config/reflect-config.json
COPY build/classes/java/main/META-INF/native-image/picocli-generated/plmaker/resource-config.json /opt/plmaker/config/resource-config.json

RUN native-image -jar /opt/plmaker/libs/plmaker-0.5.0.jar \
      -classpath /opt/plmaker/libs/gson-2.10.1.jar:/opt/plmaker/libs/picocli-4.7.1.jar:/opt/plmaker/libs/plmaker-0.5.0.jar \
      --no-fallback  \
      --enable-http  \
      --enable-https \
      --static       \
      -H:+ReportExceptionStackTraces \
      -H:Log=registerResource:5 \
      -H:ReflectionConfigurationFiles=/opt/plmaker/config/reflect-config.json \
      -H:ResourceConfigurationFiles=/opt/plmaker/config/resource-config.json \
      /opt/plmaker/plmaker

FROM alpine:3.10.1

ARG VERSION="1.0.0"

LABEL org.opencontainers.image.authors="Haruaki Tamada <tamada@users.noreply.github.com>" \
      org.opencontainers.image.url="https://github.com/tamada/plmaker" \
      org.opencontainers.image.documentation="Collecting products' information from GitHub." \
      org.opencontainers.image.source="https://raw.githubusercontent.com/tamada/plmaker/blob/main/dockers/minimaljre/Dockerfile" \
      org.opencontainers.image.version="${VERSION}"

RUN    adduser -D nonroot

COPY --from=base /opt/plmaker/plmaker /opt/plmaker/plmaker

ENV HOME=/app
ENV BTMEISTER_HOME=/opt/plmaker
ENV PLMAKER_VERSION=${VERSION}

WORKDIR "/app"
USER nonroot

ENTRYPOINT [ "/opt/plmaker/plmaker" ]