// This software is released into the Public Domain.  See copying.txt for details.
package org.openstreetmap.osmosis.kakasi.v0_6;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openstreetmap.osmosis.testutil.AbstractDataTest;


public class KakasiTest extends AbstractDataTest {
    private static final Charset EUC_JP = Charset.forName("EUC-JP");

    @Before
    public void setUp() {
        KakasiConfig config = KakasiConfig.createDefaultConfig();

        File largeDictionary = dataUtils.createDataFile("dictionary/SKK-JISYO.L", EUC_JP);
        String largeDictionaryPath = largeDictionary.getAbsolutePath();

        File stationDictionary = dataUtils.createDataFile("dictionary/SKK-JISYO.station", EUC_JP);
        String stationDictionaryPath = stationDictionary.getAbsolutePath();

        List<String> dictionaries = new ArrayList<>();
        dictionaries.add(largeDictionaryPath);
        dictionaries.add(stationDictionaryPath);

        config.setDictionaries(dictionaries);
        Kakasi.configure(config);
    }


    @Before
    public void tareDown() {
        // Do nothing
    }


    @Test
    public void testSimpleAscii() {
        assertEquals("test (villseriol)", Kakasi.run("test (villseriol)"));
        assertEquals("test", Kakasi.run("test"));
    }


    @Test
    public void testZenkaku() {
        assertEquals("Fullwidth", Kakasi.run("Ｆｕｌｌｗｉｄｔｈ"));
    }


    @Test
    public void testKatakana() {
        assertEquals("aporobe^kari^", Kakasi.run("アポロベーカリー"));
    }


    @Test
    public void testKanji() {
        // Known issues with kakasi and dictionary entry priority
        assertNotEquals("nihon", Kakasi.run("日本"));

        // Only works when using .geo dictionary, but causes other entries to fail

        // For OSM projects, exclude the .geo dictionary
        // If the entry is of geographic importance, it most likely has an english/romaji entry anyway
        assertNotEquals("shirahanechou", Kakasi.run("白羽根町"));

        assertEquals("a^kanso^ shuu", Kakasi.run("アーカンソー州"));
        assertEquals("Fullwidth & kanji", Kakasi.run("Ｆｕｌｌｗｉｄｔｈ ＆ 漢字"));
        assertEquals("anan ichiritsu aba kubou . minzoku shiryoukan", Kakasi.run("阿南市立阿波公方・民俗資料館"));
    }
}
