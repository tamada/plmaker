#! /bin/sh

VERSION=$(gradle --quiet printVersion)

mkdir -p build/completions/bash
java -cp $(ls build/libs/* | xargs echo -n | tr ' ' ':') \
     picocli.AutoComplete  \
     -n plmaker \
     -o build/completions/bash/plmaker \
     -w  \
     -f jp.cafebabe.plmaker.cli.Main
