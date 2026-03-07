// This software is released into the Public Domain.  See copying.txt for details.
package org.openstreetmap.osmosis.kakasi.v0_6.transform;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.List;
import java.util.logging.Logger;

import org.junit.Test;


public class HalfToFullTransformTest extends AbstractTransformTest {
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private HalfToFullTransform t = new HalfToFullTransform();

    @Test
    public void testHalfWidthPunctuation() {
        // Half-width punctuation in U+FF61–U+FF65
        assertEquals("Half-width ｡ should become full-width 。", "。", t.action("｡")); // U+FF61
        assertEquals("Half-width ､ should become full-width 、", "、", t.action("､")); // U+FF64
        assertEquals("Half-width ･ should become full-width ・", "・", t.action("･")); // U+FF65

        // Standalone dakuten / handakuten should be excluded
        assertEquals("Half-width ﾞ should be removed when standalone", "", t.action("ﾞ")); // U+FF9E
        assertEquals("Half-width ﾞﾞ should be removed when standalone", "", t.action("ﾞﾞ"));
        assertEquals("Half-width ﾟ should be removed when standalone", "", t.action("ﾟ")); // U+FF9F
        assertEquals("Half-width ﾞﾟﾞﾞ should be removed when standalone", "", t.action("ﾞﾟﾞﾞ"));
        assertEquals("Half-width ﾞ should be removed when standalone", "ガカ", t.action("ﾞｶﾞｶ"));
    }


