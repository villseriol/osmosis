// This software is released into the Public Domain.  See copying.txt for details.
package org.openstreetmap.osmosis.kakasi.v0_6.transform;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.openstreetmap.osmosis.testutil.AbstractDataTest;


public class AbstractTransformTest extends AbstractDataTest {
    private List<Character> hiragana;
    private List<Character> katakana;
    private List<Character> alphabet;

    @Before
    public void initialize() {
        List<Character> hira = new ArrayList<>();
        List<Character> kata = new ArrayList<>();
        List<Character> alpha = new ArrayList<>();
        // Hiragana block
        for (int i = 0x3040; i <= 0x309F; i++) {
            hira.add((char) i);
        }

        // Katakana block
        for (int i = 0x30A0; i <= 0x30FF; i++) {
            kata.add((char) i);
        }

        // English alphabet (uppercase A-Z)
        for (int i = 'A'; i <= 'Z'; i++) {
            alpha.add((char) i);
        }

        // English alphabet (lowercase a-z)
        for (int i = 'a'; i <= 'z'; i++) {
            alpha.add((char) i);
        }

        hiragana = Collections.unmodifiableList(hira);
        katakana = Collections.unmodifiableList(kata);
        alphabet = Collections.unmodifiableList(alpha);
    }


    public List<Character> getHiragana() {
        return hiragana;
    }


    public List<Character> getKatakana() {
        return katakana;
    }


    public List<Character> getAlphabet() {
        return alphabet;
    }
}
