// This software is released into the Public Domain.  See copying.txt for details.
package org.openstreetmap.osmosis.kakasi.v0_6.transform;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ibm.icu.text.Transliterator;


public class HalfToFullTransform implements Transform {
    // Regex to match runs of half-width Katakana (U+FF61–U+FF9F)
    private static final Pattern HALF_WIDTH_KATAKANA_PATTERN = Pattern
            .compile("[\\uFF61-\\uFF9D][\\uFF9E\\uFF9F]?|[\\uFF9E\\uFF9F]");

    @Override
    public String action(String input) {
        Transliterator t1 = Transliterator.getInstance("Halfwidth-Fullwidth");
        Transliterator t2 = Transliterator.getInstance("NFC");

        Matcher m = HALF_WIDTH_KATAKANA_PATTERN.matcher(input);
        StringBuffer sb = new StringBuffer();

        while (m.find()) {
            String group = m.group();

            if ("ﾞ".equals(group) || "ﾟ".equals(group)) {
                // remove standalone marks
                m.appendReplacement(sb, "");
            } else {
                String fullWidth = t2.transliterate(t1.transliterate(group));
                m.appendReplacement(sb, fullWidth);
            }
        }

        m.appendTail(sb);

        return sb.toString();
    }
}
