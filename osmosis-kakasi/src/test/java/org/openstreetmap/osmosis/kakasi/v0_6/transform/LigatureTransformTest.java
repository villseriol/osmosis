// This software is released into the Public Domain.  See copying.txt for details.
package org.openstreetmap.osmosis.kakasi.v0_6.transform;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.List;
import java.util.logging.Logger;

import org.junit.Test;


public class LigatureTransformTest extends AbstractTransformTest {
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private Transform t = new LigatureTransform();

    @Test
    public void testLigature() {
        assertEquals("AE", t.action("Æ"));
        assertEquals("OE", t.action("Œ"));
        assertEquals("D", t.action("Ð"));

        assertEquals("o", t.action("ø"));

        assertEquals("ae", t.action("æ"));
        assertEquals("oe", t.action("œ"));
        assertEquals("d", t.action("ð"));
        assertEquals("O", t.action("Ø"));
    }


    @Test
    public void testJapanese() {
        List<Character> katakana = getKatakana();
        assertFalse(katakana.isEmpty());

        for (Character c : katakana) {
            String expected = String.valueOf(c);
            logger.info("Testing: " + expected + "->" + expected);
            assertEquals(expected, t.action(expected));
        }

        List<Character> hiragana = getHiragana();
        for (Character c : hiragana) {
            String expected = String.valueOf(c);
            logger.info("Testing: " + expected + " -> " + expected);
            assertEquals(expected, t.action(expected));
        }
    }
}
