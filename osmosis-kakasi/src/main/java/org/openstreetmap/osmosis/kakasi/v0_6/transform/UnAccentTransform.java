// This software is released into the Public Domain.  See copying.txt for details.
package org.openstreetmap.osmosis.kakasi.v0_6.transform;

import com.ibm.icu.text.Transliterator;


public class UnAccentTransform implements Transform {
    private static final Transliterator STRIP_ACCENT = Transliterator
            .getInstance("[[:Latin:]]; NFD; [:Nonspacing Mark:] Remove; NFC");

    @Override
    public String action(String input) {
        return STRIP_ACCENT.transliterate(input);
    }
}
