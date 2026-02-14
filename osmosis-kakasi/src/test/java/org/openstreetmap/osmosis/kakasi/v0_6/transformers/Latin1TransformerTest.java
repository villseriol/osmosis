package org.openstreetmap.osmosis.kakasi.v0_6.transformers;

import static org.junit.Assert.assertEquals;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;


public class Latin1TransformerTest {
    private static final Transformer TRANSFORMER = Latin1Transformer.getInstance();

    @Test
    public void testAll() {
        Map<String, String> cases = new LinkedHashMap<>();

        cases.put("", "");
        cases.put("・", "·");
        cases.put("･", "·");
        cases.put("•", "·");
        cases.put("〈", "<");
        cases.put("《", "<");
        cases.put("〉", ">");
        cases.put("》", ">");
        cases.put("”", "\"");
        cases.put("ʻ", "'");
        cases.put("“", "\"");
        cases.put("″", "\"");
        cases.put("゛", "\"");
        cases.put("ﾞ", "\"");
        cases.put("Æ", "AE");
        cases.put("æ", "ae");
        cases.put("–", "-");
        cases.put("", "");
        cases.put("Ð", "D");
        cases.put("Ø", "O");
        cases.put("ø", "o");
        cases.put("Œ", "OE");
        cases.put("œ", "oe");

        // Full-width digits
        cases.put("０", "0");
        cases.put("１", "1");
        cases.put("２", "2");
        cases.put("３", "3");
        cases.put("４", "4");
        cases.put("５", "5");
        cases.put("６", "6");
        cases.put("７", "7");
        cases.put("８", "8");
        cases.put("９", "9");

        // Full-width uppercase
        cases.put("Ａ", "A");
        cases.put("Ｂ", "B");
        cases.put("Ｃ", "C");
        cases.put("Ｄ", "D");
        cases.put("Ｅ", "E");
        cases.put("Ｆ", "F");
        cases.put("Ｇ", "G");
        cases.put("Ｈ", "H");
        cases.put("Ｉ", "I");
        cases.put("Ｊ", "J");
        cases.put("Ｋ", "K");
        cases.put("Ｌ", "L");
        cases.put("Ｍ", "M");
        cases.put("Ｎ", "N");
        cases.put("Ｏ", "O");
        cases.put("Ｐ", "P");
        cases.put("Ｑ", "Q");
        cases.put("Ｒ", "R");
        cases.put("Ｓ", "S");
        cases.put("Ｔ", "T");
        cases.put("Ｕ", "U");
        cases.put("Ｖ", "V");
        cases.put("Ｗ", "W");
        cases.put("Ｘ", "X");
        cases.put("Ｙ", "Y");
        cases.put("Ｚ", "Z");

        // Full-width lowercase
        cases.put("ａ", "a");
        cases.put("ｂ", "b");
        cases.put("ｃ", "c");
        cases.put("ｄ", "d");
        cases.put("ｅ", "e");
        cases.put("ｆ", "f");
        cases.put("ｇ", "g");
        cases.put("ｈ", "h");
        cases.put("ｉ", "i");
        cases.put("ｊ", "j");
        cases.put("ｋ", "k");
        cases.put("ｌ", "l");
        cases.put("ｍ", "m");
        cases.put("ｎ", "n");
        cases.put("ｏ", "o");
        cases.put("ｐ", "p");
        cases.put("ｑ", "q");
        cases.put("ｒ", "r");
        cases.put("ｓ", "s");
        cases.put("ｔ", "t");
        cases.put("ｕ", "u");
        cases.put("ｖ", "v");
        cases.put("ｗ", "w");
        cases.put("ｘ", "x");
        cases.put("ｙ", "y");
        cases.put("ｚ", "z");

        // Full-width punctuation
        cases.put("！", "!");
        cases.put("＃", "#");
        cases.put("＄", "$");
        cases.put("％", "%");
        cases.put("＆", "&");
        cases.put("’", "'");
        cases.put("（", "(");
        cases.put("）", ")");
        cases.put("＊", "*");
        cases.put("＋", "+");
        cases.put("，", ",");
        cases.put("－", "-");
        cases.put("．", ".");
        cases.put("／", "/");
        cases.put("：", ":");
        cases.put("；", ";");
        cases.put("＜", "<");
        cases.put("＝", "=");
        cases.put("＞", ">");
        cases.put("？", "?");
        cases.put("＠", "@");
        cases.put("［", "[");
        cases.put("￥", "\\");
        cases.put("］", "]");
        cases.put("＾", "^");
        cases.put("＿", "_");
        cases.put("｀", "`");
        cases.put("｛", "{");
        cases.put("｜", "|");
        cases.put("｝", "}");
        cases.put("～", "~");
        cases.put("　", " ");

        for (Map.Entry<String, String> entry : cases.entrySet()) {
            String input = entry.getKey();
            String expected = entry.getValue();
            String actual = TRANSFORMER.transform(input);

            assertEquals("Failed for character U+" + Integer.toHexString(input.codePointAt(0)).toUpperCase(), expected,
                    actual);
        }
    }
}
