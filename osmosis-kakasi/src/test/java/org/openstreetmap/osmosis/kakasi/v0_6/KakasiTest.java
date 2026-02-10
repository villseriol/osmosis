package org.openstreetmap.osmosis.kakasi.v0_6;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.openstreetmap.osmosis.testutil.AbstractDataTest;

public class KakasiTest extends AbstractDataTest {
    @Before
    public void setUp() {
        KakasiConfig config = KakasiConfig.createDefaultConfig();

        File largeDictionary = dataUtils.createDataFile("dictionary/SKK-JISYO.L");
        String largeDictionaryPath = largeDictionary.getAbsolutePath();

        Set<String> dictionaries = new HashSet<>();
        dictionaries.add(largeDictionaryPath);

        config.setDictionaries(dictionaries);
        Kakasi.configure(config);
    }

    @Before
    public void tareDown() {

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
    public void testAll() {
        assertEquals("Fullwidth & kanji", Kakasi.run("Ｆｕｌｌｗｉｄｔｈ ＆ 漢字"));
    }
}
