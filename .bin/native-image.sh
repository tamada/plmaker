#! /bin/sh

VERSION=$(gradle --quiet printVersion)

native-image \
    -jar build/libs/plmaker-${VERSION}.jar \
    --class-path $(ls build/libs | xargs echo -n | tr ' ' ':') \
    --no-fallback \
    --enable-http \
    --enable-https \
    -H:+ReportExceptionStackTraces \
    -H:Log=registerResource:5 \
    -H:ReflectionConfigurationFiles=build/classes/java/main/META-INF/native-image/picocli-generated/plmaker/reflect-config.json \
    -H:ResourceConfigurationFiles=build/classes/java/main/META-INF/native-image/picocli-generated/plmaker/resource-config.json \
    $1

## the following settings are not available, since gson uses jdk.unsupported module and 
## module mode does not support in use of jdk.unsupport module..
#    --static \ # darwin does not support this option.
#    --module-path build/libs \
#    --module jp.cafebabe.plmaker/jp.cafebabe.plmaker.cli.Main \
#    --add-modules java.logging,java.net.http \
