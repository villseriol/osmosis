// This software is released into the Public Domain.  See copying.txt for details.
package org.openstreetmap.osmosis.kakasi.v0_6.transform;

import static org.junit.Assert.assertFalse;

import java.util.List;
import java.util.logging.Logger;

import org.junit.BeforeClass;
import org.junit.Test;
import org.villseriol.kakasi.api.Kakasi;
import org.villseriol.kakasi.api.KakasiConfig;


public class KakasiTransformTest extends AbstractTransformTest {
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private Transform t = new KakasiTransform();

    @BeforeClass
    public static void onlyOnce() {
        KakasiConfig config = KakasiConfig.createDefaultConfig();

        Kakasi.configure(config);
    }


    @Test
    public void testJapaneseIsUntouched() {
        List<Character> katakana = getKatakana();
        assertFalse(katakana.isEmpty());

        for (Character c : katakana) {
            String expected = String.valueOf(c);
            String actual = t.action(expected);
            logger.info("Testing: " + expected + "->" + actual);
            // assertEquals(expected, actual);
        }

        List<Character> hiragana = getHiragana();
        for (Character c : hiragana) {
            String expected = String.valueOf(c);
            String actual = t.action(expected);
            logger.info("Testing: " + expected + " -> " + actual);
            // assertEquals(expected, actual);
        }
    }
}
