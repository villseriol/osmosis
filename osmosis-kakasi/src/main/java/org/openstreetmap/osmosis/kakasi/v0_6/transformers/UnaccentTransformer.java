package org.openstreetmap.osmosis.kakasi.v0_6.transformers;

import java.text.Normalizer;

import org.openstreetmap.osmosis.kakasi.common.apache3.compat.StringUtils;

public class UnaccentTransformer implements Transformer {
    private static UnaccentTransformer instance;

    private UnaccentTransformer() {
        super();
    }

    @Override
    public String transform(final String input) {
        // default implementation decomposes accented characters into (base character + accent)
        final String compatibility = StringUtils.stripAccents(input);

        // re-compose any remaining accented characters
        final StringBuilder composed = new StringBuilder(Normalizer.normalize(compatibility, Normalizer.Form.NFC));

        return composed.toString();
    }

    public static Transformer getInstance() {
        if (instance == null) {
            instance = new UnaccentTransformer();
        }

        return instance;
    }
}
