package org.openstreetmap.osmosis.kakasi.v0_6;

public class KakasiTranslation {
    private final KakasiCharset from;
    private final KakasiCharset to;

    public KakasiTranslation(KakasiCharset from, KakasiCharset to) {
        this.from = from;
        this.to = to;
    }

    public KakasiCharset getFrom() {
        return from;
    }

    public KakasiCharset getTo() {
        return to;
    }
}
