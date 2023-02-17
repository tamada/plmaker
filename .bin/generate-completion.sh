#! /bin/sh

VERSION=$(gradle --quiet printVersion)

java -cp build/libs/plmaker-${VERSION}.jar:build/libs/picocli-4.7.0.jar \
     picocli.AutoComplete  \
     -n plmaker \
     -w  \
     -f jp.cafebabe.plmaker.cli.Main
