package org.openstreetmap.osmosis.kakasi.v0_6;

public enum KakasiCharset {
    ASCII("a", "Known as \"ascii\" character set."),
    JISROMAN("j", "Known as \"jis roman\" character set."),
    GRAPHIC("g", "It is the DEC graphic character set."),
    KATAKANA("k", "JIS x0201, defined as part of the GR character set."),
    KANJI("J", "JIS x0208 characters included between 16 and 94 sections."),
    HIRAGANA("H", "JIS x0208 characters included in section 4 (Hiragana)"),
    KATAKANA_JIS("K", "JIS x0208 characters included in section 5 (Katakana)"),
    SIGN("E",
            "JIS x0208 characters included in section 1,2,3,6,7, and 8. (Note that section 9-15 are undefined in JIS x0208.)");

    private final String code;
    private final String description;

    private KakasiCharset(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
