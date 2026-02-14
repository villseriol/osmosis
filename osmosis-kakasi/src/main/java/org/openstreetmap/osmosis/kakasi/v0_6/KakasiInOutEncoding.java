package org.openstreetmap.osmosis.kakasi.v0_6;

/**
 * @author Villseriol
 */
public enum KakasiInOutEncoding {
    /**
     * JIS — widely used on the internet (e.g. FJ, JP newsgroups).
     * <p>
     * Derived from the ISO-2022 coding scheme.
     *
     * <ul>
     *   <li>{@code newjis}: JIS X 0208 (1983), invoked by {@code ESC-$-B}</li>
     *   <li>{@code oldjis}: JIS X 0208 (1978), invoked by {@code ESC-$-@}</li>
     * </ul>
     */
    JIS("jis"),
    /**
     * EUC / DEC — often used on UNIX-like systems.
     * <p>
     * JIS X 0208 is assigned to GR (most significant bit set to 1).
     * The major difference between EUC and DEC is the assignment of
     * JIS X 0201 Katakana and DEC graphic characters.
     */
    EUC("euc"), DEC("dec"),
    /**
     * Shift_JIS — defined by Microsoft.
     * <p>
     * Widely used on personal computers (MS-DOS, classic Mac OS, etc.).
     */
    SJIS("sjis"),
    /**
     * UTF-8 — the current international standard.
     * <p>
     * All modern operating systems use this encoding of the Unicode
     * character set as the default.
     */
    UTF8("utf8");

    private final String code;

    private KakasiInOutEncoding(final String code) {
        this.code = code;
    }


    public String getCode() {
        return code;
    }
}
