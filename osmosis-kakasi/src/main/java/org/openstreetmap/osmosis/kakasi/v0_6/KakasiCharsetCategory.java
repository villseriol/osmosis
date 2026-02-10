package org.openstreetmap.osmosis.kakasi.v0_6;

/**
 * @author Villseriol
 */
public enum KakasiCharsetCategory {
    /**
     * Known as "ascii" character set.
     */
    ASCII("a"),
    /**
     * Known as "jis roman" character set.
     */
    JISROMAN("j"),
    /**
     * It is the DEC graphic character set.
     */
    GRAPHIC("g"),
    /**
     * JIS x0201, defined as part of the GR character set.
     */
    KATAKANA("k"),
    /**
     * JIS x0208 characters included between 16 and 94 sections.
     */
    KANJI("J"),
    /**
     * JIS x0208 characters included in section 4 (Hiragana)
     */
    HIRAGANA("H"),
    /**
     * JIS x0208 characters included in section 5 (Katakana)
     */
    KATAKANA_JIS("K"),
    /**
     *  JIS x0208 characters included in section 1,2,3,6,7, and 8. (Note that section 9-15 are undefined in JIS x0208.)
     */
    SIGN("E");

    private final String code;

    private KakasiCharsetCategory(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
