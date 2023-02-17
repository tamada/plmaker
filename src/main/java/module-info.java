module jp.cafebabe.plmaker {
    requires info.picocli;
    requires com.google.gson;
    requires java.logging;
    requires java.net.http;

    opens jp.cafebabe.plmaker.cli to info.picocli;

    exports jp.cafebabe.plmaker;
    exports jp.cafebabe.plmaker.entities;
}