    @Test
    public void testHalfWidthKatakana() {
        // Vowels
        assertEquals("Half-width ｱ should become full-width ア", "ア", t.action("ｱ"));
        assertEquals("Half-width ｲ should become full-width イ", "イ", t.action("ｲ"));
        assertEquals("Half-width ｳ should become full-width ウ", "ウ", t.action("ｳ"));
        assertEquals("Half-width ｴ should become full-width エ", "エ", t.action("ｴ"));
        assertEquals("Half-width ｵ should become full-width オ", "オ", t.action("ｵ"));

        // K-group
        assertEquals("Half-width ｶ should become full-width カ", "カ", t.action("ｶ"));
        assertEquals("Half-width ｷ should become full-width キ", "キ", t.action("ｷ"));
        assertEquals("Half-width ｸ should become full-width ク", "ク", t.action("ｸ"));
        assertEquals("Half-width ｹ should become full-width ケ", "ケ", t.action("ｹ"));
        assertEquals("Half-width ｺ should become full-width コ", "コ", t.action("ｺ"));

        // S-group
        assertEquals("Half-width ｻ should become full-width サ", "サ", t.action("ｻ"));
        assertEquals("Half-width ｼ should become full-width シ", "シ", t.action("ｼ"));
        assertEquals("Half-width ｽ should become full-width ス", "ス", t.action("ｽ"));
        assertEquals("Half-width ｾ should become full-width セ", "セ", t.action("ｾ"));
        assertEquals("Half-width ｿ should become full-width ソ", "ソ", t.action("ｿ"));

        // T-group
        assertEquals("Half-width ﾀ should become full-width タ", "タ", t.action("ﾀ"));
        assertEquals("Half-width ﾁ should become full-width チ", "チ", t.action("ﾁ"));
        assertEquals("Half-width ﾂ should become full-width ツ", "ツ", t.action("ﾂ"));
        assertEquals("Half-width ﾃ should become full-width テ", "テ", t.action("ﾃ"));
        assertEquals("Half-width ﾄ should become full-width ト", "ト", t.action("ﾄ"));

        // N-group
        assertEquals("Half-width ﾅ should become full-width ナ", "ナ", t.action("ﾅ"));
        assertEquals("Half-width ﾆ should become full-width ニ", "ニ", t.action("ﾆ"));
        assertEquals("Half-width ﾇ should become full-width ヌ", "ヌ", t.action("ﾇ"));
        assertEquals("Half-width ﾈ should become full-width ネ", "ネ", t.action("ﾈ"));
        assertEquals("Half-width ﾉ should become full-width ノ", "ノ", t.action("ﾉ"));

        // H-group
        assertEquals("Half-width ﾊ should become full-width ハ", "ハ", t.action("ﾊ"));
        assertEquals("Half-width ﾋ should become full-width ヒ", "ヒ", t.action("ﾋ"));
        assertEquals("Half-width ﾌ should become full-width フ", "フ", t.action("ﾌ"));
        assertEquals("Half-width ﾍ should become full-width ヘ", "ヘ", t.action("ﾍ"));
        assertEquals("Half-width ﾎ should become full-width ホ", "ホ", t.action("ﾎ"));

        // M-group
        assertEquals("Half-width ﾏ should become full-width マ", "マ", t.action("ﾏ"));
        assertEquals("Half-width ﾐ should become full-width ミ", "ミ", t.action("ﾐ"));
        assertEquals("Half-width ﾑ should become full-width ム", "ム", t.action("ﾑ"));
        assertEquals("Half-width ﾒ should become full-width メ", "メ", t.action("ﾒ"));
        assertEquals("Half-width ﾓ should become full-width モ", "モ", t.action("ﾓ"));

        // Y-group
        assertEquals("Half-width ﾔ should become full-width ヤ", "ヤ", t.action("ﾔ"));
        assertEquals("Half-width ﾕ should become full-width ユ", "ユ", t.action("ﾕ"));
        assertEquals("Half-width ﾖ should become full-width ヨ", "ヨ", t.action("ﾖ"));

        // R-group
        assertEquals("Half-width ﾗ should become full-width ラ", "ラ", t.action("ﾗ"));
        assertEquals("Half-width ﾘ should become full-width リ", "リ", t.action("ﾘ"));
        assertEquals("Half-width ﾙ should become full-width ル", "ル", t.action("ﾙ"));
        assertEquals("Half-width ﾚ should become full-width レ", "レ", t.action("ﾚ"));
        assertEquals("Half-width ﾛ should become full-width ロ", "ロ", t.action("ﾛ"));

        // W-group
        assertEquals("Half-width ﾜ should become full-width ワ", "ワ", t.action("ﾜ"));
        assertEquals("Half-width ｦ should become full-width ヲ", "ヲ", t.action("ｦ"));
        assertEquals("Half-width ﾝ should become full-width ン", "ン", t.action("ﾝ"));

        // Dakuten (voiced)
        assertEquals("Half-width ｶﾞ should become full-width ガ", "ガ", t.action("ｶﾞ"));
        assertEquals("Half-width ｷﾞ should become full-width ギ", "ギ", t.action("ｷﾞ"));
        assertEquals("Half-width ｸﾞ should become full-width グ", "グ", t.action("ｸﾞ"));
        assertEquals("Half-width ｹﾞ should become full-width ゲ", "ゲ", t.action("ｹﾞ"));
        assertEquals("Half-width ｺﾞ should become full-width ゴ", "ゴ", t.action("ｺﾞ"));

        // Handakuten (p-sounds)
        assertEquals("Half-width ﾊﾟ should become full-width パ", "パ", t.action("ﾊﾟ"));
        assertEquals("Half-width ﾋﾟ should become full-width ピ", "ピ", t.action("ﾋﾟ"));
        assertEquals("Half-width ﾌﾟ should become full-width プ", "プ", t.action("ﾌﾟ"));
        assertEquals("Half-width ﾍﾟ should become full-width ペ", "ペ", t.action("ﾍﾟ"));
        assertEquals("Half-width ﾎﾟ should become full-width ポ", "ポ", t.action("ﾎﾟ"));
    }


    @Test
    public void testAlphabet() {
        List<Character> alphabet = getAlphabet();
        assertFalse(alphabet.isEmpty());

        for (Character c : alphabet) {
            String expected = String.valueOf(c);
            String actual = t.action(expected);
            logger.info("Testing: " + expected + "->" + actual);
            assertEquals(expected, actual);
        }
    }
}
