FROM openjdk:17-slim-bullseye as builder

COPY build/libs /opt/plmaker/libs
RUN jlink --module-path /opt/java/openjdk/jmods \
    --compress=2 \
    --module-path  /opt/plmaker/libs \
    --add-modules jp.cafebabe.plmaker,java.base,java.logging,java.net.http,java.sql,jdk.unsupported \
    --no-header-files  \
    --no-man-pages  \
    --launcher plmaker=jp.cafebabe.plmaker/jp.cafebabe.plmaker.cli.Main \
    --output /opt/minimaljre

FROM debian:bullseye-slim

ARG VERSION="1.0.0"

LABEL org.opencontainers.image.authors="Haruaki Tamada <tamada@users.noreply.github.com>" \
      org.opencontainers.image.url="https://github.com/tamada/plmaker" \
      org.opencontainers.image.documentation="Collecting products' information from GitHub." \
      org.opencontainers.image.source="https://raw.githubusercontent.com/tamada/plmaker/blob/main/dockers/minimaljre/Dockerfile" \
      org.opencontainers.image.version="${VERSION}"

RUN adduser --disabled-login --disabled-password --no-create-home nonroot \
    && mkdir -p /opt/plmaker/libs
COPY --from=builder /opt/minimaljre /opt/minimaljre
ADD build/libs /opt/plmaker/libs

ENV PLMAKER_HOME=/opt/plmaker
ENV JAVA_HOME=/opt/minimaljre
ENV PATH=$JAVA_HOME/bin:$PATH
ENV HOME=/app
ENV PLMAKER_VERSION=${VERSION}

WORKDIR /app
USER nonroot

ENTRYPOINT [ "java", "-p", "/opt/plmaker/libs/", "-m", "jp.cafebabe.plmaker/jp.cafebabe.plmaker.cli.Main" ]
