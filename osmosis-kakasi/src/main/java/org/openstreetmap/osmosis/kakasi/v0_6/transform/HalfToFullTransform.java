// This software is released into the Public Domain.  See copying.txt for details.
package org.openstreetmap.osmosis.kakasi.v0_6.transform;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ibm.icu.text.Transliterator;


public class HalfToFullTransform implements Transform {
    // Regex to match runs of half-width Katakana (U+FF61–U+FF9F)
    private static final Pattern HALF_WIDTH_KATAKANA_PATTERN = Pattern
            .compile("[\\uFF61-\\uFF9D][\\uFF9E\\uFF9F]?|[\\uFF9E\\uFF9F]");
    private static final Transliterator HALF_FULL = Transliterator.getInstance("Halfwidth-Fullwidth");
    private static final Transliterator NFC = Transliterator.getInstance("NFC");

    @Override
    public String action(String input) {
        Matcher m = HALF_WIDTH_KATAKANA_PATTERN.matcher(input);
        StringBuffer sb = new StringBuffer();

        while (m.find()) {
            String group = m.group();

            if ("ﾞ".equals(group) || "ﾟ".equals(group)) {
                // remove standalone marks
                m.appendReplacement(sb, "");
            } else {
                String fullWidth = NFC.transliterate(HALF_FULL.transliterate(group));
                m.appendReplacement(sb, fullWidth);
            }
        }

        m.appendTail(sb);

        return sb.toString();
    }
}
