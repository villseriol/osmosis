// This software is released into the Public Domain.  See copying.txt for details.
package org.openstreetmap.osmosis.kakasi.v0_6.transform;

import com.ibm.icu.text.Transliterator;


public class LigatureTransform implements Transform {
    private static final Transliterator LIGATURE_ASCII = Transliterator
            .getInstance("[[:Latin:]]; Any-Latin; Latin-ASCII");

    @Override
    public String action(String input) {
        return LIGATURE_ASCII.transliterate(input);
    }
}
