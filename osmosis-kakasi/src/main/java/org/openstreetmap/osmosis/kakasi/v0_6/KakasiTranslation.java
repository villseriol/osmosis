// This software is released into the Public Domain.  See copying.txt for details.
package org.openstreetmap.osmosis.kakasi.v0_6;

public class KakasiTranslation {
    private final KakasiCharsetCategory from;
    private final KakasiCharsetCategory to;

    public KakasiTranslation(KakasiCharsetCategory from, KakasiCharsetCategory to) {
        this.from = from;
        this.to = to;
    }


    public KakasiCharsetCategory getFrom() {
        return from;
    }


    public KakasiCharsetCategory getTo() {
        return to;
    }
}
