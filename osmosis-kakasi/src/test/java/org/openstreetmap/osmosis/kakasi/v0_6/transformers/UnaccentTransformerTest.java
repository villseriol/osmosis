package org.openstreetmap.osmosis.kakasi.v0_6.transformers;

import static org.junit.Assert.assertEquals;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

public class UnaccentTransformerTest {
    private static final UnaccentTransformer TRANSFORMER = new UnaccentTransformer();

    @Test
    public void testAll() {
        Map<String, String> cases = new LinkedHashMap<>();

        for (Map.Entry<String, String> entry : cases.entrySet()) {
            String input = entry.getKey();
            String expected = entry.getValue();
            String actual = TRANSFORMER.transform(input);

            assertEquals(
                    "Failed for character U+"
                            + Integer.toHexString(input.codePointAt(0)).toUpperCase(),
                    expected,
                    actual);
        }
    }
}
