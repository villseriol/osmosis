// This software is released into the Public Domain.  See copying.txt for details.
package org.openstreetmap.osmosis.kakasi.v0_6.transformers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class UnaccentTransformerTest {
    private static final Transformer TRANSFORMER = UnaccentTransformer.getInstance();

    @Test
    public void testLatinCharacters() {
        ensure("äöü", "aou");
    }


    @Test
    public void testSimpleAsciiCharacters() {
        ensureUnchanged("test");
        ensureUnchanged("test (villseriol)");
    }


    @Test
    public void testJapaneseCharacters() {
        ensureUnchanged("アポロベーカリー");
    }


    /**
     * Assert that the input is not changed after transformation.
     *
     * @param input the input string
     */
    private void ensureUnchanged(String input) {
        ensure(input, input);
    }


    /**
     * Assert that the input equals the output after transformation.
     *
     * @param input the input string
     * @param output the output string
     */
    private void ensure(String input, String output) {
        String actual = TRANSFORMER.transform(input);
        assertEquals(output, actual);
    }
}
