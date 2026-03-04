// This software is released into the Public Domain.  See copying.txt for details.
package org.openstreetmap.osmosis.kakasi.v0_6.transform;

import java.text.Normalizer;

import org.openstreetmap.osmosis.kakasi.common.apache3.compat.StringUtils;


public class UnAccentTransform implements Transform {
    @Override
    public String action(String input) {
        // default implementation decomposes accented characters into (base character + accent)
        final String compatibility = StringUtils.stripAccents(input);

        // re-compose any remaining accented characters
        final StringBuilder composed = new StringBuilder(Normalizer.normalize(compatibility, Normalizer.Form.NFC));

        return composed.toString();
    }
}
