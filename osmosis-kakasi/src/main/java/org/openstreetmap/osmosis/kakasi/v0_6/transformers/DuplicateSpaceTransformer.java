package org.openstreetmap.osmosis.kakasi.v0_6.transformers;

public class DuplicateSpaceTransformer implements KakasiTransformer {
    @Override
    public String transform(String input) {
        return input
                // remove all duplicate spaces
                .replaceAll("\s+", " ")
                // remove all spaces following or preceding a bracket
                .replaceAll("([\\(\\[\\{<])\\s+", "$1")
                .replaceAll("\\s+([\\)\\]\\}>])", "$1");
    }
}
