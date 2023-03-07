package app.parser;

import app.parser.data.DroidLifeParser;
import app.parser.data.TechCrunchParser;
import app.parser.data.TechStartupsParser;

public enum Website {
    TechCrunch(new TechCrunchParser()),
    DroidLife(new DroidLifeParser()),
    TechStartups(new TechStartupsParser());

    public final JsoupParser parser;


    Website(JsoupParser parser ) {
        this.parser = parser;

    }

    public JsoupParser getParser() {
        return this.parser;
    }

}