package app.parser;

import app.parser.data.HabrParser;

public enum Website {
    Habr(new HabrParser());

    public final JsoupParser parser;

    Website(JsoupParser parser ) {
        this.parser = parser;
    }

    public JsoupParser getParser() {
        return this.parser;
    }
}