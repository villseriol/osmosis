package org.openstreetmap.osmosis.kakasi.v0_6.transformers;

public class TrimTransformer implements KakasiTransformer {
    @Override
    public String transform(String input) {
        return input.trim();
    }
}
