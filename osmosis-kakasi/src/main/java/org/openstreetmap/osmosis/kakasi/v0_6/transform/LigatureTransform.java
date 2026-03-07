// This software is released into the Public Domain.  See copying.txt for details.
package org.openstreetmap.osmosis.kakasi.v0_6.transform;

import com.ibm.icu.text.Transliterator;


public class LigatureTransform implements Transform {
    @Override
    public String action(String input) {
        Transliterator t = Transliterator.getInstance("[[:Latin:]]; Any-Latin; Latin-ASCII");

        return t.transliterate(input);
    }
}
