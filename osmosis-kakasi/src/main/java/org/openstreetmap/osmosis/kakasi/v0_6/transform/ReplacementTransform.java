// This software is released into the Public Domain.  See copying.txt for details.
package org.openstreetmap.osmosis.kakasi.v0_6.transform;

import java.util.Map;

import org.apache.commons.text.translate.CharSequenceTranslator;
import org.apache.commons.text.translate.LookupTranslator;


public class ReplacementTransform implements Transform {
    private final CharSequenceTranslator translator;

    public ReplacementTransform(final Map<CharSequence, CharSequence> replacements) {
        super();

        this.translator = new LookupTranslator(replacements);
    }


    @Override
    public String action(String input) {
        return translator.translate(input);
    }
}
