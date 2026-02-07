package org.openstreetmap.osmosis.kakasi.v0_6.transformers;

import java.text.Normalizer;

public class UnaccentTransformer implements KakasiTransformer {
    @Override
    public String transform(String input) {
        if (input == null)
            return null;

        // Step 1: Decompose Unicode into base + combining marks
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);

        // Step 2: Remove all combining marks
        return normalized.replaceAll("\\p{M}", "");
    }
}
