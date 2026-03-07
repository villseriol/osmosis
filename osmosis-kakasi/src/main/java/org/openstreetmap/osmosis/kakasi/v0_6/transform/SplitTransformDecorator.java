// This software is released into the Public Domain.  See copying.txt for details.
package org.openstreetmap.osmosis.kakasi.v0_6.transform;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SplitTransformDecorator extends TransformDecorator {
    // Regex to match known word splitters in the Latin1 codepage (includes space)
    private static final Pattern SPLIT_PATTERN = Pattern.compile("[·\\|\\)\\(\\]\\[\\}\\{ <>]+");

    public SplitTransformDecorator(Transform target) {
        super(target);
    }


    @Override
    public String action(String input) {
        StringBuilder result = new StringBuilder();

        Matcher matcher = SPLIT_PATTERN.matcher(input);

        Transform target = getTarget();

        int lastEnd = 0;
        while (matcher.find()) {
            String before = input.substring(lastEnd, matcher.start());
            String match = matcher.group();
            lastEnd = matcher.end();

            String translated = target.action(before);
            result.append(translated);
            result.append(match);
        }

        // remainder after all/no matches
        String remainder = input.substring(lastEnd);
        if (remainder != null && !"".equals(remainder)) {
            String translated = target.action(remainder);
            result.append(translated);
        }

        return result.toString();
    }
}
