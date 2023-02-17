#! /bin/sh

jlink --module-path build/libs \
      --compress=2 \
      --no-header-files \
      --no-man-pages \
      --add-modules jp.cafebabe.plmaker,java.base,java.logging,java.net.http,java.sql,jdk.unsupported \
      --output ./minimaljre \
      --verbose \
      --launcher 'plmaker=jp.cafebabe.plmaker/jp.cafebabe.plmaker.cli.Main' 
