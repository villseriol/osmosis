package org.openstreetmap.osmosis.kakasi.v0_6.transformers;

import java.util.Comparator;

public interface KakasiTransformer extends Comparable<KakasiTransformer> {
    public default int getOrder() {
        return 0;
    }

    @Override
    default int compareTo(KakasiTransformer arg0) {
        return Comparator.comparing(KakasiTransformer::getOrder).compare(this, arg0);
    }

    public String transform(String input);
}
