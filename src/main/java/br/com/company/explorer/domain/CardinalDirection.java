package br.com.company.explorer.domain;

/**
 * Created by Fábio Siqueira on 12/17/15.
 */
public enum CardinalDirection {
    NORTH("N"),
    EAST("E"),
    SOUTH("S"),
    WEST("W");

    String id;

    private CardinalDirection(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String toString() {
        return this.id;
    }

}
