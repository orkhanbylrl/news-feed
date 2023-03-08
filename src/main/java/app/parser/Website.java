package app.parser;

import app.parser.data.*;

public enum Website {
    TechCrunch(new TechCrunchParser()),
    DroidLife(new DroidLifeParser()),
    APNews(new APNewsParser()),
    ABCNews(new ABCNewsParser()),
    TechStartups(new TechStartupsParser());

    public final JsoupParser parser;


    Website(JsoupParser parser ) {
        this.parser = parser;

    }

    public JsoupParser getParser() {
        return this.parser;
    }

}