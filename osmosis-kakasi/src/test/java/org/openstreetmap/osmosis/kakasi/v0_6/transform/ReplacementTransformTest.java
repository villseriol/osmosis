// This software is released into the Public Domain.  See copying.txt for details.
package org.openstreetmap.osmosis.kakasi.v0_6.transform;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.junit.Test;


public class ReplacementTransformTest extends AbstractTransformTest {
    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Test
    public void testCharacterReplacements() {
        Map<CharSequence, CharSequence> replacements = new HashMap<>();
        replacements.put("・", "·");
        replacements.put("･", "·");
        replacements.put("•", "·");
        replacements.put("〈", "<");
        replacements.put("《", "<<");
        replacements.put("〉", ">");
        replacements.put("》", ">>");
        replacements.put("ʻ", "'");
        replacements.put("–", "-");
        replacements.put("", "");
        replacements.put("", "");

        ReplacementTransform transformer = new ReplacementTransform(replacements);

        // Test single-character replacement
        assertEquals("·", transformer.action("・"));
        assertEquals("·", transformer.action("•"));
        assertEquals("<", transformer.action("〈"));
        assertEquals("<<", transformer.action("《"));
        assertEquals(">", transformer.action("〉"));
        assertEquals(">>", transformer.action("》"));
        assertEquals("-", transformer.action("–"));
        assertEquals("'", transformer.action("ʻ"));

        // Test empty replacements
        assertEquals("", transformer.action(""));
        assertEquals("", transformer.action(""));

        // Test string with multiple replacements
        String input = "・〈》–日本語";
        String expected = "·<>>-日本語";
        assertEquals(expected, transformer.action(input));

        // Test string with no replacements (should stay the same)
        String input2 = "日本語テスト";
        assertEquals(input2, transformer.action(input2));
    }
}
