package org.openstreetmap.osmosis.kakasi.v0_6.transformers;

public class DuplicateSpaceTransformer implements KakasiTransformer {
    @Override
    public String transform(String input) {
        return input
                .replaceAll("\s+", " ")
                .replaceAll("([\\(\\[\\{<])\\s+", "$1")
                .replaceAll("\\s+([\\)\\]\\}>])", "$1");
    }
}
