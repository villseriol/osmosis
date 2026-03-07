// This software is released into the Public Domain.  See copying.txt for details.
package org.openstreetmap.osmosis.kakasi.v0_6.transform;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.List;
import java.util.logging.Logger;

import org.junit.Test;


public class UnAccentTransformTest extends AbstractTransformTest {
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private Transform t = new UnAccentTransform();

    @Test
    public void testAccentedLatin() {
        // Uppercase accented letters
        assertEquals("A", t.action("Á"));
        assertEquals("A", t.action("À"));
        assertEquals("A", t.action("Â"));
        assertEquals("A", t.action("Ã"));
        assertEquals("A", t.action("Ä"));
        assertEquals("A", t.action("Å"));

        assertEquals("C", t.action("Ç"));

        assertEquals("E", t.action("É"));
        assertEquals("E", t.action("È"));
        assertEquals("E", t.action("Ê"));
        assertEquals("E", t.action("Ë"));

        assertEquals("I", t.action("Í"));
        assertEquals("I", t.action("Ì"));
        assertEquals("I", t.action("Î"));
        assertEquals("I", t.action("Ï"));

        assertEquals("N", t.action("Ñ"));

        assertEquals("O", t.action("Ó"));
        assertEquals("O", t.action("Ò"));
        assertEquals("O", t.action("Ô"));
        assertEquals("O", t.action("Õ"));
        assertEquals("O", t.action("Ö"));

        assertEquals("U", t.action("Ú"));
        assertEquals("U", t.action("Ù"));
        assertEquals("U", t.action("Û"));
        assertEquals("U", t.action("Ü"));

        assertEquals("Y", t.action("Ý"));

        // Lowercase accented letters
        assertEquals("a", t.action("á"));
        assertEquals("a", t.action("à"));
        assertEquals("a", t.action("â"));
        assertEquals("a", t.action("ã"));
        assertEquals("a", t.action("ä"));
        assertEquals("a", t.action("å"));

        assertEquals("c", t.action("ç"));

        assertEquals("e", t.action("é"));
        assertEquals("e", t.action("è"));
        assertEquals("e", t.action("ê"));
        assertEquals("e", t.action("ë"));

        assertEquals("i", t.action("í"));
        assertEquals("i", t.action("ì"));
        assertEquals("i", t.action("î"));
        assertEquals("i", t.action("ï"));

        assertEquals("n", t.action("ñ"));

        assertEquals("o", t.action("ó"));
        assertEquals("o", t.action("ò"));
        assertEquals("o", t.action("ô"));
        assertEquals("o", t.action("õ"));
        assertEquals("o", t.action("ö"));

        assertEquals("u", t.action("ú"));
        assertEquals("u", t.action("ù"));
        assertEquals("u", t.action("û"));
        assertEquals("u", t.action("ü"));

        assertEquals("y", t.action("ý"));
        assertEquals("y", t.action("ÿ"));
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
