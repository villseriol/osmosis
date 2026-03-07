// This software is released into the Public Domain.  See copying.txt for details.
package org.openstreetmap.osmosis.kakasi.v0_6.configuration;

public class StringReplacement implements Replacement {
    private final String from;
    private final String to;

    public StringReplacement(String from, String to) {
        this.from = from;
        this.to = to;
    }


    @Override
    public String getFrom() {
        return from;
    }


    @Override
    public String getTo() {
        return to;
    }
}
