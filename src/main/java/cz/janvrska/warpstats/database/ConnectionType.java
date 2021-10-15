package cz.janvrska.warpstats.database;

public enum ConnectionType {
    MYSQL("MYSQL"),
    SQLITE("SQLITE");

    public String text;

    ConnectionType(String text) {
        this.text = text;
    }
}
