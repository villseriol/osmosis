// This software is released into the Public Domain.  See copying.txt for details.
package org.openstreetmap.osmosis.kakasi.v0_6.transformers;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public final class Latin1Transformer implements Transformer {
    private static Transformer instance;
    private static final Map<Character, String> FULLWIDTH_TO_ASCII;

    private Latin1Transformer() {
        super();
    }

    static {
        Map<Character, String> map = new HashMap<>();

        // Digits
        map.put('０', "0");
        map.put('１', "1");
        map.put('２', "2");
        map.put('３', "3");
        map.put('４', "4");
        map.put('５', "5");
        map.put('６', "6");
        map.put('７', "7");
        map.put('８', "8");
        map.put('９', "9");

        // Uppercase letters
        map.put('Ａ', "A");
        map.put('Ｂ', "B");
        map.put('Ｃ', "C");
        map.put('Ｄ', "D");
        map.put('Ｅ', "E");
        map.put('Ｆ', "F");
        map.put('Ｇ', "G");
        map.put('Ｈ', "H");
        map.put('Ｉ', "I");
        map.put('Ｊ', "J");
        map.put('Ｋ', "K");
        map.put('Ｌ', "L");
        map.put('Ｍ', "M");
        map.put('Ｎ', "N");
        map.put('Ｏ', "O");
        map.put('Ｐ', "P");
        map.put('Ｑ', "Q");
        map.put('Ｒ', "R");
        map.put('Ｓ', "S");
        map.put('Ｔ', "T");
        map.put('Ｕ', "U");
        map.put('Ｖ', "V");
        map.put('Ｗ', "W");
        map.put('Ｘ', "X");
        map.put('Ｙ', "Y");
        map.put('Ｚ', "Z");

        // Lowercase letters
        map.put('ａ', "a");
        map.put('ｂ', "b");
        map.put('ｃ', "c");
        map.put('ｄ', "d");
        map.put('ｅ', "e");
        map.put('ｆ', "f");
        map.put('ｇ', "g");
        map.put('ｈ', "h");
        map.put('ｉ', "i");
        map.put('ｊ', "j");
        map.put('ｋ', "k");
        map.put('ｌ', "l");
        map.put('ｍ', "m");
        map.put('ｎ', "n");
        map.put('ｏ', "o");
        map.put('ｐ', "p");
        map.put('ｑ', "q");
        map.put('ｒ', "r");
        map.put('ｓ', "s");
        map.put('ｔ', "t");
        map.put('ｕ', "u");
        map.put('ｖ', "v");
        map.put('ｗ', "w");
        map.put('ｘ', "x");
        map.put('ｙ', "y");
        map.put('ｚ', "z");

        // Symbols
        map.put('！', "!");
        map.put('＃', "#");
        map.put('＄', "$");
        map.put('％', "%");
        map.put('＆', "&");
        map.put('’', "'");
        map.put('（', "(");
        map.put('）', ")");
        map.put('＊', "*");
        map.put('＋', "+");
        map.put('，', ",");
        map.put('－', "-");
        map.put('．', ".");
        map.put('／', "/");
        map.put('：', ":");
        map.put('；', ";");
        map.put('＜', "<");
        map.put('＝', "=");
        map.put('＞', ">");
        map.put('？', "?");
        map.put('＠', "@");
        map.put('［', "[");
        map.put('￥', "\\");
        map.put('］', "]");
        map.put('＾', "^");
        map.put('＿', "_");
        map.put('｀', "`");
        map.put('｛', "{");
        map.put('｜', "|");
        map.put('｝', "}");
        map.put('～', "~");
        map.put('　', " "); // Full-width space

        map.put('', "");
        map.put('・', "·");
        map.put('･', "·");
        map.put('•', "·");
        map.put('〈', "<");
        map.put('《', "<");
        map.put('〉', ">");
        map.put('》', ">");
        map.put('”', "\"");
        map.put('ʻ', "\'");
        map.put('“', "\"");
        map.put('″', "\"");
        map.put('゛', "\"");
        map.put('ﾞ', "\"");
        map.put('–', "-");
        map.put('', ""); // Control characters

        map.put('Æ', "AE");
        map.put('æ', "ae");
        map.put('Ð', "D");
        map.put('Ø', "O");
        map.put('ø', "o");
        map.put('Œ', "OE");
        map.put('œ', "oe"); // Other characters

        FULLWIDTH_TO_ASCII = Collections.unmodifiableMap(map);
    }

    @Override
    public String transform(String input) {
        StringBuilder sb = new StringBuilder(input.length());

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            sb.append(FULLWIDTH_TO_ASCII.getOrDefault(c, String.valueOf(c)));
        }

        return sb.toString();
    }


    public static Transformer getInstance() {
        if (instance == null) {
            instance = new Latin1Transformer();
        }

        return instance;
    }

}
