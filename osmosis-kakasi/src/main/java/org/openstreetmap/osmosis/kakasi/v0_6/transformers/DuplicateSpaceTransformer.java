// This software is released into the Public Domain.  See copying.txt for details.
package org.openstreetmap.osmosis.kakasi.v0_6.transformers;

public final class DuplicateSpaceTransformer implements Transformer {
    private static DuplicateSpaceTransformer instance;

    private DuplicateSpaceTransformer() {
        super();
    }


    @Override
    public String transform(String input) {
        return input
                // remove all duplicate spaces
                .replaceAll("\s+", " ")
                // remove all spaces following or preceding a bracket
                .replaceAll("([\\(\\[\\{<])\\s+", "$1").replaceAll("\\s+([\\)\\]\\}>])", "$1");
    }


    public static Transformer getInstance() {
        if (instance == null) {
            instance = new DuplicateSpaceTransformer();
        }

        return instance;
    }
}
