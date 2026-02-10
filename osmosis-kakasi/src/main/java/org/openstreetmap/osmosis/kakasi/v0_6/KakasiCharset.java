package org.openstreetmap.osmosis.kakasi.v0_6;

public enum KakasiCharset {
    //   jis \-\- Widely used on the internet. (Ex: fj, jp, .. newsgroups)
    //          Derived from ISO\-2022 coding manner.
    //          newjis: JISx0208 (1983) invoked by ESC\-$\-B.
    //          oldjis: JISx0208 (1978) invoked by ESC\-$\-@.
    JIS("jis"),
    //   euc,dec \-\- Often used in UNIX like computers. JISx0208 is
    //          assigned to GR ( MSB is 1 ). The major difference between
    //          euc and dec is assignment of JISx0201 KATAKANA and
    //          the DEC graphic character.
    EUC("euc"),
    DEC("dec"),
    //   sjis \-\- Defined by Microsoft Corp. Widely used on the personal
    //          computers ( MSDOS, Mac, .. )
    SJIS("sjis"),
    //    utf8 \-\- Current international standard.  All modern OSs use this
    //          encoding of the Unicode character set as the default.
    UTF8("utf8");

    private final String code;

    private KakasiCharset(final String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
